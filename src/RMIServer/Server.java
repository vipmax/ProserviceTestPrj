package RMIServer;


import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements AccountService {

	public static final String BINDING_NAME = "Service";
	DataBase dataBase = new DataBase();

	@Override
	public Long getAmount(Integer id) {
		System.out.println("*******************");
		System.out.println("SocketServer.Client with id = " + id + " connected");


		Long amount = dataBase.getAmount(id);
		return amount;
	}

	@Override
	public boolean addAmount(Integer id, Long value) {
		System.out.println("*******************");
		System.out.println("Client connected and he mean add rows : " + id + ", " + value);
		dataBase.addAmount(id, value);

		return true;

	}

	public static void main(String... args) throws Exception {
		Server server = new Server();
		server.startServer();

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
