package netty.demo;

import com.google.protobuf.InvalidProtocolBufferException;

import java.util.ArrayList;
import java.util.List;

import netty.demo.protobuf.SubscribeReqProto;

/**
 * Unit test for simple App.
 */
public class AppTest{
    private static byte[] encode(SubscribeReqProto.SubscribeReq req) {
        return  req.toByteArray();
    }

    private static SubscribeReqProto.SubscribeReq decode(byte[] body) throws InvalidProtocolBufferException {
        return SubscribeReqProto.SubscribeReq.parseFrom(body);
    }


    public static SubscribeReqProto.SubscribeReq createReq(){
        SubscribeReqProto.SubscribeReq.Builder builder=SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqId(1);
        builder.setUserName("netty");
        builder.setProductName("in action");
        List<String> address=new ArrayList<String>();
        address.add("Hangzhou Binjiang");
        address.add("Zhejiang");
        address.add("China");
        builder.addAllAddress(address);
        return builder.build();
    }

    public static void main(String[] args) throws InvalidProtocolBufferException{
        SubscribeReqProto.SubscribeReq req=createReq();
        System.out.println("req:\n"+req.toString());
        SubscribeReqProto.SubscribeReq req1=decode(encode(req));
        System.out.println("req1:\n"+req1.toString());
        System.out.println(req.equals(req1));
    }
}
