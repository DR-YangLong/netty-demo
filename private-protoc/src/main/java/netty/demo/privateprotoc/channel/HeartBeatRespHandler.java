package netty.demo.privateprotoc.channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.demo.privateprotoc.protocol.Header;
import netty.demo.privateprotoc.protocol.MessageType;
import netty.demo.privateprotoc.protocol.NettyMessage;

/**
 * functional describe:服务端自定义心跳Handler
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-9-10
 */
public class HeartBeatRespHandler extends ChannelInboundHandlerAdapter {
private static final Logger logger= LoggerFactory.getLogger(HeartBeatRespHandler.class);
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.HEART_BEAT_REQ.getValue()) {
            //收到心跳请求
            logger.error("收到客户端心跳请求！");
            NettyMessage response=new NettyMessage();
            Header header=new Header();
            header.setType(MessageType.HEART_BEAT_RESP.getValue());
            response.setHeader(header);
            ctx.writeAndFlush(response);
        }else {
            ctx.fireChannelRead(msg);
        }
    }
}
