package org.nio.inout;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
public class RandomFileChannelTest
{
	public static void main(String[] args)
		throws IOException
	{
		File f = new File("a.txt");
		try{
			// 创建一个RandomAccessFile对象
			RandomAccessFile raf = new RandomAccessFile(f, "rw");
			// 获取RandomAccessFile对应的Channel
			FileChannel randomChannel = raf.getChannel();
			// 将Channel中所有数据映射成ByteBuffer
			ByteBuffer buffer = randomChannel.map(FileChannel
				.MapMode.READ_WRITE, 0 , f.length());
			// 把Channel的记录指针移动到最后
			randomChannel.position(f.length());
			// 将buffer中所有数据输出
			randomChannel.write(buffer);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
