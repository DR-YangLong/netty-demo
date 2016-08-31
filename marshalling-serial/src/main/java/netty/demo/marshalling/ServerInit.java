package netty.demo.marshalling;

import io.netty.channel.socket.SocketChannel;

/**
 * functional describe:服务端handler初始化
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-8-30
 */
public class ServerInit extends SuperInit {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        super.initChannel(ch);
        ch.pipeline().addLast(new ServerHandler());
    }
}
