package netty.demo.privateprotoc.channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
    //心跳列表,链接到服务端的客户端列表，key是ip和端口
    private Map<String, Boolean> nodeCheck = new ConcurrentHashMap<String, Boolean>(8);
    //连接白名单
    private List<String> whiteList = Arrays.asList("127.0.0.1", "192.168.1.45");

    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        NettyMessage message = (NettyMessage) msg;
        //判断业务类型，处理本handler要处理的业务，如果不是，将消息向下传递
        if (message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_REQ.getValue()) {
            logger.error("客户端已登入");
            String body = (String) message.getBody();
            logger.error("客户端请求体： " + body);
            String nodeIndex = ctx.channel().remoteAddress().toString();
            //重复登录检查
            if (nodeCheck.containsKey(nodeIndex)) {
                ctx.writeAndFlush(buildLoginResponse("请勿重复登录！"));
            } else {
                String address = ((InetSocketAddress) ctx.channel().remoteAddress()).getAddress().getHostAddress();
                boolean isWhite = whiteList.contains(address);
                if (isWhite) {
                    ctx.writeAndFlush(buildLoginResponse("登录成功，可以执行业务请求！"));
                } else {
                    //不在白名单
                    ctx.writeAndFlush(buildLoginResponse(address + "节点被禁止！"));
                }
            }
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    private NettyMessage buildLoginResponse(String body) {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.LOGIN_RESP.getValue());
        message.setHeader(header);
        message.setBody(body);
        return message;
    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
