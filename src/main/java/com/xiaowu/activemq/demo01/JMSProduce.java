package com.xiaowu.activemq.demo01;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author chutaojing
 * @version 1.0
 * @description
 * @date 2021-01-31
 * @see
 * @since 1.0
 */
public class JMSProduce {
    public static final String ACTIVE_URL = "tcp://192.168.145.128:61616";
    public static final String QUEUE_NAME = "test_queue";
    
    public static void main(String[] args) {
        //activemq连接工厂
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVE_URL);
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;
        try {
            //获取连接
            connection = factory.createConnection();
            //启动连接
            connection.start();
            //获取会话,参数1表示事务、参数2表示签收
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //创建目的地(队列/主题)
            Queue queue = session.createQueue(QUEUE_NAME);
            //创建消息的生产者
            producer = session.createProducer(queue);
            //使用生产者生产消息
            for (int i = 1; i <= 10; i++) {
                //创建消息
                TextMessage textMessage = session.createTextMessage("test message " + i);
                //生产者发送消息到mq
                producer.send(textMessage);
            }
            System.out.println("========消息发送到mq完成========");
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            try {
                producer.close();
                session.close();
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
