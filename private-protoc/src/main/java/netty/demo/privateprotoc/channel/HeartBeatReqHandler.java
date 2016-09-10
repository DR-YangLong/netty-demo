package netty.demo.privateprotoc.channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.ScheduledFuture;
import netty.demo.privateprotoc.protocol.Header;
import netty.demo.privateprotoc.protocol.MessageType;
import netty.demo.privateprotoc.protocol.NettyMessage;

/**
 * functional describe:客户端自定义心跳handler
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-9-10
 */
public class HeartBeatReqHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger=LoggerFactory.getLogger(HeartBeatReqHandler.class);
    private volatile ScheduledFuture<?> heartBeat;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_RESP.getValue()) {
            //链接成功，添加心跳
            heartBeat = ctx.executor().scheduleAtFixedRate(new HeartBeatReqHandler.HeartBeatTask(ctx), 0, 500, TimeUnit.MILLISECONDS);
        }else if(message.getHeader() != null && message.getHeader().getType() == MessageType.HEART_BEAT_RESP.getValue()){
            logger.error("收到服务器心跳回复信息！");
        }else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if(heartBeat!=null){
            heartBeat.cancel(true);
            heartBeat=null;
        }
        ctx.fireExceptionCaught(cause);
    }

    static class HeartBeatTask implements Runnable {
        private final ChannelHandlerContext ctx;

        public HeartBeatTask(ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        @Override
        public void run() {
            NettyMessage heart = new NettyMessage();
            Header header = new Header();
            header.setType(MessageType.HEART_BEAT_REQ.getValue());
            heart.setHeader(header);
            ctx.writeAndFlush(heart);
        }
    }
}
