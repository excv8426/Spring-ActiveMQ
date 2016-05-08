package javasrc.listener;

import javax.jms.Message;
import javax.jms.MessageListener;

public class SecondQueueListener implements MessageListener {

	@Override
	public void onMessage(Message arg0) {
		System.out.println("SecondQueue:"+arg0.toString());
	}
}
