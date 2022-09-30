package com.gugu;

import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author minmin
 * @date 2022/09/30
 */
public class KeepWriteTest {
    @Test
    public void keepWrite() {
        // 模拟日志一直被写入的情况
        Path path = Paths.get(System.getenv("test.file.path"));
        while (true){
            try(FileOutputStream fileOutputStream = new FileOutputStream(path.toFile(), true)){
                fileOutputStream.write(1);
                fileOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testMain() throws Exception {
        Main.main(null);
    }
}
