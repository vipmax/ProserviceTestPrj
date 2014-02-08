package RMIServer;


import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements AccountService {

	public static final String BINDING_NAME = "Service";

	@Override
	public Long getAmount(Integer id) {
		System.out.println("SocketServer.Client with id = " + id + " connected");
		DataBase dataBase = new DataBase();

		Long amount = dataBase.getAmount(id);
		return amount;
	}

	@Override
	public void addAmount(Integer id, Long value) {

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
