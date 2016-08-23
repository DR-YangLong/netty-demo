package netty.demo.linedecoder.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * functional describe:
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-8-23
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter{
    private static final Logger logger= LoggerFactory.getLogger(TimeClientHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("====================收到服务端响应======================");
        logger.debug("Server said:" + (String)msg);
        //释放资源，如果有多个handler，后面的handler还需要此资源
        logger.info("====================客户端处理服务端消息结束======================");
        //如果这是最终的handler，响应处理完毕，关闭链接
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("===================channelActive==================");
        String msg="查询服务器时间"+System.getProperty("line.separator");
        //申请ByteBuf
        for (int i=0;i<50;i++) {
            ByteBuf send = ctx.alloc().buffer(4 * msg.length());
            //消息写入ByteBuf
            send.writeBytes(msg.getBytes());
            //发送
            ctx.write(send);
            ctx.flush();
        }
    }
}
