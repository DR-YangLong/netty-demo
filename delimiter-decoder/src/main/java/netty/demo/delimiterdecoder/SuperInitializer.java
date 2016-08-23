package netty.demo.delimiterdecoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * functional describe:channel初始化模板
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-8-23
 */
public class SuperInitializer extends ChannelInitializer<SocketChannel>{

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ByteBuf delimiter= Unpooled.copiedBuffer("$_".getBytes());
        ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,delimiter));
        ch.pipeline().addLast(new StringDecoder());
    }
}
