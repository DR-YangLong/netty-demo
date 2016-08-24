package netty.demo.delimiterdecoder;

import netty.demo.common.server.Server;

/**
 * functional describe:
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-8-24
 */
public class ServerBoot {
    public static void main(String[] args){
        new Server(new EchoServerChannelInit()).start();
    }
}
