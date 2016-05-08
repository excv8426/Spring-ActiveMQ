package javasrc.listener;

import javax.jms.Message;
import javax.jms.MessageListener;

public class FirstQueueListener implements MessageListener {

	@Override
	public void onMessage(Message arg0) {
		System.out.println("FirstQueue:"+arg0.toString());
	}

}
