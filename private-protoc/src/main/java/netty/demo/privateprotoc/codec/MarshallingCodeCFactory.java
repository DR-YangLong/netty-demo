package netty.demo.privateprotoc.codec;

import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

import io.netty.handler.codec.marshalling.DefaultMarshallerProvider;
import io.netty.handler.codec.marshalling.DefaultUnmarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;

/**
 * functional describe:marshalling解码，编码器工厂
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-8-30
 */
public class MarshallingCodeCFactory {
    /**
     * 解码器
     * @return decoder
     */
    public static NettyMarshallingDecoder buildMarshallingDecoder() {
        final MarshallerFactory marshallerFactory = Marshalling.getMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        UnmarshallerProvider provider=new DefaultUnmarshallerProvider(marshallerFactory,configuration);
        NettyMarshallingDecoder decoder=new NettyMarshallingDecoder(provider,1024);
        return decoder;
    }

    /**
     * 编码器
     * @return
     */
    public static NettyMarshallingEncoder buildMarshallingEncoder(){
        final MarshallerFactory marshallerFactory = Marshalling.getMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        MarshallerProvider provider=new DefaultMarshallerProvider(marshallerFactory,configuration);
        NettyMarshallingEncoder encoder=new NettyMarshallingEncoder(provider);
        return encoder;
    }
}
