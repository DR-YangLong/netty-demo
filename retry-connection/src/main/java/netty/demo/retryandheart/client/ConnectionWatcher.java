package netty.demo.retryandheart.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;

/**
 * functional describe:链接状态监视器，实现inboundHandler，监听链路的链接情况。
 * 记录重连次数，当链路链接成功时为0,当链路断开后，发起重连，并记录重连次数，
 * 在达到上限次数还没有连接成功时停止。
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-9-11
 */
@ChannelHandler.Sharable
public abstract class ConnectionWatcher extends ChannelInboundHandlerAdapter implements TimerTask, ChannelHandlerHolder {
    private static final Logger logger= LoggerFactory.getLogger(ConnectionWatcher.class);
    //客户端
    private final Bootstrap bootstrap;
    //定时调度器
    private final Timer timer;
    //服务端服端口
    private final int port;
    //服务端ip
    private final String host;
    //链接断开后是否重连
    private volatile boolean reconnect=true;
    //已重试连接次数
    private int retryCount;
    //最大重连次数
    private final int maxRetryCount;

    public ConnectionWatcher(Bootstrap bootstrap, Timer timer, int port, String host, boolean reconnect, int maxRetryCount) {
        this.bootstrap = bootstrap;
        this.timer = timer;
        this.port = port;
        this.host = host;
        this.reconnect = reconnect;
        this.maxRetryCount = maxRetryCount;
    }

    /**
     * channel链路每次active的时候，将其连接的次数重新☞ 0
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.error("当前链路已经激活了，重连尝试次数重新置为0");
        retryCount= 0;
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("链接关闭");
        if(reconnect){
            System.out.println("链接关闭，将进行重连");
            if (retryCount < maxRetryCount) {
                retryCount++;
                //重连的间隔时间会越来越长
                int timeout = 2 << retryCount;
                timer.newTimeout(this, timeout, TimeUnit.MILLISECONDS);
            }
        }
        ctx.fireChannelInactive();
    }


    public void run(Timeout timeout) throws Exception {
        ChannelFuture future;
        //绑定handler
        synchronized (bootstrap) {
            bootstrap.handler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ch.pipeline().addLast(handlers());
                }
            });
            future = bootstrap.connect(host,port);
        }
        //future对象
        future.addListener((ChannelFuture f)-> {
                boolean succeed = f.isSuccess();
                //如果重连失败，则调用ChannelInactive方法，再次出发重连事件，
                // 一直尝试达到次数上限，如果失败则不再重连
                if (!succeed) {
                    logger.error("重连失败");
                    f.channel().pipeline().fireChannelInactive();
                }else{
                    logger.error("重连成功");
                }
        });

    }
}
