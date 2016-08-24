package netty.demo.delimiterdecoder;

import netty.demo.common.Client.Client;

/**
 * functional describe:
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-8-24
 */
public class ClientBoot {
    public static void main(String[] args){
        new Client(new EchoClientChannelInit()).connect();
    }
}
