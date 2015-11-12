package netty.demo.four.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import netty.demo.common.ByteBufToBytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * package: netty.demo.three.client <br/>
 * blog:<a href="http://dr-yanglong.github.io/">dr-yanglong.github.io</a><br/>
 * <p>
 * functional describe:http客户端模拟处理
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/11/6
 */
public class HttpClientInBoundHandler extends ChannelInboundHandlerAdapter {
    private ByteBufToBytes reader;
    private static final Logger logger = LoggerFactory.getLogger(HttpClientInBoundHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.debug("================进入客户端InBoundHandler channelRead============");
        //如果是response
        if (msg instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) msg;
            logger.debug("客户端收到的响应CONTENT_TYPE:" + response.headers().get(HttpHeaders.Names.CONTENT_TYPE));
            if (HttpHeaders.isContentLengthSet(response)) {
                reader = new ByteBufToBytes((int) HttpHeaders.getContentLength(response));
            }
        }
        if (msg instanceof HttpContent) {
            HttpContent httpContent = (HttpContent) msg;
            ByteBuf content = httpContent.content();
            reader.reading(content);
            content.release();
            if (reader.isEnd()) {
                String resultStr = new String(reader.readFull());
                logger.debug("收到的服务端的消息是：" + resultStr);
                ctx.close();
            }
        }
        logger.debug("================出客户端InBoundHandler channelRead============");
    }
}
