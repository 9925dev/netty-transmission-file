# netty-transmission-file
基于netty的文件传输

## FileClient
这个类是客户端，要把文件传输到服务端。公司电脑启动这个类。

1、设置需要访问的服务端IP
public static final String IP = "10.237.3.89";
2、设置需要访问的服务端端口
public static final int PORT = 9999;
3、指定读取的文件地址(要被传输的文件) 例如要把文件A传输到家里电脑上，这里就是A文件的地址
public static final String PATH = "/Users/admin/Downloads/ioa.zip";

## FileServer 
这个类是服务端，要接收文件的服务。家里电脑启动这个类。
  
1、设置服务端端口
public static final int PORT = 9999;
2、指定保存文件地址 例如要把公司文件A，传输到家里电脑上，存为B文件，这里就是B文件的地址
public static final String PATH = "/Users/admin/Downloads/yinyue31.zip";

## 测试
需要在一个局域网下，台式机可能不方便。最好有一台电脑是笔记本。
1、公司电脑和家里电脑都用idea打开这个工程
2、家里电脑运行FileServer类中的main方法
3、公司电脑运行FileClient类中的main方法
