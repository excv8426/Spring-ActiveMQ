package javasrc.listener;

import javax.jms.Message;
import javax.jms.MessageListener;

public class ConsumerMessageListener implements MessageListener {

	@Override
	public void onMessage(Message arg0) {
		System.out.println("收到消息*********************************");
        System.out.println(arg0.toString());
	}

}
