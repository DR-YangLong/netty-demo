package netty.demo.udp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * functional describe:udpServer,处理udp链接,由于udp是广播，所以是客户端对客户端，并非是服务端-客户端的方式
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-9-8
 */
public class UdpServer {
    private static final Logger logger= LoggerFactory.getLogger(UdpServer.class);

    private int port=8888;
    private ChannelInitializer<NioDatagramChannel> channelChannelInitializer;

    public void start(){
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        try {
            Bootstrap bootstrap=new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST,true)
                    .handler(this.channelChannelInitializer);
            ChannelFuture future=bootstrap.bind(port).sync();
            System.out.print("===========服务端启动===========");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error("服务端启动错误",e);
        }finally {
            workerGroup.shutdownGracefully();
        }
    }

    public UdpServer(ChannelInitializer<NioDatagramChannel> channelChannelInitializer) {
        this.channelChannelInitializer = channelChannelInitializer;
    }

    public UdpServer(int port, ChannelInitializer<NioDatagramChannel> channelChannelInitializer) {
        this.port = port;
        this.channelChannelInitializer = channelChannelInitializer;
    }

    public static void main(String[] args) throws Exception{
      new  UdpServer(new ServerHandler()).start();
    }
}
