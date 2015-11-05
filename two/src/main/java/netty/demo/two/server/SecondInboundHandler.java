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
 * functional describe:第二个 in handler
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/11/5
 */
public class SecondInboundHandler extends ChannelInboundHandlerAdapter{
private static final Logger logger = LoggerFactory.getLogger(SecondInboundHandler.class);
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
        logger.debug("=========出第二个InboundHandler=================");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.debug("=========第二个InboundHandler，开始读消息=================");
        ByteBuf copyMsg=(ByteBuf)msg;
        //按消息长度创建接收数组
        byte[] result=new byte[copyMsg.readableBytes()];
        //将消息转换成数组
        copyMsg.readBytes(result);
        //消息数组转换成字符串
        String msgStr=new String(result);
        logger.debug("收到的消息是：" + msgStr);
        //响应客户端
        String response="This is second in handler!";
        //响应字符串要转换成ByteBuf
        //申请响应ByteBuf
        ByteBuf send=ctx.alloc().buffer(4 * response.length());
        //写入响应字符数组
        send.writeBytes(response.getBytes());
        //发送，跳到outBoundHandler执行
        ctx.write(send);
        //释放资源
        send.release();
        logger.debug("===========第二个InboundHandler读消息结束===========");
    }
}
