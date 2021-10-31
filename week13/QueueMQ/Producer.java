package week13.QueueMQ;

import week13.QueueMQ.Topic;

import java.util.*;

class Producer extends Thread {

    public List<Topic> topics = new ArrayList<Topic>();
    public String topicName;

    public Producer() {
        Topic topic = new Topic();
        this.topics.add(topic);
        this.topicName = "";
    }

    public Producer(Topic topic) {
        this.topics.add(topic);
    }

    //增加1个topic
    public void addTopic(String topicname, String msg){
        Topic newtopic = new Topic(topicname);
        newtopic.offSet = 1;
        newtopic.topicMsg.add(msg);
        topics.add(newtopic);
        this.topicName = topicname;
    }

    //发送消息
    public void sendMQ (String topicname, String msg){

        if (topics.size() == 1 ){
            addTopic(topicname, msg);
            return;
        }

        for(int i=0; i<=topics.size(); i++){
            if (topics.get(i).topicName.equals(topicname)){
                topics.get(i).offSet++;
                topics.get(i).topicMsg.add(msg);
                return;
            }
        }
        addTopic(topicname, msg);
    }

    @Override
    public void run(){

        try{
                for (int i=0; i<=10; i++){
                this.sendMQ(this.topicName, "发出第" + i+ "个消息");
                Thread.sleep(1000);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        //String msg = "";
        //Scanner input = new Scanner(System.in);
        //while(!msg.equals("exit")){
        //    msg = input.next();
        //    this.sendMQ(this.topicName, msg);
        //}

    }

}
