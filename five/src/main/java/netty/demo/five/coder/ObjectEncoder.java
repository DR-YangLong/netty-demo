package netty.demo.five.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import netty.demo.common.KryoSerialization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * package: netty.demo.five.coder <br/>
 * blog:<a href="http://dr-yanglong.github.io/">dr-yanglong.github.io</a><br/>
 * functional describe:对象编码器，将Object对像编码成byte数组
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/11/12
 */
public class ObjectEncoder extends MessageToByteEncoder{
    private static final Logger logger = LoggerFactory.getLogger(ObjectEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        logger.debug("================入ObjectEncoder============");
        byte[] bytes= KryoSerialization.serialize(msg);
        out.writeBytes(bytes);
        ctx.flush();
        logger.debug("================出ObjectEncoder============");
    }
}
