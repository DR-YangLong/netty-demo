package netty.demo.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;

import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaders.setContentLength;

/**
 * functional describe:websocket处理
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-8-31
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {
    private static final Logger logger=LoggerFactory.getLogger(WebSocketServerHandler.class);
    private WebSocketServerHandshaker handshaker;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof FullHttpRequest){
            //普通http请求
            handleHttpRequest(ctx,(FullHttpRequest) msg);
        }
        if(msg instanceof WebSocketFrame){
            //websocket请求
            handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    /**
     * 处理普通http请求
     * @param ctx context
     * @param req request
     */
    private void handleHttpRequest(ChannelHandlerContext ctx,FullHttpRequest req){
        //解码失败并且不是websocket请求
        if(!req.getDecoderResult().isSuccess()||(!"websocket".equals(req.headers().get("Upgrade")))){
            sendHttpResponse(ctx,req,new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        WebSocketServerHandshakerFactory factory=
                new WebSocketServerHandshakerFactory("ws://127.0.0.1:8888/websocket",null,false);
        handshaker=factory.newHandshaker(req);
        if(handshaker==null){
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        }else {
            handshaker.handshake(ctx.channel(),req);
        }
    }

    /**
     * 处理websocket请求
     * @param ctx
     * @param frame
     */
    private void handleWebSocketFrame(ChannelHandlerContext ctx,WebSocketFrame frame){
        if(frame instanceof CloseWebSocketFrame){
            //关闭处理
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
        if(frame instanceof PingWebSocketFrame){
            //ping处理
            ctx.channel().writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        if(!(frame instanceof TextWebSocketFrame)){
            //非文本消息处理
            throw new UnsupportedOperationException(String.format("%s frame type not supported",frame.getClass().getName()));
        }
        String request=((TextWebSocketFrame) frame).text();
        logger.error(String.format("%s received %s",ctx.channel(),request));
        ctx.channel().writeAndFlush(new TextWebSocketFrame(request+"，netty websocket server："+new Date().toString()));
    }


    /**
     * 返回响应
     * @param ctx
     * @param req
     * @param res
     */
    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res){
        if(res.getStatus().code()!=200){
            ByteBuf byteBuf= Unpooled.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(byteBuf);
            byteBuf.release();
            setContentLength(res,res.content().readableBytes());
        }
        ChannelFuture f=ctx.channel().writeAndFlush(res);
        if(!isKeepAlive(req)||res.getStatus().code()!=200){
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }
}
