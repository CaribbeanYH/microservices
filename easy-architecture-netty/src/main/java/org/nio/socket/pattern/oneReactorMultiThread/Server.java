package org.nio.socket.pattern.oneReactorMultiThread;



import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Server {

    public static void main(String[] args) {
        try {
            Selector selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(8080));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (selector.select() > 0) {
                for (SelectionKey sk : selector.selectedKeys()) {
                    selector.selectedKeys().remove(sk);
                    if (sk.isReadable()) {
                        Processor processor = (Processor) sk.attachment();
                        processor.process(sk);
                    } else if (sk.isAcceptable()) {
                        ServerSocketChannel acceptServerSocketChannel = (ServerSocketChannel) sk.channel();
                        SocketChannel socketChannel = acceptServerSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        SelectionKey key = socketChannel.register(selector, SelectionKey.OP_READ);

                        //绑定处理器线程
                        key.attach(new Processor());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
