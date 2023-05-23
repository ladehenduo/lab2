package task3;

import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        try {
            InetAddress address = InetAddress.getByName("10.14.3.182");
            SocketAddress address1 = new InetSocketAddress(address, 9988);
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(address1);
            System.out.println("服务器已启动，正在等待连接...");
            Socket socket = serverSocket.accept();
            System.out.println("服务器已被" + socket.getInetAddress().getHostAddress() + "连接！");
            File file = new File("E:\\JavaEE\\lab2\\src\\task3\\data.txt");
            file.createNewFile();
            //获得输入输出流，并创建对象流，文件流
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            ObjectInputStream ois = new ObjectInputStream(in);
            ObjectOutputStream oos = new ObjectOutputStream(out);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
