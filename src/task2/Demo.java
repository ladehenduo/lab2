package task2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Demo {
    public static void main(String[] args) {
        String[] menu = {"咖喱饭",  "小碗菜", "麻辣拌", "大盘鸡面", "摇滚炒鸡", "舌尖大师", "琥珀炸鸡", "鸡丝油泼面", "羊汤烩面"};
        List<String> list = new ArrayList<>();
        Random random = new Random();
        long l = System.currentTimeMillis();

        int maxlen = 10;
        long maxTime = 15000;
        Integer lock = 1;
        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    synchronized (lock) {
                        long r = System.currentTimeMillis();
                        if(r - l >= maxTime) break;
                        if(list.size() >= maxlen) {
                            System.out.println("快吃！快吃！餐盘用完了！");
                        }
                        else{
                            int tp = random.nextInt(9);
                            list.add(menu[tp]);
                            System.out.println("新菜出炉：" + menu[tp]);
                        }
                        try {
                            lock.wait(500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
        Thread consumer1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    synchronized (lock) {
                        long r = System.currentTimeMillis();
                        if(r - l >= maxTime) break;
                        if(list.size() == 0) {
                            System.out.println(Thread.currentThread().getName() + "说：" + "快做！快做！饿死我了！");
                        }
                        else{
                            System.out.println(Thread.currentThread().getName() + "说：" + list.get(0) + "真好吃！^_^");
                            list.remove(0);
                        }
                        try {
                            lock.notifyAll();
                            lock.wait(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
        Thread consumer2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    synchronized (lock) {
                        long r = System.currentTimeMillis();
                        if(r - l >= maxTime) break;
                        if(list.size() == 0) {
                            System.out.println(Thread.currentThread().getName() + "说：" + "快做！快做！饿死我了！");
                        }
                        else{
                            System.out.println(Thread.currentThread().getName() + "说：" + list.get(0) + "真好吃！^_^");
                            list.remove(0);
                        }
                        try {
                            lock.wait(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
        Thread consumer3 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    synchronized (lock) {
                        long r = System.currentTimeMillis();
                        if(r - l >= maxTime) break;
                        if(list.size() == 0) {
                            System.out.println(Thread.currentThread().getName() + "说：" + "快做！快做！饿死我了！");
                        }
                        else{
                            System.out.println(Thread.currentThread().getName() + "说：" + list.get(0) + "真好吃！^_^");
                            list.remove(0);
                        }
                        try {
                            lock.wait(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
        Thread consumer4 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    synchronized (lock) {
                        long r = System.currentTimeMillis();
                        if(r - l >= maxTime) break;
                        if(list.size() == 0) {
                            System.out.println(Thread.currentThread().getName() + "说：" + "快做！快做！饿死我了！");
                        }
                        else{
                            System.out.println(Thread.currentThread().getName() + "说：" + list.get(0) + "真好吃！^_^");
                            list.remove(0);
                        }
                        try {
                            lock.wait(10000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
        producer.start();
        consumer1.start();
        consumer2.start();
        consumer3.start();
        consumer4.start();
    }
}
