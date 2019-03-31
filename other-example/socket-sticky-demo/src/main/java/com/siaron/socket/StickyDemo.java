package com.siaron.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author xielongwang
 * @create 2019-03-2610:28 PM
 * @email xielong.wang@nvr-china.com
 * @description
 * java socket半包、粘包问题解决方案
 * 1、以特殊字符串比如/r、/n作为数据的结尾，这样就可以区分数据包了。
 * 2、发送请求包的时候只发送固定长度的数据包，这样在服务端接收数据也只接收固定长度的数据，这种方法效率太低，不太合适频繁的数据包请求。
 * 3、在tcp协议的基础上封装一层数据请求协议，既数据包=数据包长度+数据包内容，这样在服务端就可以知道每个数据包的长度，也就可以解决半包、粘包问题
 */
public class StickyDemo {

    public static void main(String[] args) throws IOException, InterruptedException {
        new SocketServer().start();
        new SocketClient().start();
    }

    static class SocketClient extends Thread {
        Socket clientSocket = new Socket();

        public SocketClient() throws IOException {
            clientSocket.connect(new InetSocketAddress(8089));
        }

        @Override
        public void run() {
            String reqMessage1 = "HelloWorld！ from clientsocket ";
            String reqMessage = " this is test half packages!";
            try {
                for (int i = 0; i < 10; i++) {
                    OutputStream os = clientSocket.getOutputStream();
                    os.write(reqMessage1.getBytes());
                    os.write(reqMessage.getBytes());
                    os.flush();
                    System.out.println("SocketClient send message " + i + " " + reqMessage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (clientSocket != null) {
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    static class SocketServer extends Thread {
        ServerSocket serverSocket;

        public SocketServer() {
            try {
                serverSocket = new ServerSocket();
                serverSocket.bind(new InetSocketAddress(8089));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            int count = 0;
            Socket socket = null;
            try {
                socket = serverSocket.accept();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            while (true) {
                try {
                    byte[] byteBuffer = new byte[70];
                    StringBuffer receivBuffer = new StringBuffer();
                    InputStream reader = socket.getInputStream();
                    count = reader.read(byteBuffer);
                    if (count > 0) {
                        receivBuffer.append(new String(byteBuffer, 0, count));
                        System.out.println("SocketServer receive data from client-->:  " + receivBuffer.toString() + "end  ");
                    }
                    count = 0;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}