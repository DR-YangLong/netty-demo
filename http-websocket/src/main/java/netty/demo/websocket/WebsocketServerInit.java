package netty.demo.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * functional describe:
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-8-31
 */
public class WebsocketServerInit extends ChannelInitializer<SocketChannel>{
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline=ch.pipeline();
        //请求解码，应答编码
        pipeline.addLast("http-codec",new HttpServerCodec());
        //将http消息多个部分组合成一条完整的http消息
        pipeline.addLast("aggregator",new HttpObjectAggregator(65536));
        //异步文件处理
        pipeline.addLast("http-chunked",new ChunkedWriteHandler());
        pipeline.addLast("handler",new WebSocketServerHandler());
    }
}
