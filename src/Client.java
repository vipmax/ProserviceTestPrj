import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by vipmax on 04.02.14.
 */
public class Client {

	private static final int ID = (int) (Math.random() * 5 + 1);


	public static void main(String[] args) {
		System.out.println("I'm client");

		try {
			Socket socket = new Socket("localhost", 11111);

			DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
			DataInputStream inputStream = new DataInputStream(socket.getInputStream());


			System.out.println("My id: " + ID);
			//отправляем   ID счета от серверу
			outputStream.writeInt(ID);
			outputStream.flush();
			Long amount = inputStream.readLong();
			System.out.println("My amount is  " + amount);


		} catch (IOException e) {
			e.printStackTrace();
		}


	}
}
