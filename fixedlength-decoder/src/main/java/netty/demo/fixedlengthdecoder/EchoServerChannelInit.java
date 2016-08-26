package netty.demo.fixedlengthdecoder;

import io.netty.channel.socket.SocketChannel;

/**
 * functional describe:服务端handler注册
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-8-23
 */
public class EchoServerChannelInit extends SuperInitializer {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        super.initChannel(ch);
        ch.pipeline().addLast(new EchoServerHandler());
    }
}
