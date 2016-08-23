package netty.demo.linedecoder;

import netty.demo.common.Client.Client;
import netty.demo.linedecoder.client.LineChildHandler;

/**
 * functional describe:启动客户端
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-8-23
 */
public class ClientStarter {
    public static void main(String[] args){
        new Client(new LineChildHandler()).connect();
    }
}
