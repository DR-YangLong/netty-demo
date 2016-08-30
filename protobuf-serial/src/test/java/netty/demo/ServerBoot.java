package netty.demo;

import netty.demo.common.server.Server;
import netty.demo.protobuf.ServerInit;

/**
 * functional describe:服务端
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-8-30
 */
public class ServerBoot {
    public static void main(String[] args) {
        Server server = new Server(new ServerInit());
        server.start();
    }
}
