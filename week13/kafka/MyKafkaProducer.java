package week13.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import scala.concurrent.impl.FutureConvertersImpl;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class MyKafkaProducer extends Thread{

    private Properties properties;
    private KafkaProducer<Integer, String>  producer;
    private String topic;  //主题

    public MyKafkaProducer(String topic){

        //构建连接配置
        properties = new Properties();

        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "my-producer");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //构造Client
        producer = new KafkaProducer<Integer, String>(properties);
        this.topic = topic;
    }

    //同步调用
    public void run() {
        int num = 0;
        String msg = "Kafka practice msg: " + num;
        while(num < 20){
            try{
                //发送消息，同步调用
                RecordMetadata recordMetadata = producer.send(new ProducerRecord<>(topic, msg)).get();
                //等get到结果了，才能执行
                System.out.println(recordMetadata.offset() + "-->"
                              + recordMetadata.partition() + "-->"
                              + recordMetadata.topic());
                TimeUnit.SECONDS.sleep(2);
                num++;
            }catch (InterruptedException e){
                e.printStackTrace();
            }catch (ExecutionException e){
                e.printStackTrace();
            }
        }
    }

    //异步调用
    /*
    public void run() {
        int num = 0;
        while(num < 20){
            String msg = "Kafka practice msg: " + num;
            try{
                //发送消息，异步调用
                producer.send(new ProducerRecord<>(topic, msg), new Callback(){
                    @Override
                    //回调函数
                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                        System.out.println("callback: " + recordMetadata.offset()
                                           + "-->" + recordMetadata.partition());
                    }
                });
                TimeUnit.SECONDS.sleep(2);
                num++;
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    */

    public static void main(String[] args){
        new MyKafkaProducer("topic111").start();
    }


}




















