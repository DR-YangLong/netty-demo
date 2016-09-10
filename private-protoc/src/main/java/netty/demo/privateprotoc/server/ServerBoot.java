package netty.demo.privateprotoc.server;

import netty.demo.common.server.Server;
import netty.demo.privateprotoc.channel.ServerChannelInit;

/**
 * functional describe: 服务端启动
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-9-9
 */
public class ServerBoot {
    public static void main(String[] args){
        new Server(new ServerChannelInit()).start();
    }
}
