package week13.QueueMQ;

import java.util.ArrayList;
import java.util.List;

public class Topic{

    public String topicName;
    public int offSet;
    public List topicMsg;

    public Topic(){
        this.topicName = "";
        this.offSet = 0;
        this.topicMsg = new ArrayList<String>();
    }

    public Topic(String topicname){
        this.topicName = topicname;
        this.offSet = 0;
        this.topicMsg = new ArrayList<String>();
    }

}

