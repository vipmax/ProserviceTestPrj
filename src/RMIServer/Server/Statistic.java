package RMIServer.Server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by vipmax on 2/11/14.
 */
public class Statistic {


	public static Integer countOfRequestAddAmountInOneSec;
	public static Integer countOfRequestGetAmountInOneSec;
	public static Integer countOfAllRequestGetAmount = 0;
	public static Integer countOfAllRequestAddAmount = 0;
	public static Long countHitToCash = new Long(0), countHitToDB = new Long(0), countHitToNotDB = new Long(0);
	public static Long fullTimeHitCache = new Long(0), fullTimeHitDB = new Long(0);
	public static Long countOfAddRowTToDB = new Long(0), fullTimeAddInDB = new Long(0);

	public static void pushToFile(String s, String host) {
		File file = new File(s);
		try {
			FileWriter fileWriter = new FileWriter(file, true);
			String dt = new java.text.SimpleDateFormat("dd-MMM-yy G hh:mm aaa").format(java.util.Calendar.getInstance().getTime());

			fileWriter.write("getAmount used: " + host + " " + dt + "\n");
			fileWriter.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void startTime() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {


					countOfRequestAddAmountInOneSec = 0;
					countOfRequestGetAmountInOneSec = 0;
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					System.out.println("Count of request in one sec :   getAmount = " + countOfRequestGetAmountInOneSec
							+ "  addAmount = " + countOfRequestAddAmountInOneSec
							+ "  All Request = " + (countOfAllRequestGetAmount + countOfAllRequestAddAmount));


				}
			}


		}).start();
	}


}
