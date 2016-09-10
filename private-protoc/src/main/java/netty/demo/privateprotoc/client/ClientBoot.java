package netty.demo.privateprotoc.client;

import netty.demo.common.Client.Client;
import netty.demo.privateprotoc.channel.ClientChannelInit;

/**
 * functional describe:客户端启动
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-9-9
 */
public class ClientBoot {
    public static void main(String[] args){
        new Client(new ClientChannelInit()).connect();
    }
}
