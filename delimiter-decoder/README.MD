DelimiterBasedFrameDecoder(
            int maxFrameLength, boolean stripDelimiter, boolean failFast, ByteBuf... delimiters);
        maxFrameLength:单个消息体最大长度
        stripDelimiter:解析时是否将分割符剔除
        failFast:解析到maxFrameLength还没有匹配到分割符是否不在解析立即报错
        delimiters:分割符