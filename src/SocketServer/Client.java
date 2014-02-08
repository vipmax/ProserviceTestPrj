package SocketServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by vipmax on 04.02.14.
 */
public class Client {


	public static void main(String[] args) {
		System.out.println("I'm client");

		try {
			Socket socket = new Socket("localhost", 11111);

			DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
			DataInputStream inputStream = new DataInputStream(socket.getInputStream());

			int ID = (int) (Math.random() * 5 + 1);
			System.out.println("My id: " + ID);
			//отправляем   ID счета  серверу
			outputStream.writeInt(ID);
			outputStream.flush();

			//получаем результат от сервера
			Long amount = inputStream.readLong();
			System.out.println("My amount is  " + amount);


		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}

