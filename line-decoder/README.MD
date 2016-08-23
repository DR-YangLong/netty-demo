换行符作为消息结束符，LineBasedFrameDecoder遇到换行符：\n或\r\n时将以此位置作为消息结束位置，从可读索引到结束位置将消息分割。

LineBasedFrameDecoder(final int maxLength, final boolean stripDelimiter, final boolean failFast);
maxLength:解析时消息最大长度，如果解析时超过这个长度还没有遇到分隔符，则抛出异常。
stripDelimiter：解析后的消息是否剔除分割符，默认true，剔除。
failFast：如果解析发现数据超过maxLength时立即报错，不等整个数据帧解析完后才报错。
