package netty.demo.protobuf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * functional describe:服务端处理handler
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-8-30
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger= LoggerFactory.getLogger(ServerHandler.class);

    //处理请求
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReqProto.SubscribeReq req= (SubscribeReqProto.SubscribeReq) msg;
        logger.debug("客户端请求："+req);
        ctx.writeAndFlush(createResp(req.getSubReqId()));
    }


    /**
     * 创建响应对象
     * @param resId
     * @return
     */
    private SubscribeRespProto.SubscribeResp createResp(int resId){
        SubscribeRespProto.SubscribeResp.Builder builder=SubscribeRespProto.SubscribeResp.newBuilder();
        builder.setSubReqId(resId);
        builder.setRespCode(0);
        builder.setDesc("请求业务处理成功");
        return builder.build();
    }
}
