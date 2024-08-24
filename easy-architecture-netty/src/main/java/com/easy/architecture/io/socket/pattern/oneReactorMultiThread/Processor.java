package com.easy.architecture.io.socket.pattern.oneReactorMultiThread;


import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.*;

public class Processor {

    private static final ExecutorService service = Executors.newCachedThreadPool();

    public void process(SelectionKey selectionKey) {
        service.submit(() -> {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            int count = socketChannel.read(buffer);
            if (count < 0) {
                socketChannel.close();
                selectionKey.cancel();
                System.out.println("读取结束！");
                return null;
            } else if (count == 0) {
                return null;
            }
            System.out.println("读取内容：" + new String(buffer.array()));
            return null;
        });
    }


}