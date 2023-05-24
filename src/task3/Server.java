package task3;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        SocketAddress address1 = new InetSocketAddress(9988);
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(address1);
        System.out.println("服务器已启动，正在等待连接...");
        Socket socket = serverSocket.accept();
        System.out.println("服务器已被" + socket.getRemoteSocketAddress().toString() + "连接！");
//            File file = new File("E:\\JavaEE\\lab2\\src\\task3\\data.txt");
        File file = new File("E:\\IDEA_Java\\lab2\\src\\task3\\data.txt");
        file.createNewFile();
        //获得输入输出流，并创建对象流，文件流
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());      // 将对象序列化传输通讯


        FileInputStream fileInputStream = new FileInputStream(file);    // 将消息对象持久化，实现历史记录功能
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream oosf = new ObjectOutputStream(fileOutputStream);
        ObjectInputStream oisf = new ObjectInputStream(fileInputStream);

        List<ChatMessage> lastMs = new ArrayList<>(); // 历史记录
//        if(file.length() != 0) {
//            while(true) {
//                ChatMessage m = (ChatMessage) oisf.readObject();
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
        Scanner scanner = new Scanner(System.in);

        Thread receiveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        ChatMessage message = (ChatMessage) ois.readObject();
                        if(message == null) {
                            System.out.println("无事发生...");
                            Thread.sleep(10);
                        }
                        else {
                            message.isServerMessage = false;
                            System.out.println("客户端：" + message.getMessage() + " " + new Date(message.getDate()).toString());
                            currMs.add(message);
                        }
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                }catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        Thread sendThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        System.out.println("Sever send send send");
                        String tp;
                        long time;
                        tp = scanner.next();
                        time = System.currentTimeMillis();
                        if (tp.equals("bye")) { // 如果输入bye则关闭连接
                            for (ChatMessage m : currMs) {
                                oosf.writeObject(m);
                            }
                            ois.close();
                            oos.close();
                            oosf.close();
                            socket.close();
                            break;
                        } else if (tp.equals("历史消息")) {
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
                        } else {
                            ChatMessage message = new ChatMessage(tp, time, true);
                            oos.writeObject(message);
                            currMs.add(message);
                        }
                    }
                }
                catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        sendThread.start();
        receiveThread.start();
    }
}
