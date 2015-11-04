package netty.demo.one.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

/**
 * Created by lenovo on 2015/11/4.
 * 服务端类，监听客户端链接
 */
public class HelloServer {
    private static final Logger logger= LoggerFactory.getLogger(HelloServer.class);
    //服务端实现
    public void start(int port) throws Exception{
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) //这里我们采用NioServerSocketChannel类来实例化一个进来的连接。
                    .childHandler(new ChannelInitializer<SocketChannel>() { //我们总是为新连接到服务器的handler分配一个新的Channel. ChannelInitializer用于配置新生成的Channel, 就和你通过配置ChannelPipeline来配置Channel是一样的效果。考虑到应用程序的复杂性，你可以采用一个匿名类来向pipeline中添加更多的handler。
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            //注册handler
                            ch.pipeline().addLast(new HelloServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)// 你也可以向指定的Channel设置参数。由于我开发的是TCP/IP服务器，所以我们可以对socket设置诸如tcpNoDelay,keepAlive之类的参数。要了解更多有关ChannelOption的设置请参考相关的api 文档。
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // 你是否主要到代码中用到了option(), childOption()两个不同的方法。option() 方法用于设置监听套接字。childOption()则用于设置连接到服务器的客户端套接字。

            //一切都已准备就绪，接下来就让我们启动服务器。
            // 这里我们绑定了主机所有网卡的<param>port</param>端口。
            // 你可以多次调用bind()方法来绑定不同的地址
            ChannelFuture f = b.bind(port).sync();
            // 关闭链接
            f.channel().closeFuture().sync();
        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            logger.debug("========服务端关闭===========");
        }
    }

    //启动服务端
    public static void main(String[] args) throws Exception{
        int port=8000;
        HelloServer server = new HelloServer();
        logger.debug("服务端启动，地址："+ InetAddress.getLocalHost().getHostAddress()+":"+port);
        server.start(port);
    }
}
