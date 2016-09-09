package netty.demo.privateprotoc.protocol;

/**
 * functional describe:netty协议封装
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-9-9
 */
public final class NettyMessage {
    private Header header;
    private Object body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Netty message header:"+header;
    }
}
