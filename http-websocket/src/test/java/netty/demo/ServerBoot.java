package netty.demo;

import netty.demo.common.server.Server;
import netty.demo.websocket.WebsocketServerInit;

/**
 * functional describe:服务端启动
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-9-8
 */
public class ServerBoot {
    public static void main(String[] args){
        new Server(new WebsocketServerInit()).start();
    }
}
