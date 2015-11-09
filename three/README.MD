## 实现的目的：
``` bash
1、Client向Server发送http请求。

2、Server端对http请求进行解析。

3、Server端向client发送http响应。

4、Client对http响应进行解析。
```

##  netty相关类
```bash
 1、HttpRequestEncoder：对httpRequest进行编码。

2、HttpRequestDecoder：把流数据解析为httpRequest。

3、HttpResponseEncoder：对httpResponse进行编码。

4、HttpResponseEncoder：把流数据解析为httpResponse。
```

Encoder实质上是一个OutBoundHandler,Decoder实质上是一个InBoundHandler;
HttpRequestDecoder========extends======HttpObjectDecoder========extends======ByteToMessageDecoder========extends======ChannelInboundHandlerAdapter
ByteToMessageDecoder.channelRead()====>ByteToMessageDecoder.callDecode()循环读取ByteBuf===>HttpObjectDecoder.decode()对请求进行解码，将请求头和请求内容分开放入list out中===>
ByteToMessageDecoder.channelRead()中循环调用ctx.fireChannelRead(out.get(i))，多次调用HttpServerInBoundHandler;