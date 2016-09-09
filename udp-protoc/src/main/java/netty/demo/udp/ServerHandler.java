package netty.demo.udp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.ThreadLocalRandom;

/**
 * functional describe:
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-9-9
 */
public class ServerHandler extends ChannelInitializer<NioDatagramChannel> {
    private static final Logger logger= LoggerFactory.getLogger(ServerHandler.class);

    @Override
    protected void initChannel(NioDatagramChannel ch) throws Exception {
        ch.pipeline().addLast(new ChineseProverbServerHandler());
    }

    static class ChineseProverbServerHandler extends SimpleChannelInboundHandler<DatagramPacket>{
        private static final String[] DICTIONARY={"好好学习天天向上","双十一猫狗大战","双十二什么也没有","哎呀，下标要超过了"};

        private String nextQuote(){
            int quoteId= ThreadLocalRandom.current().nextInt(DICTIONARY.length);
            return DICTIONARY[quoteId];
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
           String req=msg.content().toString(CharsetUtil.UTF_8);
            logger.error("收到广播信息:"+req);
            if("谚语字段查询？".equals(req)){
                ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("查询结果："+nextQuote(),CharsetUtil.UTF_8),msg.sender()));
            }
        }
    }
}
