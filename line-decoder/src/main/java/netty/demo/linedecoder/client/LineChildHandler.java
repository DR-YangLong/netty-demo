package netty.demo.linedecoder.client;

import io.netty.channel.socket.SocketChannel;
import netty.demo.linedecoder.LineSuperHandler;

/**
 * functional describe:
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-8-23
 */
public class LineChildHandler extends LineSuperHandler {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        super.initChannel(ch);
        ch.pipeline().addLast(new TimeClientHandler());
    }
}
