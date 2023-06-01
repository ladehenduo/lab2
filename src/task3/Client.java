package task3;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.util.*;

public class Client {
    private static InputStream in;
    private static OutputStream out;
    private static ObjectInputStream ois;
    private static ObjectOutputStream oos;

    public static Date date = new Date();
    static void sendMessage(String ms) {
        synchronized (date) {
            ChatMessage message = new ChatMessage(ms, System.currentTimeMillis(), false);
            try {
                oos.writeObject(message);
            } catch (IOException e) {
                System.out.println("发送失败：" + e.getMessage());
            }
        }
    }
    static void receiveMessage() {
        synchronized (date) {
            try {
                if(ois.available() > 0) {
                    ChatMessage message = (ChatMessage) ois.readObject();
                    if(message.getMessage().equals("历史记录")) {
                        System.out.println("---------------------以下为历史消息-----------------------");
                        do {
                            message = (ChatMessage) ois.readObject();
                            if(message.isServerMessage) {
                                System.out.println("时间：" + new Date(message.getDate()).toString() + " 服务器：" + message.getMessage());
                            }
                            else {
                                System.out.println("时间：" + new Date(message.getDate()).toString() + "客户端：" + message.getMessage());
                            }
                        } while(!message.getMessage().equals("over"));
                    }
                    else {
                        System.out.println("时间：" + new Date(message.getDate()).toString() + " 服务器：" + message.getMessage());
                    }
                }
            } catch (IOException e) {
                System.out.println("接受失败：" + e.getMessage());
            } catch (ClassNotFoundException e) {
                System.out.println("接受失败：" + e.getMessage());
            }
        }
    }
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("192.168.88.241", 9988);
        if(socket.isConnected()) {
            System.out.println("连接服务器成功！");
            System.out.println("远程主机地址为：" + socket.getRemoteSocketAddress().toString());
            // Client
            in = socket.getInputStream();   // 获得IO流
            out = socket.getOutputStream();
            oos = new ObjectOutputStream(out);
            ois = new ObjectInputStream(in);
        }
        else {
            System.out.println("连接失败！");
            return ;
        }
        Scanner scanner = new Scanner(System.in); // 控制台交互

        Thread sendThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while(true) {
                        String tp;
                        tp = scanner.next();
                        if(tp.equals("bye")) {
                            ois.close();
                            oos.close();
                            socket.close();
                            break;
                        }
                        else {
                            sendMessage(tp);
                        }
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread receiveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    receiveMessage();
                }
            }
        });
        System.out.println("开始通信");
        sendThread.start();
        receiveThread.start();
    }
}
