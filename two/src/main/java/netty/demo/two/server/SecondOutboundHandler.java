package netty.demo.two.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * package: netty.demo.two.server <br/>
 * blog:<a href="http://dr-yanglong.github.io/">dr-yanglong.github.io</a><br/>
 * <p>
 * functional describe:第二个 out handler
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/11/5
 */
public class SecondOutboundHandler extends ChannelOutboundHandlerAdapter{
    private static final Logger logger= LoggerFactory.getLogger(SecondOutboundHandler.class);
    @Override
    public void  write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        logger.debug("===========进入第二个outBoundHandler=============");
        ByteBuf copyMsg=(ByteBuf)msg;
        //按消息长度创建接收数组
        byte[] result=new byte[copyMsg.readableBytes()];
        //将消息转换成数组
        copyMsg.readBytes(result);
        //消息数组转换成字符串
        String msgStr=new String(result);
        logger.debug("收到的消息是：" + msgStr);
        String response="the second outBoundHandler!";
        ByteBuf encoded = ctx.alloc().buffer(4 * response.length());
        encoded.writeBytes(response.getBytes());
        ctx.write(encoded);
        //将消息立即写到客户端
        ctx.flush();
        //释放资源
        encoded.release();
        logger.debug("===========出第二个outBoundHandler=============");
    }
}
