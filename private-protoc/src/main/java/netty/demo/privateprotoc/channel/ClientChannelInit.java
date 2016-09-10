package netty.demo.privateprotoc.channel;

import io.netty.channel.socket.SocketChannel;

/**
 * functional describe:客户端初始化
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-9-10
 */
public class ClientChannelInit extends ChannelInit {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        super.initChannel(ch);
        ch.pipeline().addLast(new LoginAuthReqHandler());
    }
}
