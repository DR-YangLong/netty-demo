package netty.demo.file;

import java.io.File;
import java.io.RandomAccessFile;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.FileRegion;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import netty.demo.common.server.Server;

/**
 * functional describe:处理文件服务handler初始化。用LineBasedFrameDecoder解码，遇到/r或/r/n则拆分消息
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-9-9
 */
public class ChannelInit extends ChannelInitializer<SocketChannel> {

    /**
     * 初始化解码器和编码器
     * @param ch
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline=ch.pipeline();
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8))
                .addLast(new LineBasedFrameDecoder(1024))
                .addLast(new StringDecoder(CharsetUtil.UTF_8))
                .addLast(new FileServerHandler());
    }

    /**
     * handler
     */
    private static class FileServerHandler extends SimpleChannelInboundHandler<String>{
        private static final String CR=System.getProperty("line.separator");

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
           File file=new File(msg);
            if(file.exists()){
                if(!file.isFile()){
                    ctx.writeAndFlush("Not a file:"+file+CR);
                    return;
                }
                ctx.write(file+""+file.length()+CR);
                RandomAccessFile randomAccessFile=new RandomAccessFile(msg,"r");
                FileRegion region=new DefaultFileRegion(randomAccessFile.getChannel(),0,randomAccessFile.length());
                ctx.write(region);
                ctx.writeAndFlush(CR);
                randomAccessFile.close();
            }else {
                ctx.writeAndFlush("File not found:"+file+CR);
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }


    public static void main(String[] args){
        new Server(new ChannelInit()).start();
    }
}
