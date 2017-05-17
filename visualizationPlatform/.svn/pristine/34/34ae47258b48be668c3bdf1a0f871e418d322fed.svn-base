package androidServer.timerTask;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;

import androidServer.service.DBService;

public class TimerTask {

	@Autowired
	DBService dbService;

	public void scan() {
		Timestamp end = new Timestamp(System.currentTimeMillis());
		Timestamp start = new Timestamp(end.getTime() - 30 * 60 * 1000);
		System.out.println(end + " 执行扫描任务");
		boolean status = dbService.updateUserWarning(start, end);
		System.out.println("扫描结果  " + status);
	}
}
