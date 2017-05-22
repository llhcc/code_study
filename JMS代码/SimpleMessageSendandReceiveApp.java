package com.llh.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class SimpleMessageSendandReceiveApp {
	public static final String user = "admin";  
    public static final String password = "admin";  
    public static final String url = "tcp://localhost:61616";  
    public static final String queueName = "test_queue1";  
    public static final String messageBody = "Hello JMS!";  
    public static final boolean transacted = false;  
    public static final boolean persistent = false;  
      //如果发送端停止，则可以获取Persistent的消息。而刚才发送的non persistent消息已经丢失了。
    //如果发送一个non persistent消息, 而刚好这个时候没有消费者在监听, 这个消息也会丢失.
    public static void main(String[] args){  
        Connection connection = null;  
        Session session = null;  
          
        try{  
            // create the connection  
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);  
            connection = connectionFactory.createConnection();  
            connection.start();  
              
            // create the session  
            session = connection.createSession(transacted, Session.AUTO_ACKNOWLEDGE);  
            Destination destination = session.createQueue(queueName);  
              
            // create the producer  
            MessageProducer producer = session.createProducer(destination);  
            if (persistent){  
                producer.setDeliveryMode(DeliveryMode.PERSISTENT);  
            }else{  
                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);  
            }  
              
            // create text message  
            Message message = session.createTextMessage(messageBody);  
              
            // send the message  
            producer.send(message);  
            System.out.println("Send message: " + ((TextMessage)message).getText());  
              
            // create the consumer  
            MessageConsumer consumer = session.createConsumer(destination);  
            // blocking till receive the message  
            Message recvMessage = consumer.receive();  
            System.out.println("Receive message: " + ((TextMessage)recvMessage).getText());  
              
        }catch (Exception e){  
            e.printStackTrace();  
        }finally{  
            try{  
                // close session and connection  
                if (session != null){  
                    session.close();  
                }  
                if (connection != null){  
                    connection.close();  
                }  
            }catch (Exception e){  
                e.printStackTrace();  
            }  
        }  
    }
}
