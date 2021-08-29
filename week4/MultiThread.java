package week4;

import java.util.Scanner;
import java.util.concurrent.*;

public class MultiThread {

    static  Object obj = new Object();

    //1：wait+notify
    static class Thread1 extends Thread{
        @Override
        public void run(){
            synchronized (obj){
                try{
                    System.out.println("-----------子线程1启动-----------");
                    for (int i=0; i<=10; i++) {
                        System.out.println("子线程1计数：" + i);
                        Thread.sleep(1000);
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println("-----------子线程1退出-----------");
                obj.notify();  //释放对象锁
            }

        }
    }

    //2：Callable+Future
    static class Thread2 implements Callable<Boolean> {
        public Boolean call(){
            try{
                System.out.println("-----------子线程2启动-----------");
                for (int i=0; i<=10; i++) {
                    System.out.println("子线程2计数：" + i);
                    Thread.sleep(1000);
                    if (Thread.interrupted()){
                        return false;
                    }
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("-----------子线程2退出-----------");
            return true;
        }
    }

    //3：join
    static class Thread3 extends Thread{
        public void run(){
            try{
                System.out.println("-----------子线程3启动-----------");
                for (int i=0; i<=10; i++) {
                    System.out.println("子线程3计数：" + i);
                    Thread.sleep(1000);
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("-----------子线程3退出-----------");
        }
    }


    public static void main(String[] args) throws Exception{

        Scanner input = new Scanner(System.in);
        System.out.print("请输入线程调用方法（1-wait+notify；2-Callable+Future）；3-join，结束输入#：");
        String val = input.next();  //等待输入值

        switch (val){

            case "1":
                System.out.println("您输入的是：1：wait+notify");
                synchronized (obj){   //主线程获取对象锁
                    Thread1 th1 = new Thread1();
                    th1.start();
                    obj.wait();
                }
                break;

            case "2":
                System.out.println("您输入的是：2：Callable+Future");
                int timeout = 15;
                Boolean result = false;
                Callable<Boolean> th2 = new Thread2();
                ExecutorService executor = Executors.newSingleThreadExecutor();
                Future<Boolean> future = executor.submit(th2);
                try{
                    result = future.get(timeout, TimeUnit.SECONDS);
                }catch (InterruptedException e){
                    e.printStackTrace();
                    System.out.println("线程2中断出错");
                    future.cancel(true);
                }catch (ExecutionException e){
                    e.printStackTrace();
                    System.out.println("线程2服务出错");
                    future.cancel(true);
                }catch (TimeoutException e){
                    e.printStackTrace();
                    System.out.println("超时");
                    future.cancel(true);
                }finally {
                    System.out.println("线程2服务关闭");
                    executor.shutdown();
                }
                break;

            case "3":
                System.out.println("您输入的是：3：join");
                Thread3 th3 = new Thread3();
                th3.start();
                th3.join();
                break;

            case "#":
                System.out.println("你输入了\"#\"，程序已经退出！");
                break;

            default:
                System.out.println("输入非法，程序已经退出！");
                break;
        }
        input.close(); // 关闭资源
        System.out.println("-----------主线程退出！-----------");
    }

}
