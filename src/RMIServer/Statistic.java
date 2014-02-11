package RMIServer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by vipmax on 2/11/14.
 */
public class Statistic {


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
}
