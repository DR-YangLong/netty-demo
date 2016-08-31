package netty.demo.marshalling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * functional describe:服务断业务处理
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-8-30
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger= LoggerFactory.getLogger(ServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ReqBody reqBody= (ReqBody) msg;
        logger.error(reqBody.toString());
        RespBody respBody=new RespBody();
        respBody.setReqId(reqBody.getReqId());
        List<String> list=new ArrayList<>();
        list.add("订单");
        list.add("处理");
        list.add("成功");
        respBody.setDesc(list);
        ctx.writeAndFlush(respBody);
    }
}
