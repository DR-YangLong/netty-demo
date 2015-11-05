package netty.demo.two.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * package: netty.demo.two.server <br/>
 * blog:<a href="http://dr-yanglong.github.io/">dr-yanglong.github.io</a><br/>
 * <p>
 * functional describe:第一个in handler
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/11/5
 */
public class FirstInboundHandler extends ChannelInboundHandlerAdapter{
    private static final Logger logger= LoggerFactory.getLogger(FirstInboundHandler.class);

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.debug("=========出第一个InboundHandler=================");
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.debug("=========第一个InboundHandler，开始读消息=================");
        ByteBuf copyMsg=(ByteBuf)msg;
        //按消息长度创建接收数组
        byte[] result=new byte[copyMsg.readableBytes()];
        //将消息转换成数组
        copyMsg.readBytes(result);
        //消息数组转换成字符串
        String msgStr=new String(result);
        logger.debug("收到的消息是：" + msgStr);
        //v-2到下一个inbound
        ctx.fireChannelRead(msg);
        /*v-1
        //响应客户端
        String response="This is first in handler!";
        //响应字符串要转换成ByteBuf
        //申请响应ByteBuf
        ByteBuf send=ctx.alloc().buffer(4*response.length());
        //写入响应字符数组
        send.writeBytes(response.getBytes());
        send.release();
        //v-1发送到outboundHandler，此方法会直接回调outboundHandler
        ctx.write(send);
        copyMsg=(ByteBuf)msg;
        result=new byte[copyMsg.readableBytes()];
        copyMsg.readBytes(result);
        msgStr=new String(result);
        logger.debug("outboundHandler释放后第二次调用："+msgStr);
        //v-1传递到下一个handler，如果调用了上面的ctx.write(send)，
        // 并且在outboundHandler中已经使用ctx.flush()写过消息，
        // 那么msg是空的，因为资源已经被释放了
        //ctx.fireChannelRead(msg);
        */
        logger.debug("===========第一个InboundHandler读消息结束===========");
    }
}
