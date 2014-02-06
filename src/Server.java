import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by vipmax on 04.02.14.
 */
public class Server implements IAccountService {
	public static int countOfConnectedClient = 0;

	public static void main(String[] args) {
		System.out.println("I'm server");


		try {
			ServerSocket serverSocket = new ServerSocket(11111);
			while (true) {
				System.out.println("Waiting clients");
				Socket socket = serverSocket.accept();
				System.out.println("Client connect");
				countOfConnectedClient++;
				DataInputStream inputStream = new DataInputStream(socket.getInputStream());
				DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

				Integer id = inputStream.readInt();
				System.out.println("Get id from client : " + id);
				Long amount = new DataBase().getAmount(id);
				System.out.println("Send amount to client: " + amount);
				outputStream.writeLong(amount);
				outputStream.flush();

				System.out.println("Since the launch of the server to be connected " + countOfConnectedClient + " clients");
			}


		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	@Override
	public Long getAmount(Integer id) {

		DataBase db = new DataBase();
		Long amount = db.getAmount(id);

		return amount;
	}

	@Override
	public void addAmount(Integer id, Long value) {

	}
}


