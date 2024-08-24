package com.easy.architecture.io.inout;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class HoleFile {
    public static void main(String[] args) throws Exception {
        FileOutputStream fos = new FileOutputStream("/opt/tmp.txt");
        FileChannel fc = fos.getChannel();
        ByteBuffer bbuf = ByteBuffer.allocateDirect(1024);
        long value = 10 * 1024 * 1024;
        for(int i = 1; i < 1025; i = i * 2) {
            bbuf.put((byte)1);
            bbuf.flip();
            fc.position(i * value);
            fc.write(bbuf);
            bbuf.clear();
        }
        fc.close();
        fos.close();
    }
}
