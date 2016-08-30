package netty.demo.protobuf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * functional describe:客户端处理Handler
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-8-30
 */
public class ClientHandler extends ChannelInboundHandlerAdapter{
    private static final Logger logger= LoggerFactory.getLogger(ClientHandler.class);
    //链接建立后，发送请求
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i=0;i<10;i++){
            ctx.write(createReq(i));
        }
        ctx.flush();
    }

    //处理服务端响应消息
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.debug("服务端响应："+msg);
    }

    private SubscribeReqProto.SubscribeReq createReq(int id){
        SubscribeReqProto.SubscribeReq.Builder builder=SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqId(id);
        builder.setUserName("netty");
        builder.setProductName("in action");
        List<String> address=new ArrayList<String>();
        address.add("Hangzhou Binjiang");
        address.add("Zhejiang");
        address.add("China");
        builder.addAllAddress(address);
        return builder.build();
    }

}
