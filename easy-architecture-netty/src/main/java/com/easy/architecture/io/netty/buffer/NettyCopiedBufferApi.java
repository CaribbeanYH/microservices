package com.easy.architecture.io.netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yanghai10
 * @ClassName
 * @Description
 * @date 2024/9/20 10:22
 */
@Slf4j
public class NettyCopiedBufferApi {

    public static void main(String[] args) {
        ByteBuf header = ByteBufAllocator.DEFAULT.buffer();//可自动扩容
        header.writeCharSequence("header", CharsetUtil.UTF_8);
        ByteBuf body = ByteBufAllocator.DEFAULT.buffer();
        body.writeCharSequence("body", CharsetUtil.UTF_8);
        //修改原始ByteBuf中的值，不会会影响到allBb
        ByteBuf allBb = Unpooled.copiedBuffer(header, body);
        log.info("allBb is {}", allBb.toString(CharsetUtil.UTF_8));
        header.setCharSequence(0, "Newer0", CharsetUtil.UTF_8); //case
        log.info("allBb is {}", allBb.toString(CharsetUtil.UTF_8));
    }
}
