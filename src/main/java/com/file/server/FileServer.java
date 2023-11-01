package com.file.server;

import lombok.extern.slf4j.Slf4j;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @Author:
 * @Description
 * @Date: 下午5:28 2023/10/31
 */
@Slf4j
public class FileServer {

    /**
     * 设置服务端端口
     */
    public static final int PORT = 9999;
    /**
     * 指定保存文件地址 例如要把公司文件A，传输到家里电脑上，存为B文件，这里就是B文件的地址
     */
    public static final String PATH = "/Users/admin/Downloads/yinyue31.zip";

    public static void main(String[] args) {
        FileChannel fileChannel = null;
        SocketChannel socketChannel = null;
        try {
            // 1. 创建并配置 服务器套接字通道 ServerSocketChannel
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //设置端口
            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
            // 注意这里使用阻塞模式, 不调用该代码
            // serverSocketChannel.configureBlocking(false);
            // 2. 获取文件通道
            fileChannel = new FileOutputStream(PATH).getChannel();
            log.info("服务端启动完毕******等待接收文件");
            // 3. 阻塞等待
            socketChannel = serverSocketChannel.accept();
            log.info("服务端开始接收文件******");
            long startTime = System.currentTimeMillis();
            // 4. 零拷贝核心操作
            fileChannel.transferFrom(socketChannel, 0, Long.MAX_VALUE);
            log.info("服务端接收文件结束，耗时{}ms", System.currentTimeMillis() - startTime);
        } catch (IOException e) {
            log.error("服务端接收异常{}", e);
        } finally {
            // 5. 释放资源
            if (null != socketChannel) {
                try {
                    socketChannel.close();
                } catch (IOException e) {
                    log.error("服务端关闭流socketChannel异常{}", e);
                }
            }
            if (null != fileChannel) {
                try {
                    fileChannel.close();
                } catch (IOException e) {
                    log.error("服务端关闭流socketChannel异常{}", e);
                }
            }
        }
    }
}
