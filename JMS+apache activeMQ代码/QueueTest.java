package com.llh.jms;
import javax.jms.Connection;  
import javax.jms.JMSException;  
import javax.jms.Message;  
import javax.jms.MessageConsumer;  
import javax.jms.MessageListener;  
import javax.jms.MessageProducer;  
import javax.jms.Queue;  
import javax.jms.Session;  
import javax.jms.TextMessage;  
  

import org.apache.activemq.ActiveMQConnectionFactory;  
import org.apache.activemq.command.ActiveMQQueue;  
public class QueueTest {
	public static final String user = "admin";  
    public static final String password = "admin";  
    public static final String url = "tcp://localhost:61616";
	public static void main(String[] args) throws JMSException {  
        ActiveMQConnectionFactory factory=new ActiveMQConnectionFactory(user, password, url);  
        Connection connection=factory.createConnection();  
        connection.start();  
        //创建一个Queue  
        Queue queue=new ActiveMQQueue("testQueue");  
        //创建一个Session  
        Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);  
        //注册消费者1  
        MessageConsumer consumer1=session.createConsumer(queue);  
        consumer1.setMessageListener(new MessageListener(){  
            public void onMessage(Message m) {  
                try {  
                    System.out.println("Consumer1 get: "+((TextMessage)m).getText());  
                } catch (JMSException e) {  
                    e.printStackTrace();  
                }  
            }  
              
        });  
        //注册消费者2  
        MessageConsumer consumer2=session.createConsumer(queue);  
        consumer2.setMessageListener(new MessageListener(){  
            public void onMessage(Message m) {  
                try {  
                    System.out.println("Consumer2 get: "+((TextMessage)m).getText());  
                } catch (JMSException e) {  
                    e.printStackTrace();  
                }  
            }  
              
        });  
        //创建一个生产者，然后发送多个消息。  
        MessageProducer producer=session.createProducer(queue);  
        for(int i=0;i<10;i++){  
            producer.send(session.createTextMessage("Message:"+i));  
        }  
    }  
}
