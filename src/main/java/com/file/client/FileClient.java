package com.file.client;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @Author:
 * @Description 
 * @Date: 下午5:27 2023/10/31
 */
@Slf4j
public class FileClient {

    /**
     * 设置需要访问的服务端IP
     */
    public static final String IP = "10.237.3.89";

    /**
     * 设置需要访问的服务端端口
     */
    public static final int PORT = 9999;
    /**
     * 指定读取的文件地址(要被传输的文件) 例如要把文件A传输到家里电脑上，这里就是A文件的地址
     */
    public static final String PATH = "/Users/admin/Downloads/ioa.zip";

    public static void main(String[] args) {
        FileChannel fileChannel = null;
        SocketChannel socketChannel = null;
        try {
            // 1. 创建并配置 服务器套接字通道 ServerSocketChannel
            socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress(IP, PORT));
            //socketChannel.configureBlocking(false);
            // 2. 从文件输入流中获取文件通道 ( FileChannel )
            fileChannel = new FileInputStream(PATH).getChannel();
            long startTime = System.currentTimeMillis();
            // 3. 零拷贝传输数据, 注意记录每次拷贝的起始位置
            long transferLen;
            long totalCount = 0;
            log.info("文件开始传输******");
            // 使用零拷贝将文件数据传到服务器, 循环终止条件是传输结果小于等于 0
            while ((transferLen = fileChannel.transferTo(totalCount, Long.MAX_VALUE, socketChannel)) > 0) {
                totalCount += transferLen;
                log.info("文件大小：{}", transferLen);
            }
            log.info("文件传输完毕, 用时:{}ms", System.currentTimeMillis() - startTime);
        } catch (IOException e) {
            log.error("客户端传输异常{}", e);
        } finally {
            // 5. 释放资源
            if (null != socketChannel) {
                try {
                    socketChannel.close();
                } catch (IOException e) {
                    log.error("客户端关闭流socketChannel异常{}", e);
                }
            }
            if (null != fileChannel) {
                try {
                    fileChannel.close();
                } catch (IOException e) {
                    log.error("客户端关闭流fileChannel异常{}", e);
                }
            }
        }
    }
}
