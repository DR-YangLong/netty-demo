package netty.demo.linedecoder.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * functional describe:
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-8-23
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter{
    private static final Logger logger= LoggerFactory.getLogger(TimeServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //消息数组转换成字符串,减去分割符
        String msgStr=(String)msg;
        logger.debug("收到的消息是：" + msgStr);
        //响应客户端
        String response="This time is "+new Date(System.currentTimeMillis()).toString();
        //添加分割，使客户端可以拆包
        response=response+System.getProperty("line.separator");
        //响应字符串要转换成ByteBuf
        //申请响应ByteBuf
        ByteBuf send=ctx.alloc().buffer(4*response.length());
        //写入响应字符数组
        send.writeBytes(response.getBytes());
        //发送
        ctx.write(send);
        logger.debug("===========出TimeServerHandler===========");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
