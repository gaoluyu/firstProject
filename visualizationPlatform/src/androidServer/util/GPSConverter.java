package androidServer.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.fasterxml.jackson.databind.ObjectMapper;

import androidServer.bean.WorkParameter;

public class GPSConverter {
	final int threadPoolSize = 20;
	private ExecutorService es = null;
	private HttpPool pool;
	// private List<WorkParameter> list;

	public GPSConverter() {
		es = Executors.newFixedThreadPool(threadPoolSize);
		pool = HttpPool.getInstance();
	}

	public void stop() {
		es.shutdown();
		pool.closePool();
	}

	public static void main(String... args) {
		String path = "E:/excelUpload/3.xlsx";
		System.out.println("loading in batch\nsource file :" + path);
		final GPSConverter converter = new GPSConverter();

		int eachLoadSize = 5000;
		int start = 1;

		System.out.println("load from " + start);
		List<WorkParameter> list;
		try {
			list = EXCELUtil.getExcelContent(path, start, eachLoadSize);
			converter.startConvert(list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		converter.stop();

	}

	public void startConvert(List<WorkParameter> list) {
		// list = _list;
		List<FetchTask> tasks = new ArrayList<FetchTask>();

		for (int id = 0; id < threadPoolSize; id++) {
			List<WorkParameter> subList = new ArrayList<WorkParameter>();
			for (int i = id; i < list.size(); i = i + threadPoolSize) {
				subList.add(list.get(i));
			}
			tasks.add(new FetchTask(subList, id));
		}
		try {
			final List<Future<Boolean>> status = es.invokeAll(tasks);
			// System.out.println("working");
			while (true) {
				Thread.currentThread().sleep(800);
				int count = 0;
				for (Future<Boolean> s : status) {
					if (s.isDone())
						count++;
					// System.out.println(s.isDone());
				}
				if (count == threadPoolSize) {
					System.out.println("更新完成");
					break;
				}
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	class FetchTask implements Callable<Boolean> {
		private int id;
		private List<WorkParameter> list;
		private final int convertLimit = 90;
		private final String ak = "NNu08gY8VCMhVPINhIk1Bw2r";

		public FetchTask(List<WorkParameter> list, int id) {
			System.out.println("id " + id + " size " + list.size());
			this.id = id;
			this.list = list;

		}

		@Override
		public Boolean call() throws Exception {
			int total = list.size();
			int sendedNumber = 0;
			int receivedNumber = 0;
			while (sendedNumber < total) {
				Map<String, String> params = new HashMap<String, String>();
				params.put("ak", ak);
				String coords = "";
				int i = 0;
				String defaultCoords = "113.6995820,35.7837149;";
				for (i = 0; (sendedNumber + i) < total && i < convertLimit; i++) {
					double longitude = list.get(sendedNumber + i).getLongitude();
					double latitude = list.get(sendedNumber + i).getLatitude();
					if (longitude == 0 || latitude == 0)
						coords += defaultCoords;
					else
						coords += (longitude + "," + latitude + ";");
				}
				sendedNumber += i;
				// System.out.println(sendedNumber);
				if (coords.length() >= 1)
					coords = coords.substring(0, coords.length() - 1);
				// params.put("location", "39.983424,116.322987");
				params.put("coords", coords);
				// System.out.println(location);
				params.put("from", "1");
				params.put("to", "5");
				// System.out.println(id + " send request,sended number " +
				// sendedNumber);
				// System.out.println(id + " send request \n" + coords);
				Map<String, Object> data = pool.post("http://api.map.baidu.com/geoconv/v1/?", params);

				Object status = data.get("status");
				if (status != null && ((Integer) status) == 0) {
					ObjectMapper mapper = new ObjectMapper();
					Object retList = data.get("result");
					String retListStr = mapper.writeValueAsString(retList);
					// System.out.println(id + " received string " +
					// retListStr);
					List<Map<String, Double>> retListMap = JSONUtil.jsonToJavaList(retListStr, Map.class);
					for (int j = 0; j < retListMap.size(); j++) {
						Map<String, Double> map = retListMap.get(j);
						double longidudeMap = map.get("x");
						double latitudeMap = map.get("y");
						list.get(receivedNumber).setLongitudeMap(longidudeMap);
						list.get(receivedNumber).setLatitudeMap(latitudeMap);
						receivedNumber++;
					}
					// System.out.println(id + " received number " +
					// receivedNumber);
				}
			}

			System.out.println("task " + Thread.currentThread().getName() + "finished");
			return sendedNumber == receivedNumber;
		}
	}
}
