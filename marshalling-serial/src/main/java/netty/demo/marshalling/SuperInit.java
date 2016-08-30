package netty.demo.marshalling;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * functional describe:
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-8-30
 */
public class SuperInit extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline=ch.pipeline();
        pipeline.addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
        pipeline.addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
        //TODO 业务处理handler
    }
}
