package com.easy.architecture.io.netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Arrays;

/**
 * @author yanghai10
 * @ClassName
 * @Description
 * @date 2024/9/19 21:12
 */
public class NettyBufferApi {
    public static void main(String[] args) {
        //1.创建一个非池化的ByteBuf,大小为10字节
        ByteBuf buf = Unpooled.buffer(10);
        System.out.println("原始ByteBuf为:" + buf.toString());
        System.out.println("1.ByteBuf中的内容为:" + Arrays.toString(buf.array()));
        System.out.println();

        //2.写入一段内容
        byte[] bytes = {1,2,3,4,5};
        buf.writeBytes(bytes);
        System.out.println("写入的bytes为:" + Arrays.toString(bytes));
        System.out.println("写入一段内容后ByteBuf为:" + buf);
        System.out.println("2.ByteBuf中的内容为:" + Arrays.toString(buf.array()));
        System.out.println();

        //3.读取一段内容
        byte b1 = buf.readByte();
        byte b2 = buf.readByte();
        System.out.println("读取的bytes为:" + Arrays.toString(new byte[]{b1, b2}));
        System.out.println("读取一段内容后ByteBuf为:" + buf);
        System.out.println("3.ByteBuf中的内容为:" + Arrays.toString(buf.array()));
        System.out.println();

        //4.将读取的内容丢弃
        buf.discardReadBytes();
        System.out.println("丢弃已读取的内容后ByteBuf为:" + buf);
        System.out.println("4.ByteBuf中的内容为:" + Arrays.toString(buf.array()));
        System.out.println();

        //5.清空读写指针
        buf.clear();
        System.out.println("清空读写指针后ByteBuf为:" + buf);
        System.out.println("5.ByteBuf中的内容为:" + Arrays.toString(buf.array()));
        System.out.println();

        //6.再次写入一段内容,比第一段内容少
        byte[] bytes2 = {1,2,3};
        buf.writeBytes(bytes2);
        System.out.println("再写入的bytes2为:" + Arrays.toString(bytes2));
        System.out.println("再写入一段内容后ByteBuf为:" + buf);
        System.out.println("6.ByteBuf中的内容为:" + Arrays.toString(buf.array()));
        System.out.println();

        //7.将ByteBuf清空
        buf.setZero(0, buf.capacity());
        System.out.println("内容清空后ByteBuf为:" + buf);
        System.out.println("7.ByteBuf中的内容为:" + Arrays.toString(buf.array()));
        System.out.println();

        //8.再次写入一段超过容量的内容
        byte[] bytes3 = {1,2,3,4,5,6,7,8,9,10,11};
        buf.writeBytes(bytes3);
        System.out.println("写入超量的bytes3为:" + Arrays.toString(bytes3));
        System.out.println("写入超量内容后ByteBuf为:" + buf);
        System.out.println("8.ByteBuf中的内容为:" + Arrays.toString(buf.array()));
        System.out.println();
    }
}

/*
 池化和非池化的区别主要是申请内存缓存空间以及缓存空间的使用上,体现为内存复用.
 在申请内存缓存空间方面:
    pool:池化申请的时候会申请一个比当前所需内存空间更大的内存空间,这就好比一个快递柜,为此netty提供了buf分配管理器专门用来处理这种事情,来创建或复用ByteBuf.
    unpool:非池化申请只会申请特定大小能够使用的内存缓存空间,使用完之后立刻释放,这就像直接把快递放到你的手中,你所在的位置就是开辟的内存空间.
 在缓存空间使用方面:
    pool:池化申请的内存空间有一定扩容容积,也就是这个快递柜可以存放多个快递,只需要找到对应的方格即可存放,同样buf分配管理器来复用已经创建好的内存空间,在创建ByteBuf的时候已经开辟3中大小的内存块
        normal:16MN
        small:8KB
        tiny:512B
    unpool:毫无疑问,非池化的方式必然是每次都会再去开辟内存空间的.
 */
