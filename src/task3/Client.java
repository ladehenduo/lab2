package task3;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

public class Client {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("192.168.31.51", 9988);
        System.out.println("连接服务器成功！");
        System.out.println("远程主机地址为：" + socket.getRemoteSocketAddress().toString());

        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

        File file = new File("E:\\IDEA_Java\\lab2\\src\\task3\\data.txt");
        file.createNewFile();
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream oisf = new ObjectInputStream(fileInputStream);
        List<ChatMessage> lastMs = new ArrayList<>(); // 历史记录

//        if(file.length() != 0) {
//            while(true) {
//                Object o = oisf.readObject();
//                ChatMessage m = (ChatMessage) o;
//                if(m != null) {
//                    lastMs.add(m);
//                }
//                else {
//                    break;
//                }
//            }
//            oisf.close();
//        }

        List<ChatMessage> currMs = new ArrayList<>(); // 本次聊天记录

        Scanner scanner = new Scanner(System.in); // 控制台交互
        Thread sendThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while(true) {
                        System.out.println("Client send send send");
                        String tp;
                        long time = System.currentTimeMillis();
                        tp = scanner.next();
                        if(tp.equals("bye")) {
//                            ois.close();
                            oos.close();
                            socket.close();
                            break;
                        }
                        else if(tp.equals("历史记录")) {
                            for (ChatMessage m : lastMs) {
                                Date date = new Date(m.getDate());
                                if (m.isServerMessage) {
                                    System.out.println("服务器：" + m.getMessage() + " " + date.toString());
                                } else {
                                    System.out.println("客户端：" + m.getMessage() + " " + date.toString());
                                }
                            }
                            for (ChatMessage m : currMs) {
                                Date date = new Date(m.getDate());
                                if (m.isServerMessage) {
                                    System.out.println("服务器：" + m.getMessage() + " " + date.toString());
                                } else {
                                    System.out.println("客户端：" + m.getMessage() + " " + date.toString());
                                }
                            }
                        }
                        else {
                            ChatMessage message = new ChatMessage(tp, time, false);
                            oos.writeObject(message);
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
                try{
                    while(true) {
                        ChatMessage message = (ChatMessage) ois.readObject();
                        if(message == null) {
                            System.out.println("无事发生...");
                            Thread.sleep(10);
                        }
                        else {
                            System.out.println("服务器：" + message.getMessage() + " " + new Date(message.getDate()));
                            currMs.add(message);
                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        System.out.println("开始通信");
        sendThread.start();
        receiveThread.start();
        System.out.println("都开始了");
    }
}
