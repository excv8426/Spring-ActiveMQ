package javasrc.component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

@Component
public class JmsQueueSender {
	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	@Qualifier("queue2")
	private ActiveMQQueue queue;
	
	public void simpleSend(){
		jmsTemplate.send(queue, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage("**jmsTemplate**");
			}
		});
	}
}
