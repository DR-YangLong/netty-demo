package netty.demo.retryandheart.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * functional describe:服务端处理超时发起心跳事件handler，此处可用于将闲置的链接关闭
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-9-11
 */
@ChannelHandler.Sharable
public class AcceptorIdleStateTrigger extends ChannelInboundHandlerAdapter{
    private static final Logger logger= LoggerFactory.getLogger(AcceptorIdleStateTrigger.class);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.READER_IDLE) {
                logger.error("服务端触发读取超时时间");
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
