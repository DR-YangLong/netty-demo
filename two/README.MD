## 探究handler执行顺序

## demo使用
1. server包下HelloServer运行main函数。
2. client包下HelloClient运行main函数。

## 疑问：
>1. workgroup和bossgroup分别负责什么？

    workgroup是真正负责I/O读写操作的线程组，用于后续的Channel绑定，业务处理的handler就绑定在channel上。bossgroup负责监听TCP链接，bossGroup线程组实际就是Acceptor线程池。
>2. 如何保持一个长链接？

    模拟session会话。
>3. handler是在什么时候触发的？有哪些类型。handler中的函数是在什么时候触发的？

    inboundhandler，在建立链接之后，IO读取时触发。ouboundHandler在IO往客户端写时触发。
>4. 为什么要从ChannelHandlerContext申请ByteBuf.

>5. ctx.write(ByteBuf buf);ctx.flush();做了什么。

    执行write，如果在当前线程中，会调用下一个handler的write方法。如果不在，生成一个写任务，放入线程调度器中。flush方法，如果当前线程中，则执行写IO，不再则生成调度任务。

## 执行流程
根据注册顺序
> 先执行InboundHandler然后执行outBoundHandler

> inboundHandler按注册顺序执行，outBoundHandler按注册顺序逆序执行。

> 注意outBoundHandler注册要在最后一个inboundHandler之前.

如果在inboundHandler中调用**ctx.write**方法，将会直接跳过下一个inboundHandler开始执行outBoundHandler,outBoundHandler执行完后再执行接下来的inBoundHandler.这样的话需要注意ByteBuf此时在后续的inboundHandler中已经没用了，因为已经被释放.