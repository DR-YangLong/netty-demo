package netty.demo.privateprotoc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;

/**
 * functional describe:将decode方法扩展为公有，可以外部使用
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-9-10
 */
public class NettyMarshallingDecoder extends MarshallingDecoder{

    public NettyMarshallingDecoder(UnmarshallerProvider provider) {
        super(provider);
    }

    public NettyMarshallingDecoder(UnmarshallerProvider provider, int maxObjectSize) {
        super(provider, maxObjectSize);
    }

    @Override
    public Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        return super.decode(ctx, in);
    }
}
