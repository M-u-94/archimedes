package la.archimedes.datay.mmap;

import cn.hutool.core.io.resource.ResourceUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.Buffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * read /write huge file fast using memory-mapping
 */
@Slf4j
public class FastFileAccessor {
    public static void main(String[] args) {

        String fullPath = ResourceUtil.getResource("mmap.csv").getPath();
        //500 mb
        long maxByteSize = 500*1024*1024;
        long beginTime = System.currentTimeMillis();
        try(RandomAccessFile raf = new RandomAccessFile(fullPath,"rw")) {
            log.info("begin writing {} bytes to disk...",maxByteSize);
            MappedByteBuffer buffer = raf.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 16+3);
            /*for(long i = 0;i<maxByteSize;i++){
                buffer.put((byte)i);
                if(i/1024 == 0 && i >= 1024){
                    buffer.put(System.lineSeparator().getBytes(StandardCharsets.UTF_8));
                }
            }*/
//            buffer.force();
            /*for(String line = raf.readLine();line != null;){
                log.info("检测到行是：{}",line);
            }*/
            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer);
            log.info(charBuffer.toString());

        } catch (FileNotFoundException e) {
           log.error("file not found in {}",fullPath);
        } catch (IOException e) {
           log.error("io exception occurs ,cause by :",e);
        }

    }








}
