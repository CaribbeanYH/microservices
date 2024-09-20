package com.easy.architecture.io.netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yanghai10
 * @ClassName
 * @Description
 * @date 2024/9/20 11:23
 */
@Slf4j
public class NettySliceApi {

    public static void main(String[] args) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();//可自动扩容
        buf.writeBytes(new byte[]{0, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        //零拷贝
        ByteBuf bb1 = buf.slice(0, 5);
        log.info("bb1 is {}", read(bb1));
        //零拷贝
        ByteBuf bb2 = buf.slice(5, 5);
        log.info("bb2 is {}", read(bb2));
        System.out.println("修改原始数据");
        buf.setByte(2, 5); //修改原始buf数据
        log.info("bb1 is {}", read(bb1)); //再打印bb1的结果，发现数据发生了变化
    }

    public static String read(ByteBuf buf) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < buf.readableBytes(); i++) {
            result.append(buf.getByte(i));
        }
        return result.toString();
    }
}
