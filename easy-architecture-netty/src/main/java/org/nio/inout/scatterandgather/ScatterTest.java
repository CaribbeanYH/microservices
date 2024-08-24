package org.nio.inout.scatterandgather;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * Created by andilyliao on 16-6-1.
 */
public class ScatterTest {
    private static Charset charset = Charset.forName("UTF-8");
    private static void scatter(final String fileName) throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile(fileName, "r");
        //获取文件通道
        FileChannel channel = accessFile.getChannel();
        //创建两个缓冲区
        ByteBuffer headBuffer = ByteBuffer.allocate(3);
        ByteBuffer bodyBuffer = ByteBuffer.allocate(1024);

        ByteBuffer[] allBuffers = new ByteBuffer[]{headBuffer, bodyBuffer};
        // headBuffer 前10个字节
        // bodyBuffer 剩下的
        long n = channel.read(allBuffers);
        System.out.println("共读到多少字节:" + n);

        headBuffer.flip();
        //head缓冲区中的数据:qw
        System.out.println("head缓冲区中的数据:" + charset.decode(headBuffer));

        bodyBuffer.flip();
        //body缓冲区中的数据:ertyuiop
        System.out.println("body缓冲区中的数据:" + charset.decode(bodyBuffer));
        accessFile.close();
        channel.close();
    }
    private static void scatter2(final String fileName) throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile(fileName, "r");
        //获取文件通道
        FileChannel channel = accessFile.getChannel();
        //创建五个缓冲区
        ByteBuffer headBuffer = ByteBuffer.allocate(2);
        ByteBuffer bodyBuffer1 = ByteBuffer.allocate(3);
        ByteBuffer bodyBuffer2 = ByteBuffer.allocate(2);
        ByteBuffer bodyBuffer3 = ByteBuffer.allocate(2);
        ByteBuffer bodyBuffer4 = ByteBuffer.allocate(1);

        ByteBuffer[] allBuffers = new ByteBuffer[]{
                headBuffer,
                bodyBuffer1, bodyBuffer2,
                bodyBuffer3, bodyBuffer4,};
        //0从那个缓冲区开始被使用    使用3个缓冲区
        //会使用 headBuffer,bodyBuffer1,bodyBuffer2
        long n = channel.read(allBuffers, 0, 3);

        System.out.println("共读到多少字节:" + n);

        headBuffer.flip();
        //head缓冲区中的数据:qw
        System.out.println("head缓冲区中的数据:" + charset.decode(headBuffer));

        bodyBuffer1.flip();
        //body1缓冲区中的数据:ert
        System.out.println("body1缓冲区中的数据:" + charset.decode(bodyBuffer1));

        bodyBuffer2.flip();
        //body2缓冲区中的数据:yu
        System.out.println("body2缓冲区中的数据:" + charset.decode(bodyBuffer2));

        bodyBuffer3.flip();
        //body3，没有数据
        System.out.println("body3缓冲区中的数据:" + charset.decode(bodyBuffer3));

        bodyBuffer4.flip();
        //body4没有数据
        System.out.println("body4缓冲区中的数据:" + charset.decode(bodyBuffer4));

        accessFile.close();
        channel.close();
    }
    private static void writeData(final String fileName, String data) throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile(fileName, "rw");
        accessFile.writeBytes(data);
        accessFile.close();
    }
    public static void main(String[] args) throws IOException {
        final String fileName = "/opt/test1.log";
        //先写入10个字节数据 以便测试 scatter模式
        writeData(fileName, "qwertyuiop");

        /**----------Scatter------------*/
        //read(java.nio.ByteBuffer[])
        scatter(fileName);

        //read(java.nio.ByteBuffer[], int, int)
        scatter2(fileName);
    }
}
