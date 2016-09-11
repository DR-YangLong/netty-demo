package netty.demo.retryandheart.client;

import io.netty.channel.ChannelHandler;

/**
 * functional describe:客户端存储handler的接口定义，
 * 用于建立重连时复用第一次的handler
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-9-11
 */
public interface ChannelHandlerHolder {
    /**
     * 将handler的获取延迟到子类中，并可复用
     * @return ChannelHandlers
     */
    ChannelHandler[] handlers();
}
