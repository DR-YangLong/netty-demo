package netty.demo.privateprotoc.channel;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import netty.demo.privateprotoc.codec.NettyMessageDecoder;
import netty.demo.privateprotoc.codec.NettyMessageEncoder;

/**
 * functional describe:handler通用初始化
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-9-9
 */
public class ChannelInit extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new NettyMessageDecoder(1024*1024,4,4,-8,0)).addLast(new NettyMessageEncoder());
    }
}
