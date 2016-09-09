package netty.demo.udp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

/**
 * functional describe:udp发包端
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-9-9
 */
public class UdpClient {
    private static final Logger logger= LoggerFactory.getLogger(UdpClient.class);

    public static void connect(int port) throws Exception{
        EventLoopGroup group=new NioEventLoopGroup();
        try {
            Bootstrap bootstrap=new Bootstrap();
            bootstrap.group(group).channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST,true)
                    .handler(new ChineseProverbClientHandler());
            Channel channel=bootstrap.bind(0).sync().channel();
            //广播消息
            channel.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("谚语字段查询？", CharsetUtil.UTF_8),new InetSocketAddress("255.255.255.255",port))).sync();
            //此处bug，由于是广播，有的是收不到回应的
            if(channel.closeFuture().await(15000)){
                logger.error("查询超时！");
            }
        } finally {
            group.shutdownGracefully();
        }
    }

     private  static class ChineseProverbClientHandler extends SimpleChannelInboundHandler<DatagramPacket>{
        private static final Logger logger=LoggerFactory.getLogger(ChineseProverbClientHandler.class);

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
            String response=msg.content().toString(CharsetUtil.UTF_8);
            if(response.startsWith("查询结果")){
                logger.error(response);
                ctx.close();
            }
        }
    }


    public static void main(String[] args) throws Exception{
        UdpClient.connect(8888);
    }
}
