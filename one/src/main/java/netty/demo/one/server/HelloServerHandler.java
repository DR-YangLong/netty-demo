package netty.demo.one.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lenovo on 2015/11/4.
 * 接收服务端消息并返回
 */
public class HelloServerHandler extends ChannelInboundHandlerAdapter{
    private static final Logger logger= LoggerFactory.getLogger(HelloServerHandler.class);
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.debug("==========进入HelloServerHandler=============");
        ByteBuf copyMsg=(ByteBuf)msg;
        //按消息长度创建接收数组
        byte[] result=new byte[copyMsg.readableBytes()];
        //将消息转换成数组
        copyMsg.readBytes(result);
        //消息数组转换成字符串
        String msgStr=new String(result);
        logger.debug("收到的消息是：" + msgStr);
        //释放字符串资源 必须做
        copyMsg.release();
        //响应客户端
        String response="Hello client!";
        //响应字符串要转换成ByteBuf
        //申请响应ByteBuf
        ByteBuf send=ctx.alloc().buffer(4*response.length());
        //写入响应字符数组
        send.writeBytes(response.getBytes());
        //发送
        ctx.write(send);
        ctx.flush();
        logger.debug("===========出HelloServerHandler===========");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.debug("==========HelloServerHandler处理完成调用============");
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.debug("==========HelloServerHandler发生异常触发============");
        super.exceptionCaught(ctx,cause);
    }
}
