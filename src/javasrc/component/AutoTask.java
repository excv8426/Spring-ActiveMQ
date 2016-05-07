package javasrc.component;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Component
public class AutoTask {
	
	/**
	 * 定时任务。
	 * 每天x秒x分x时执行一次。*/
	@Scheduled(cron="0 0 11 * * ?" )
	private void runtask(){
		
	}
}
