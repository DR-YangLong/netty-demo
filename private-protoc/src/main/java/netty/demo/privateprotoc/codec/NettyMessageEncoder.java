package netty.demo.privateprotoc.codec;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;
import netty.demo.privateprotoc.protocol.NettyMessage;

/**
 * functional describe:消息编码器，用于NettyMessage的编码。最终生成byte流
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-9-9
 */
public class NettyMessageEncoder extends MessageToMessageEncoder<NettyMessage> {
    private NettyMarshallingEncoder marshallingEncoder;
    public NettyMessageEncoder() throws IOException{
        this.marshallingEncoder =MarshallingCodeCFactory.buildMarshallingEncoder();
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, List<Object> out) throws Exception {
            ByteBuf sendBuf= Unpooled.buffer();
        sendBuf.writeInt(msg.getHeader().getCrcCode());
        sendBuf.writeInt(msg.getHeader().getLength());
        sendBuf.writeLong(msg.getHeader().getSessionID());
        sendBuf.writeByte(msg.getHeader().getType());
        sendBuf.writeByte(msg.getHeader().getPriority());
        sendBuf.writeInt(msg.getHeader().getAttachment().size());
        String key=null;
        byte[] keyArrary=null;
        Object value =null;
        for(Map.Entry<String,Object> param:msg.getHeader().getAttachment().entrySet()){
            key=param.getKey();
            keyArrary=key.getBytes(CharsetUtil.UTF_8.name());
            sendBuf.writeInt(keyArrary.length);
            sendBuf.writeBytes(keyArrary);
            value=param.getValue();
            marshallingEncoder.encode(ctx,value,sendBuf);
        }
        key=null;
        keyArrary=null;
        value=null;
        if(msg.getBody()!=null){
            marshallingEncoder.encode(ctx,msg.getBody(),sendBuf);
        }
        //消息长度写入，从第四个byte开始，向后写4个byte
        int length=sendBuf.readableBytes();
        sendBuf.setInt(4,length);
        out.add(sendBuf);
    }
}
