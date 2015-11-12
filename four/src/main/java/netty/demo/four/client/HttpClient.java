package netty.demo.four.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;

import java.net.URI;

/**
 * package: netty.demo.three.client <br/>
 * blog:<a href="http://dr-yanglong.github.io/">dr-yanglong.github.io</a><br/>
 * <p>
 * functional describe:
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/11/6
 */
public class HttpClient {
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
                    //客户端最后的工作是接收消息第一，接收服务端消息,解码
                    channel.pipeline().addLast(new HttpResponseDecoder());
                    //客户端发送编码
                    channel.pipeline().addLast(new HttpRequestEncoder());
                    //业务处理
                    channel.pipeline().addLast(new HttpClientInBoundHandler());
                }
            });
            ChannelFuture future = bootstrap.connect(host, port).sync();
            URI uri=new URI("http://"+host+":"+port);
            String msg="Hello HttpServer!";
            DefaultFullHttpRequest request=new DefaultFullHttpRequest(HttpVersion.HTTP_1_1,HttpMethod.POST,uri.toASCIIString(), Unpooled.wrappedBuffer(msg.getBytes()));
            //设置请求头
            request.headers().set(HttpHeaders.Names.HOST, host);
            request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
            request.headers().set(HttpHeaders.Names.CONTENT_LENGTH, request.content().readableBytes());
            request.headers().set("messageType", "normal");
            request.headers().set("businessType", "testServerState");
            //发起请求
            future.channel().write(request);
            future.channel().flush();
            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    //启动客户端
    public static void main(String[] args) throws Exception {
        HttpClient client = new HttpClient();
        client.start("127.0.0.1", 8000);
    }
}
