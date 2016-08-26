package netty.demo.httpserver;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * functional describe:
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-8-26
 */
public class ServerInit extends ChannelInitializer<SocketChannel>{
    private String path;
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline =ch.pipeline();
        pipeline.addLast("http-decoder",new HttpRequestDecoder());
        pipeline.addLast("http-aggregator",new HttpObjectAggregator(65536));
        pipeline.addLast("http-encoder",new HttpResponseEncoder());
        pipeline.addLast("http-chunked",new ChunkedWriteHandler());
        pipeline.addLast("fileServerHandler",new HttpFileServerHandler(path));
    }

    public ServerInit(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
