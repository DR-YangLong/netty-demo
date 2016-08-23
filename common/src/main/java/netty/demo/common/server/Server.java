package netty.demo.common.server;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * functional describe:server端模板
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-8-23
 */
public class Server {
    private static final Logger logger= LoggerFactory.getLogger(Server.class);

    private int port=8888;
    private ChannelInitializer<SocketChannel> channelChannelInitializer;

    public void start(){
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap=new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .childHandler(this.channelChannelInitializer);
            ChannelFuture future=bootstrap.bind(port).sync();
            System.out.print("===========服务端启动===========");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
           logger.error("服务端启动错误",e);
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public Server(ChannelInitializer<SocketChannel> channelChannelInitializer) {
        this.channelChannelInitializer = channelChannelInitializer;
    }

    public Server(int port, ChannelInitializer<SocketChannel> channelChannelInitializer) {
        this.port = port;
        this.channelChannelInitializer = channelChannelInitializer;
    }
}
