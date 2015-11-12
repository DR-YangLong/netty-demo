package netty.demo.four.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * package: netty.demo.four.server <br/>
 * blog:<a href="http://dr-yanglong.github.io/">dr-yanglong.github.io</a><br/>
 * functional describe:服务端自定义编码器，要将业务处理器发送过来的String消息编码成HttpResponse发送给客户端
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/11/11
 */
public class StringEncoder extends ChannelOutboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(StringDecoder.class);
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        logger.debug("=========已进入server端自定义编码器======");
        logger.debug("编码前的数据对象类型：" + msg.getClass());
        String serverMsg = (String) msg;
        //构造httpResponse
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(
                serverMsg.getBytes()));
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain");
        response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        ctx.write(response);
        ctx.flush();
        logger.debug("=========出server端自定义编码器======");
    }
}
