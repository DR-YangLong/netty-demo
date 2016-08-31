package netty.demo;

import netty.demo.common.Client.Client;
import netty.demo.marshalling.ClientInit;

/**
 * functional describe:客户端启动
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-8-30
 */
public class ClientBoot {
    public static void main(String[] args){
        new Client(new ClientInit()).connect();
    }
}
