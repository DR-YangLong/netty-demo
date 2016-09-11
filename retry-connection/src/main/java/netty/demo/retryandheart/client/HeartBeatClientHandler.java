package netty.demo.retryandheart.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * functional describe:处理服务端心跳返回消息，并中断消息传递
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-9-11
 */
@ChannelHandler.Sharable
public class HeartBeatClientHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(HeartBeatClientHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.error("HeartBeatClientHandler#channelActive:激活时间-" + new Date());
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.error("HeartBeatClientHandler#channelInactive:链接断开时间-"+new Date());
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String message = (String) msg;
        logger.error(message);
        if (message.equals("Heartbeat")) {
            ctx.writeAndFlush("已经收到心跳响应");
        }
        ReferenceCountUtil.release(msg);
    }
}
