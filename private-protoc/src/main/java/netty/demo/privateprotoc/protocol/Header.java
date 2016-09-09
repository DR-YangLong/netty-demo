package netty.demo.privateprotoc.protocol;

import java.util.HashMap;
import java.util.Map;

/**
 * functional describe:netty协议头
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-9-9
 */
public final class Header {
    //版本校验 32bit
    private int crcCode=0xabef0101;
    //消息长度 32bit
    private int length;
    //会话id 64bit
    private long sessionID;
    //消息类型 8bit
    private byte type;
    //消息优先级 8bit
    private byte priority;
    //附件 变长
    private Map<String,Object> attachment=new HashMap<String,Object>();

    public int getCrcCode() {
        return crcCode;
    }

    public void setCrcCode(int crcCode) {
        this.crcCode = crcCode;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public long getSessionID() {
        return sessionID;
    }

    public void setSessionID(long sessionID) {
        this.sessionID = sessionID;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getPriority() {
        return priority;
    }

    public void setPriority(byte priority) {
        this.priority = priority;
    }

    public Map<String, Object> getAttachment() {
        return attachment;
    }

    public void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }
}
