package netty.demo.linedecoder;

import netty.demo.common.server.Server;
import netty.demo.linedecoder.server.LineChildHandler;

/**
 * functional describe:启动服务端
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-8-23
 */
public class ServerStarter {
    public static void main(String[] args){
        new Server(new LineChildHandler()).start();
    }
}
