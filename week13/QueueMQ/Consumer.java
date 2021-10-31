package week13.QueueMQ;

import week13.QueueMQ.Topic;
import week13.QueueMQ.Producer;

class Consumer extends Thread{

    public Producer producer;
    public String topicName;

    public Consumer(){}

    public Consumer(Producer producer, String topicname){

        this.producer = producer;
        this.topicName = topicname;
    }

    public void getMQ (Producer producer, String topicname) {

        Topic topic;
        String msg = "";
        int offset = 0;

        for (int i = 0; i <= producer.topics.size(); i++) {

            if (producer.topics.get(i).topicName.equals(topicname)) {
                topic = producer.topics.get(i);
                topic.offSet = 0;
                msg = topic.topicMsg.get(topic.offSet).toString();

                while (!msg.equals("exit")) {
                    for (offset = 0; offset <= topic.topicMsg.size(); offset++) {
                        System.out.println("订阅topicname=" + topicname + ", 第" + offset + "个消息：" + msg);
                        msg = topic.topicMsg.get(offset).toString();
                    }
                }
            }
        }
    }

    @Override
    public void run() {
        this.getMQ(this.producer, this.topicName);
    }


}
