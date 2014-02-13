package RMIServer;


import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;

public class Server implements AccountService {

	public static final String BINDING_NAME = "Service";
	DataBase dataBase = new DataBase();

	CacheManager cacheManager = CacheManager.getInstance();

	@Override
	public Long getAmount(Integer id) {
		System.out.println("*******************");
		System.out.println("Id = " + id + " connected");
		Long secRun = System.currentTimeMillis();
		Statistic.countOfRequestGetAmountInOneSec++;
		Statistic.countOfAllRequestGetAmount++;
		String s = null;
		try {
			s = UnicastRemoteObject.getClientHost();
		} catch (ServerNotActiveException e) {
			e.printStackTrace();
		}
		Statistic.pushToFile("getAmount stat", s);


		Long aLong = cacheManager.geth(id);
		if (aLong != null) {
			System.out.println("Found in cache. Time: " + (System.currentTimeMillis() - secRun) + " ms");
			return aLong;
		}


		System.out.println("Client with id = " + id + " connected");


		Long amount = dataBase.getAmount(id);
		if (amount.equals(DataBase.notFount)) {
			System.out.println("Not Found in database.Time: " + (System.currentTimeMillis() - secRun) + " ms");
			return amount;
		}


		System.out.println("Found in database.Time: " + (System.currentTimeMillis() - secRun) + " ms");
		System.out.println("*******************");
		return amount;
	}

	@Override
	public boolean addAmount(Integer id, Long value) {
		System.out.println("*******************");
		System.out.println("Id = " + id + " connected with value = " + value);
		Long secRun = System.currentTimeMillis();


		cacheManager.puth(id, value);
		System.out.println("Update cache. Time: " + (System.currentTimeMillis() - secRun) + " ms");


		Statistic.countOfRequestAddAmountInOneSec++;
		Statistic.countOfAllRequestAddAmount++;

		String s = null;
		try {
			s = UnicastRemoteObject.getClientHost();
			Statistic.pushToFile("addAmount stat", s);
		} catch (ServerNotActiveException e) {
			e.printStackTrace();
		}


		dataBase.addAmount(id, value);

		System.out.println("Update database. Time: " + (System.currentTimeMillis() - secRun) + " ms");
		System.out.println("*******************");
		return true;

	}


	public static void main(String... args) throws Exception {
		Server server = new Server();
		server.startServer();
		Statistic.startTime();

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



