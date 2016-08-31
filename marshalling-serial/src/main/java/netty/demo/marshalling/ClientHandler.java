package netty.demo.marshalling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.IntStream;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * functional describe:客户端业务处理
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-8-30
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger= LoggerFactory.getLogger(ClientHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        IntStream.range(0,10).forEach(i->{
            ReqBody reqBody=new ReqBody();
            reqBody.setReqId(i);
            reqBody.setUserName("买家："+i);
            ctx.write(reqBody);
                }
        );
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RespBody respBody= (RespBody) msg;
        logger.error(respBody.toString());
    }
}
