package javasrc.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Component
public class AutoTask {
	
	@Autowired
	private JmsQueueSender sender;
	/**
	 * 定时任务。
	 * 每天x秒x分x时执行一次。*/
	@Scheduled(cron="0 0 11 * * ?" )
	private void runtask(){
		
	}
	
	@Scheduled(cron="0/5 * *  * * ? ")
	private void intervalRun(){
		sender.simpleSend();
	}
}
