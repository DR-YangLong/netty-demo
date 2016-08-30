package netty.demo.protobuf;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * functional describe:设置handler超类
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-8-30
 */
public class SuperInit extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline=ch.pipeline();
        //解决半包解码
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        //proto解码
       // pipeline.addLast(new ProtobufDecoder(SubscribeReqProto.SubscribeReq.getDefaultInstance()));
        //pipeline.addLast(new ProtobufDecoder(SubscribeRespProto.SubscribeResp.getDefaultInstance()));
        pipeline.addLast(new ProtobufDecoder(ProtoProtocolProto.ProtoProtocol.getDefaultInstance()));
        //解决半包编码
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        //proto编码
        pipeline.addLast(new ProtobufEncoder());
        //TODO 子类添加InboundHandler
    }
}
