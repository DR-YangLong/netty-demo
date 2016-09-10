package netty.demo.common.Client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * functional describe:客户端模板
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-8-23
 */
public class Client {
    private static final Logger logger= LoggerFactory.getLogger(Client.class);
    private int port=8888;
    private String host="127.0.0.1";
    private ChannelInitializer<SocketChannel> initializer;
    private ScheduledExecutorService executor= Executors.newScheduledThreadPool(1);
    public Client(ChannelInitializer<SocketChannel> initializer) {
        this.initializer = initializer;
    }

    public Client(int port, String host, ChannelInitializer<SocketChannel> initializer) {
        this.port = port;
        this.host = host;
        this.initializer = initializer;
    }

    public void connect(){
        EventLoopGroup group=new NioEventLoopGroup();
        try {
            Bootstrap bootstrap=new Bootstrap();
            bootstrap.group(group);
            bootstrap.channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(initializer);
            ChannelFuture future=bootstrap.connect(host,port).sync();
            future.channel().closeFuture().sync();
        }catch (Exception e){
            logger.error("客户端启动错误",e);
        }
        finally {
            //group.shutdownGracefully();
            executor.execute(()->{
                try {
                    TimeUnit.SECONDS.sleep(5);
                    connect();
                } catch (Exception  e) {
                  logger.error("重连出错！",e);
                }
            });
        }
    }
}
