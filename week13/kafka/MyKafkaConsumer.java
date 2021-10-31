package week13.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class MyKafkaConsumer extends Thread{

    private Properties properties;
    private KafkaConsumer<Integer, String> consumer;
    private String topic;  //主题

    public MyKafkaConsumer(String topic){

        //构建连接配置
        properties = new Properties();

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, "my-consumer");
        //反序列化
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "my-gid");  //要加入的group
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000"); //超时，心跳
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000"); //自动提交（批量）
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); //新group消费位置

        consumer = new KafkaConsumer<>(properties);
        this.topic = topic;
    }

    public void run(){
        //死循环不断消费消息
        while (true){
            //绑定订阅主题
            //注：Collections.singleton返回一个单元素&不可修改set集合
            //同样的还有singletonList, singletonMap
            consumer.subscribe(Collections.singleton(this.topic));
            //接收消息POLL()
            ConsumerRecords<Integer, String> consumerRecords = consumer.poll(Duration.ofSeconds(1));
            consumerRecords.forEach(record -> System.out.println(record.key() + "->" +
                                               record.value() + "->" + record.offset()));
        }
    }

    public static void main(String[] args){
        //拉取 topic111 主题的消息
        new MyKafkaConsumer("topic111").start();
    }

}



































