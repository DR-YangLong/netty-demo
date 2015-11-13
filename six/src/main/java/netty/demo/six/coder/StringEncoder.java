package netty.demo.six.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * package: netty.demo.six.coder <br/>
 * blog:<a href="http://dr-yanglong.github.io/">dr-yanglong.github.io</a><br/>
 * functional describe:将String转换成byte数组
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/11/13
 */
public class StringEncoder extends MessageToByteEncoder{
    private static final Logger logger= LoggerFactory.getLogger(StringEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        logger.debug("===================入StringEncoder==========================");
        Person person=(Person)msg;
        String mes=person.toString();
        logger.debug(mes);
        out.writeBytes(mes.getBytes());
        logger.debug("===================出StringEncoder==========================");
    }
}
