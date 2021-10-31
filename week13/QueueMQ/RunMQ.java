package week13.QueueMQ;

import java.util.*;

public class RunMQ{

    public static List<Topic> topics = new ArrayList<Topic>();

    public static void main (String[] args) {

        String topicname = "topic111";
        Topic topic = new Topic(topicname);

        Producer producer = new Producer(topic);
        Consumer consumer = new Consumer(producer, topicname);

        producer.start();
        consumer.start();
    }
}
