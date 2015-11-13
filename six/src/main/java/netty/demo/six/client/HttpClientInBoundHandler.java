package netty.demo.six.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.demo.six.coder.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * package: netty.demo.three.client <br/>
 * blog:<a href="http://dr-yanglong.github.io/">dr-yanglong.github.io</a><br/>
 * <p>
 * functional describe:http客户端模拟处理
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/11/6
 */
public class HttpClientInBoundHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientInBoundHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.debug("================进入客户端InBoundHandler channelRead============");
        ByteBuf result = (ByteBuf) msg;
        byte[] result1 = new byte[result.readableBytes()];
        result.readBytes(result1);
        logger.debug("服务端消息:" + new String(result1));
        logger.debug("================出客户端InBoundHandler channelRead============");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("================进入客户端InBoundHandler channelActive============");
        Person person=new Person();
        person.setUserId(1l);
        person.setUserName("YangLong");
        person.setPassword("123456");
        logger.debug(person.toString());
        ctx.write(person);
        ctx.flush();
        logger.debug("================出客户端InBoundHandler channelActive============");
    }
}
