### Server与Client建立连接后，会执行以下的步骤：
``` bash
1、Client向Server发送消息：Are you ok?

2、Server接收客户端发送的消息，并打印出来。

3、Server端向客户端发送消息：I am ok!

4、Client接收Server端发送的消息，并打印出来，通讯结束。
```

### 涉及到的类有4个：
``` bash
1、HelloServer ：server类，启动Netty server

2、HelloServerInHandler：server的handler，接收客户端消息，并向客户端发送消息

3、HelloClient：client类，建立于Netty server的连接

4、HelloClientIntHandler：client的handler，接收server端的消息，并向服务端发送消息
```

## demo使用
1. server包下HelloServer运行main函数。
2. client包下HelloClient运行main函数。

## 疑问：
>1. workgroup和bossgroup分别负责什么？
>2. 如何保持一个长链接？
>3. handler是在什么时候触发的？有哪些类型。handler中的函数是在什么时候触发的？
>4. 消息发送为什么要从ChannelHandlerContext申请ByteBuf.
>5. ctx.write(ByteBuf buf);ctx.flush();做了什么。