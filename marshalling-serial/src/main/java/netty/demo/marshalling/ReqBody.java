package netty.demo.marshalling;

import java.io.Serializable;

/**
 * functional describe:请求体
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-8-30
 */
public class ReqBody implements Serializable{
    private static final long serialVersionUID = 84312500295263495L;
    private int reqId;
    private String userName;

    public int getReqId() {
        return reqId;
    }

    public void setReqId(int reqId) {
        this.reqId = reqId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "ReqBody{" +
                "reqId=" + reqId +
                ", userName='" + userName + '\'' +
                '}';
    }
}
