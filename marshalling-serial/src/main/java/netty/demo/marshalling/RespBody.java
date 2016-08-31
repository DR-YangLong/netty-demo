package netty.demo.marshalling;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

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

    @Override
    public String toString() {
        final Optional<String> resut=desc.stream().reduce((x,y)-> x+y);
        return "RespBody{" +
                "reqId=" + reqId +
                ", desc=" + resut.orElseGet(()->"无描述") +
                '}';
    }
}
