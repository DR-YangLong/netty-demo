package netty.demo.six.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import netty.demo.common.ByteBufToBytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * package: netty.demo.six.coder <br/>
 * blog:<a href="http://dr-yanglong.github.io/">dr-yanglong.github.io</a><br/>
 * functional describe:将byte数组转换成String
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/11/13
 */
public class StringDecoder extends ByteToMessageDecoder{
    private static final Logger logger= LoggerFactory.getLogger(StringDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        logger.debug("===================入StringDecoder==========================");
        //获取协议标识，{@see Person#toString}
        byte protocol="P".getBytes()[0];
        byte clientProtocol=in.readByte();
        //重置in的读取下标
        in.resetReaderIndex();
        //如果是本解码器可以解码的协议
        if(protocol==clientProtocol){
            ByteBufToBytes reader = new ByteBufToBytes(in.readableBytes());
            String msg=new String(reader.read(in));
            logger.debug(msg);
            //此处输出到业务处理器，将消息转变为对象，示例仅新建一个
            Person person=new Person();
            person.setPassword("789456");
            person.setUserName("杨龙");
            person.setUserId(2l);
            out.add(person);
        }else {//不是本解码器处理的协议
            ctx.fireChannelRead(in);
        }
        logger.debug("===================出StringDecoder==========================");
    }
}
