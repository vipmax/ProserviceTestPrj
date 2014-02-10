package RMIServer;


import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.TimeUnit;

public class Server implements AccountService {

	public static final String BINDING_NAME = "Service";
	DataBase dataBase = new DataBase();
	private static Integer countOfRequestGetAmount, countOfRequestAddAmount, countOfAllRequest = 0;

	@Override
	public Long getAmount(Integer id) {

		countOfRequestGetAmount++;
		countOfAllRequest++;
		//System.out.println("*******************");
		//System.out.println("SocketServer.Client with id = " + id + " connected");


		Long amount = dataBase.getAmount(id);


		return amount;
	}

	@Override
	public boolean addAmount(Integer id, Long value) {
		countOfRequestAddAmount++;
		countOfAllRequest++;
		//System.out.println("*******************");
		//System.out.println("Client connected and he mean add rows : " + id + ", " + value);
		dataBase.addAmount(id, value);
		System.out.println("Count Of All Request" + countOfAllRequest);
		return true;

	}

	public static void main(String... args) throws Exception {
		Server server = new Server();
		server.startServer();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {


					countOfRequestAddAmount = 0;
					countOfRequestGetAmount = 0;
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					System.out.println("Count of request in one sec :   getAmount() = " + countOfRequestGetAmount
							+ "  addAmount() = " + countOfRequestAddAmount
							+ "  All Request = " + countOfAllRequest);


				}
			}


		}).start();
	}


	private void startServer() throws RemoteException, AlreadyBoundException {
		System.out.print("Starting registry...");
		final Registry registry = LocateRegistry.createRegistry(2099);
		System.out.println(" OK");

		final AccountService service = new Server();
		Remote stub = UnicastRemoteObject.exportObject(service, 0);

		System.out.print("Binding service...");
		registry.bind(BINDING_NAME, stub);
		System.out.println(" OK");
	}
}
