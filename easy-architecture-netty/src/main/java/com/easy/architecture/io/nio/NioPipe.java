package com.easy.architecture.io.nio;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * @author yanghai10
 * @ClassName
 * @Description
 * @date 2024/9/19 21:12
 */
public class NioPipe {
    public static void main(String[] args) throws IOException {

        //初始化Pipe实例
        Pipe pipe = Pipe.open();

        // 获取写通道
        Pipe.SinkChannel skChannel = pipe.sink();

        String testData = "Test Data to Check java NIO Channels Pipe.";

        ByteBuffer buffer = ByteBuffer.allocate(512);

        buffer.put(testData.getBytes());

        buffer.flip();
        //向写通道写入数据
        while (buffer.hasRemaining()) {
            skChannel.write(buffer);
        }
        //获得读取数据通道
        Pipe.SourceChannel sourceChannel = pipe.source();
        ByteBuffer buffer1 = ByteBuffer.allocate(512);

        buffer1.clear();
        //将读取数据写到控制台
        while (sourceChannel.read(buffer1) > 0) {

            //flip方法将当前读取位置设置为0， limit设置为写入数据的size
            buffer1.flip();

            while (buffer1.hasRemaining()) {
                char ch = (char) buffer1.get();
                System.out.print(ch);
            }

            //clear方法将buffer的limit设置为其容量capacity， 将position设置为0
            buffer1.clear();
        }

    }
}
