package netty.demo;

import netty.demo.common.server.Server;
import netty.demo.httpserver.ServerInit;

/**
 * Unit test for simple App.
 */
public class AppTest {
    public static void main(String args[]){
        new Server(new ServerInit("/home/dream")).start();
    }
}
