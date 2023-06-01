package task3;

import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.*;

public class Server {
    private static InputStream in;
    private static OutputStream out;
    private static ObjectInputStream ois;
    private static ObjectOutputStream oos;
    private static ObjectOutputStream outfile;
    private static ObjectInputStream infile;
    public static List<ChatMessage> listLast;
    public static Object date = new Object();
    public static Socket socket;

    public static void sendMessage(String ms) {
        ChatMessage message = new ChatMessage(ms, System.currentTimeMillis(), true);
        try {
            listLast.add(message);
            oos.writeObject(message);
        } catch (IOException e) {
            System.out.println("发送失败：" + e.getMessage());
        }
    }
    public static void sendMessage(ChatMessage ms) {
        try {
            listLast.add(ms);
            oos.writeObject(ms);
        } catch (IOException e) {
            System.out.println("发送失败：" + e.getMessage());
        }
    }
    public static void receiveMessage() {
        try {
            ChatMessage message = (ChatMessage) ois.readObject();
            if(message.getMessage().equals("历史消息")) {
                for(int i = 0; i < listLast.size(); i++) {
                    sendMessage(listLast.get(i));
                }
                sendMessage("over");
            }
            else if(message.getMessage().equals("bye")) {
                outfile.flush();
                socket.shutdownOutput();
                socket.shutdownInput();
                socket.close();
            }
            else {
                System.out.println("时间：" + new Date(message.getDate()).toString() + "客户端：" + message.getMessage());
                listLast.add(message);
                outfile.writeObject(message);
            }
        } catch (IOException e) {
            System.out.println("接收消息失败：" + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("接收消息失败：" + e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException{
        InetAddress address = InetAddress.getByName("192.168.31.51");  //
        SocketAddress address1 = new InetSocketAddress(address, 9988);
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(address1);
        System.out.println("服务器已启动，正在等待连接...");
        socket = serverSocket.accept();
        System.out.println("服务器已被" + socket.getInetAddress().getHostAddress() + "连接！");
        File file = new File("E:\\IDEA_Java\\lab2\\src\\task3\\data.txt");  // desktop
//        File file = new File("E:\\JavaEE\\lab2\\src\\task3\\data.txt");  // laptop
//        file.createNewFile();
        listLast = new ArrayList<>();
        outfile = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file, false)));
        try {
            infile = new ObjectInputStream(new FileInputStream(file));
            do {
                try {
                    ChatMessage message = (ChatMessage) infile.readObject();
                    if(message == null) break;
                } catch (ClassNotFoundException e) {
                    System.out.println("读取历史消息失败：" + e.getMessage());
                }
            }while (true);
        }catch (EOFException e) {
            System.out.println("无历史记录");
        }
        //获得输入输出流，并创建对象流，文件流
        out = socket.getOutputStream();
        in = socket.getInputStream();
        ois = new ObjectInputStream(in);
        oos = new ObjectOutputStream(out);
        Scanner scanner = new Scanner(System.in);

        Thread sendThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String tp;
                while(true) {
                    tp = scanner.next();
                    if(tp.equals("bye")) {
                        try {
                            outfile.flush();
                            sendMessage("bye");
                            socket.shutdownInput();
                            socket.shutdownOutput();
                            socket.close();
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    }
                    else if(tp.equals("历史记录")) {
                        synchronized (date) {
                            System.out.println("---------------------以下为历史消息-----------------------");
                            for(int i = 0; i < listLast.size(); i++) {
                                ChatMessage message = listLast.get(i);
                                if(message.isServerMessage) {
                                    System.out.println("时间：" + new Date(message.getDate()).toString() + " 服务器：" + message.getMessage());
                                }
                                else {
                                    System.out.println("时间：" + new Date(message.getDate()).toString() + "客户端：" + message.getMessage());
                                }
                            }
                        }
                    }
                    else {
                        sendMessage(tp);
                    }
                }
            }
        });
        Thread receiveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if(socket.isClosed()) {
                        try {
                            outfile.flush();
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    }
                    else {
                        receiveMessage();
                    }
                }
            }
        });
        sendThread.start();
        receiveThread.start();
    }
}
