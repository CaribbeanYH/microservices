package com.easy.architecture.io.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author yanghai10
 * @ClassName
 * @Description
 * @date 2024/9/19 21:12
 */
public class NioFileChannel {
    public static void main(String[] args) {
        File file = new File("/Users/yanghai10/Documents/athena/microservices/easy-architecture-netty/src/main/resources/nio.txt");
        try {
            // 创建FileInputStream，以该文件输入流创建FileChannel
            FileChannel inChannel = new FileInputStream(file).getChannel();
            // 以文件输出流创建FileBuffer，用以控制输出
            FileChannel outChannel = new FileOutputStream("/Users/yanghai10/Documents/athena/microservices/easy-architecture-netty/src/main/resources/nio_copy.txt").getChannel();
            outChannel.transferFrom(inChannel, 0, file.length());
            inChannel.transferTo(0, file.length(), outChannel);
            // 将FileChannel里的全部数据映射成ByteBuffer
            MappedByteBuffer buffer = inChannel.map(
                    FileChannel.MapMode.READ_ONLY, 0, file.length());
            // 使用GBK的字符集来创建解码器
            Charset charset = StandardCharsets.UTF_8;
            // 直接将buffer里的数据全部输出
            outChannel.write(buffer);
            // 再次调用buffer的clear()方法，复原limit、position的位置
            buffer.clear();
            // 创建解码器(CharsetDecoder)对象
            CharsetDecoder decoder = charset.newDecoder();
            CharsetEncoder encoder = charset.newEncoder();
            // 使用解码器将ByteBuffer转换成CharBuffer
            CharBuffer charBuffer = decoder.decode(buffer);
            ByteBuffer bf = encoder.encode(charBuffer);
            // CharBuffer的toString方法可以获取对应的字符串
            System.out.println(charBuffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
