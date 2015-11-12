package netty.demo.four.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import netty.demo.common.ByteBufToBytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * package: netty.demo.four.server <br/>
 * blog:<a href="http://dr-yanglong.github.io/">dr-yanglong.github.io</a><br/>
 * functional describe:服务端自定义解码器
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/11/11
 */
public class StringDecoder extends ChannelInboundHandlerAdapter{
    private static Logger logger  = LoggerFactory.getLogger(StringDecoder.class);
    private ByteBufToBytes reader;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.debug("=========已进入server端自定义解码器======");
        logger.debug("解码前的数据对象类型："+msg.getClass());
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
                //这里相当于解码
                String resultStr = new String(reader.readFull());
                logger.debug("客户端发送过来的消息:" + resultStr);
                //调用下一个InboundHandler
                ctx.fireChannelRead(resultStr);
            }
        }
        logger.debug("=========出server端自定义解码器======");
    }
}
