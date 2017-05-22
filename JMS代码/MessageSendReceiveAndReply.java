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
  
public class MessageSendReceiveAndReply {  
  
	public static final String user = "admin";  
    public static final String password = "admin";  
    public static final String url = "tcp://localhost:61616";
    public static void main(String[] args) throws JMSException {  
        ActiveMQConnectionFactory factory=new ActiveMQConnectionFactory(user, password, url);  
        Connection connection=factory.createConnection();  
        connection.start();  
        //消息发送到这个Queue  
        Queue queue=new ActiveMQQueue("testQueue");  
        //消息回复到这个Queue  
        Queue replyQueue=new ActiveMQQueue("replayQueue");  
        final Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);  
        //创建一个消息，并设置它的JMSReplayTo为replayQueue  
        Message message=session.createTextMessage("Andy");  
        message.setJMSReplyTo(replyQueue);  
        //创建一个生产者来发送这个消息  
        MessageProducer producer=session.createProducer(queue);  
        producer.send(message);  
        //消息的接收者  
        MessageConsumer consumer=session.createConsumer(queue);  
        consumer.setMessageListener(new MessageListener(){  
  
            public void onMessage(Message m) {  
                try {  
                    //创建一个新的MessageProducer来发送一个回复消息  
                    MessageProducer producer=session.createProducer(m.getJMSReplyTo());  
                    producer.send(session.createTextMessage("Hello "+((TextMessage)m).getText()));  
                } catch (JMSException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }  
            }  
              
        });  
        //这个接收者用来接收回复的消息  
        MessageConsumer consumer2=session.createConsumer(replyQueue);  
        consumer2.setMessageListener(new MessageListener(){  
  
            public void onMessage(Message m) {  
                try {  
                    System.out.println(((TextMessage)m).getText());  
                } catch (JMSException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }  
            }  
              
        });  
    }  
  
}  
