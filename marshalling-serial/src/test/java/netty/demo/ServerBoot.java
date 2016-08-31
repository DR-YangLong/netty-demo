package netty.demo;

import netty.demo.common.server.Server;
import netty.demo.marshalling.ServerInit;

/**
 * functional describe:服务端启动
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-8-30
 */
public class ServerBoot {
    public static void main(String[] args){
        new Server(new ServerInit()).start();
    }
}
