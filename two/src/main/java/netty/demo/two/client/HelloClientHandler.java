package netty.demo.two.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * package: netty.demo.one.client <br/>
 * blog:<a href="http://dr-yanglong.github.io/">dr-yanglong.github.io</a><br/>
 * <p>
 * functional describe:接收Server消息，并相应
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/11/4
 */
public class HelloClientHandler extends ChannelInboundHandlerAdapter{
    private static final Logger logger= LoggerFactory.getLogger(HelloClientHandler.class);

    /**
     * 接收服务端信息，并打印
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("====================收到服务端响应HelloClientIntHandler.channelRead======================");
        ByteBuf result = (ByteBuf) msg;
        byte[] result1 = new byte[result.readableBytes()];
        result.readBytes(result1);
        logger.debug("Server said:" + new String(result1));
        //释放资源，如果有多个handler，后面的handler还需要此资源
        result.release();
        logger.info("====================客户端处理服务端消息结束======================");
        //如果这是最终的handler，响应处理完毕，关闭链接
        ctx.close();
    }

    /**
     * 链接打开后执行的操作
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.debug("===================HelloClientHandler.channelActive==================");
        String msg="Hello,this is client.";
        //申请ByteBuf
        ByteBuf send=ctx.alloc().buffer(4*msg.length());
        //消息写入ByteBuf
        send.writeBytes(msg.getBytes());
        //发送
        ctx.write(send);
        ctx.flush();
    }
}
