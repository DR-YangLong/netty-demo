package netty.demo.marshalling;

import java.io.Serializable;
import java.util.List;

/**
 * functional describe:响应体
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-8-30
 */
public class RespBody implements Serializable {
    private static final long serialVersionUID = -960191689371692092L;
    private int reqId;
    private List<String> desc;

    public int getReqId() {
        return reqId;
    }

    public void setReqId(int reqId) {
        this.reqId = reqId;
    }

    public List<String> getDesc() {
        return desc;
    }

    public void setDesc(List<String> desc) {
        this.desc = desc;
    }
}
