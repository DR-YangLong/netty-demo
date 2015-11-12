package netty.demo.five.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import netty.demo.common.ByteBufToBytes;
import netty.demo.common.KryoSerialization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * package: netty.demo.five.coder <br/>
 * blog:<a href="http://dr-yanglong.github.io/">dr-yanglong.github.io</a><br/>
 * functional describe:对象解码器，将byte数组转换成Object对象
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/11/12
 */
public class ObjectDecoder extends ByteToMessageDecoder{
    private static final Logger logger = LoggerFactory.getLogger(ObjectDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        logger.debug("================进入ObjectDecoder============");
        ByteBufToBytes read = new ByteBufToBytes(in.readableBytes());
        byte[] objBytes=read.read(in);
        Object object=KryoSerialization.deserialize(objBytes);
        out.add(object);
        logger.debug("================出ObjectDecoder============");
    }
}
