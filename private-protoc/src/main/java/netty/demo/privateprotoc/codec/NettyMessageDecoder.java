package netty.demo.privateprotoc.codec;

import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import netty.demo.privateprotoc.protocol.Header;
import netty.demo.privateprotoc.protocol.NettyMessage;

/**
 * functional describe:消息解码器，用于NettyMessage的消息解码。由于协议是使用长度属性标识消息长度的byte流，
 * 所以需要基于消息LengthFieldBasedFrameDecoder
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-9-9
 */
public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder{
    private NettyMarshallingDecoder marshallingDecoder;

    public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset,
                               int lengthFieldLength,int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
        marshallingDecoder = MarshallingCodeCFactory.buildMarshallingDecoder();
    }


    public Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception{
        ByteBuf frame = (ByteBuf)super.decode(ctx, in);
        if(frame == null){
            return null;
        }
        //解析出消息头
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setCrcCode(frame.readInt());
        header.setLength(frame.readInt());
        header.setSessionID(frame.readLong());
        header.setType(frame.readByte());
        header.setPriority(frame.readByte());
        //解析出附加参数个数
        int size = frame.readInt();
        if(size > 0){
            Map<String, Object> attach = new HashMap<String, Object>(size);
            int keySize = 0;
            byte[] keyArray = null;
            String key = null;
            //获取附加参数
            for(int i=0; i<size; i++){
                keySize = frame.readInt();
                keyArray = new byte[keySize];
                in.readBytes(keyArray);
                key = new String(keyArray, "UTF-8");
                //获取到key和value对象，依赖marshalling的解码方式
                attach.put(key, marshallingDecoder.decode(ctx, frame));
            }
            key = null;
            keyArray = null;
            header.setAttachment(attach);
        }
        //还有可读数据，转换为消息体
        if(frame.readableBytes() > 0){
            message.setBody(marshallingDecoder.decode(ctx, frame));
        }
        message.setHeader(header);
        return message;
    }
}
