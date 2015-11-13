package netty.demo.six.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.demo.six.coder.ObjectEncoder;
import netty.demo.six.coder.StringEncoder;

/**
 * package: netty.demo.six.client <br/>
 * blog:<a href="http://dr-yanglong.github.io/">dr-yanglong.github.io</a><br/>
 * functional describe:字符串协议客户端
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/11/13
 */
public class StringClient {
    //客户端实现
    public void start(String host, int port) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.handler(new ChannelInitializer() {
                @Override
                protected void initChannel(Channel channel) throws Exception {
                    //注册handler
                    //客户端发送编码
                    channel.pipeline().addLast(new StringEncoder());
                    //业务处理
                    channel.pipeline().addLast(new HttpClientInBoundHandler());
                }
            });
            ChannelFuture future = bootstrap.connect(host, port).sync();future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    //启动客户端
    public static void main(String[] args) throws Exception {
        StringClient client = new StringClient();
        client.start("127.0.0.1", 8000);
    }
}
