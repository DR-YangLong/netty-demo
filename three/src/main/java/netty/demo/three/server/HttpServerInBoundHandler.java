package netty.demo.three.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import netty.demo.common.ByteBufToBytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * package: netty.demo.three.server <br/>
 * blog:<a href="http://dr-yanglong.github.io/">dr-yanglong.github.io</a><br/>
 * <p>
 * functional describe:服务端http模拟处理
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/11/6
 */
public class HttpServerInBoundHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(HttpServerInBoundHandler.class);
    private ByteBufToBytes reader;

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.debug("=========已进入server端channelRead======");
        //如果是http request,第一次进入
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            logger.debug("收到的客户端发送的请求头信息如下：\n" +
                    "messageType:" + request.headers().get("messageType") +
                    "\nbusinessType:" + request.headers().get("businessType"));
            //如果header中有内容长度不为0
            if (HttpHeaders.isContentLengthSet(request)) {
                reader = new ByteBufToBytes((int) HttpHeaders.getContentLength(request));
            }
        }
        //第二次进入
        if (msg instanceof HttpContent) {
            HttpContent httpContent = (HttpContent) msg;
            ByteBuf content = httpContent.content();
            reader.reading(content);
            //读取到后释放资源，此处只有一个in handler，消息不用往下传递
            content.release();
            if (reader.isEnd()) {
                String resultStr = new String(reader.readFull());
                logger.debug("客户端发送过来的消息:" + resultStr);
                //构造response
                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer("Hello Client!"
                        .getBytes()));
                response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain");
                response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, response.content().readableBytes());
                response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
                ctx.write(response);
                ctx.flush();
            }
        }
        logger.debug("=========出server端channelRead======");
    }
}
