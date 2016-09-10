package netty.demo.privateprotoc.protocol;

/**
 * functional describe:消息类型领域模型
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-9-10
 */
public enum MessageType {
    BIZ_REQ((byte)0,"业务请求消息"),BIZ_RESP((byte)1,"业务响应消息"),BIZ_ONE_WAY((byte)2,"业务one way消息")
    ,LOGIN_REQ((byte)3,"登录请求消息"),LOGIN_RESP((byte)4,"登录响应消息"),HEART_BEAT_REQ((byte)5,"心跳请求"),
    HEART_BEAT_RESP((byte)6,"心跳应答消息");

    private byte value;
    private String remark;

    MessageType(byte value, String remark) {
        this.value = value;
        this.remark = remark;
    }

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
