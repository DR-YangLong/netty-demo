package netty.demo.four.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.demo.common.ByteBufToBytes;
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
    private ByteBufToBytes reader;

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.debug("==========================进入服务端BusinessBoundHandler==================================");
        logger.debug("经过解码器后的对象类型："+msg.getClass());
        //自定义解码器将消息解码成了String
        String clientMsg=(String) msg;
            logger.debug("客户端发送来的消息：" + clientMsg);
        ctx.write("服务端业务处理成功！");
        logger.debug("==========================出服务端BusinessBoundHandler==================================");
    }
}
