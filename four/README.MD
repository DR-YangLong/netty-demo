## 本例说明自定义解码、编码器的使用
> 运行结果

``` bash
15:19:52 DEBUG netty.demo.four.server.HttpServer - 服务端启动，地址：192.168.1.52:8000
15:20:10 DEBUG n.demo.four.server.StringDecoder - =========已进入server端自定义解码器======
15:20:10 DEBUG n.demo.four.server.StringDecoder - 解码前的数据对象类型：class io.netty.handler.codec.http.DefaultHttpRequest
15:20:10 DEBUG n.demo.four.server.StringDecoder - 收到的客户端发送的请求头信息如下：
messageType:normal
businessType:testServerState
15:20:10 DEBUG n.demo.four.server.StringDecoder - =========出server端自定义解码器======
15:20:10 DEBUG n.demo.four.server.StringDecoder - =========已进入server端自定义解码器======
15:20:10 DEBUG n.demo.four.server.StringDecoder - 解码前的数据对象类型：class io.netty.handler.codec.http.DefaultLastHttpContent
15:20:10 DEBUG n.demo.four.server.StringDecoder - 客户端发送过来的消息:Hello HttpServer!
15:20:10 DEBUG n.d.f.server.BusinessBoundHandler - ==========================进入服务端BusinessBoundHandler==================================
15:20:10 DEBUG n.d.f.server.BusinessBoundHandler - 经过解码器后的对象类型：class java.lang.String
15:20:10 DEBUG n.d.f.server.BusinessBoundHandler - 客户端发送来的消息：Hello HttpServer!
15:20:10 DEBUG n.demo.four.server.StringDecoder - =========已进入server端自定义编码器======
15:20:10 DEBUG n.demo.four.server.StringDecoder - 编码前的数据对象类型：class java.lang.String
15:20:10 DEBUG n.demo.four.server.StringDecoder - =========出server端自定义编码器======
15:20:10 DEBUG n.d.f.server.BusinessBoundHandler - ==========================出服务端BusinessBoundHandler==================================
15:20:10 DEBUG n.demo.four.server.StringDecoder - =========出server端自定义解码器======
```