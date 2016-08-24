package netty.demo.delimiterdecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * functional describe:
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-8-23
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger= LoggerFactory.getLogger(EchoClientHandler.class);
    private long count=0;
    static final String ECHO_REQ="Hi,this is client send.$_";
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
       for(int i=0;i<10;i++){
           ctx.writeAndFlush(Unpooled.copiedBuffer(ECHO_REQ.getBytes()));
       }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            String body=(String)msg;
            logger.debug("message from server:"+body);
    }
}
