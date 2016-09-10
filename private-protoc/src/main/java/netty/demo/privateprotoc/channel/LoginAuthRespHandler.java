package netty.demo.privateprotoc.channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.demo.privateprotoc.protocol.Header;
import netty.demo.privateprotoc.protocol.MessageType;
import netty.demo.privateprotoc.protocol.NettyMessage;

/**
 * functional describe:服务端handler，接受登录请求，并根据逻辑作出回应
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-9-10
 */
public class LoginAuthRespHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(LoginAuthRespHandler.class);

    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        NettyMessage message = (NettyMessage) msg;
        //判断业务类型，处理本handler要处理的业务，如果不是，将消息向下传递
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_REQ.getValue()) {
            logger.error("客户端已登入");
            String body = (String) message.getBody();
            logger.error("客户端请求体： " + body);
            ctx.writeAndFlush(buildLoginResponse());
        }
    }

    private NettyMessage buildLoginResponse() {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.LOGIN_RESP.getValue());
        message.setHeader(header);
        message.setBody("登录成功，可以执行业务请求！");
        return message;
    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
