package netty.demo.retryandheart.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

/**
 * functional describe:用于拦截超时事件信息，不让信息向下传递影响真正的业务，接收到超时事件后，
 * 向服务端发起心跳请求
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-9-11
 */
@ChannelHandler.Sharable
public class ConnectorIdleStateTrigger extends ChannelInboundHandlerAdapter {
    //心跳信息
    private static final ByteBuf HEARTBEAT_INFO= Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Heartbeat", CharsetUtil.UTF_8));

    //拦截IdleStateHandler产生的事件
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.WRITER_IDLE) {
                //向服务端发出心跳信息
                ctx.writeAndFlush(HEARTBEAT_INFO.duplicate());
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
