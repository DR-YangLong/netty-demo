package netty.demo.five.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.demo.five.coder.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * package: netty.demo.three.server <br/>
 * blog:<a href="http://dr-yanglong.github.io/">dr-yanglong.github.io</a><br/>
 * <p>
 * functional describe:服务端经过自定义解码器后执行的业务处理器
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/11/6
 */
public class BusinessBoundHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(BusinessBoundHandler.class);

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.debug("==========================进入服务端BusinessBoundHandler==================================");
        logger.debug("经过解码器后的对象类型："+msg.getClass());
        //自定义解码器将消息解码成了String
        Person person=(Person) msg;
            logger.debug("客户端发送来的消息：" + person.toString());
        String response="接收Person完成!";
        //响应字符串要转换成ByteBuf
        //申请响应ByteBuf
        ByteBuf send=ctx.alloc().buffer(4*response.length());
        //写入响应字符数组
        send.writeBytes(response.getBytes());
        //发送
        ctx.write(send);
        ctx.flush();
        logger.debug("==========================出服务端BusinessBoundHandler==================================");
        //send.release();
    }
}
