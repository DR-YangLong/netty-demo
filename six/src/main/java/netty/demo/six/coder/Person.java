package netty.demo.six.coder;

import java.io.Serializable;

/**
 * package: netty.demo.five.coder <br/>
 * blog:<a href="http://dr-yanglong.github.io/">dr-yanglong.github.io</a><br/>
 * functional describe:netty发送对象
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/11/12
 */
public class Person implements Serializable{
    private static final long serialVersionUID = -6038853460669031879L;
    //用户id
    private Long userId;
    //用户姓名
    private String userName;
    //登录密码
    private String password;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Person{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
