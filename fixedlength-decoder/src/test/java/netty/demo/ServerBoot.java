package netty.demo;

import netty.demo.common.server.Server;
import netty.demo.fixedlengthdecoder.EchoServerChannelInit;

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
