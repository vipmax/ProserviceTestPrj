package RMIServer.Server;


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

	public static void main(String... args) throws Exception {
		Server server = new Server();
		server.startServer();
		Statistic.startTime();

	}

	@Override
	public Long getAmount(Integer id) {
		System.out.println("*******************");
		System.out.println("Id = " + id + " connected");

		Statistic.countOfRequestGetAmountInOneSec++;
		Statistic.countOfAllRequestGetAmount++;
		String s = null;
		try {
			s = UnicastRemoteObject.getClientHost();
		} catch (ServerNotActiveException e) {
			e.printStackTrace();
		}
		Statistic.pushToFile("getAmount stat", s);

		Long nanoTime = System.nanoTime();

		Long aLong = cacheManager.get(id);
		Long deltaTime = System.nanoTime() - nanoTime;
		Statistic.fullTimeHitCache += deltaTime;
		if (aLong != null) {
			Statistic.countHitToCash++;
			System.out.println("Found in cache. Time: " + deltaTime + " ns" + " avg: " + Statistic.fullTimeHitCache / Statistic.countHitToCash + " ns" + " All hits :" + Statistic.countHitToCash);
			return aLong;
		}


		Long nanoTimeDB = System.nanoTime();

		Long amount = dataBase.getAmount(id);
		Long deltaTimeDB = System.nanoTime() - nanoTimeDB;

		if (amount.equals(DataBase.notFount)) {
			Statistic.fullTimeHitDB += deltaTimeDB;
			Statistic.countHitToNotDB++;
			System.out.println("Not Found in database.Time: " + deltaTimeDB + " ns" + " avg: " + Statistic.fullTimeHitDB / Statistic.countHitToNotDB + " ns" + " All hits :" + Statistic.countHitToNotDB);
			System.out.println("*******************");
			return amount;
		}
		Statistic.fullTimeHitDB += deltaTimeDB;
		Statistic.countHitToDB++;
		System.out.println("Found in database.Time: " + deltaTimeDB + " ns" + " avg: " + Statistic.fullTimeHitDB / Statistic.countHitToDB + " ns" + " All hits :" + Statistic.countHitToDB);
		System.out.println("*******************");
		return amount;
	}


	@Override
	public boolean addAmount(Integer id, Long value) {
		System.out.println("*******************");
		System.out.println("Id = " + id + " connected with value = " + value);


		Statistic.countOfRequestAddAmountInOneSec++;
		Statistic.countOfAllRequestAddAmount++;

		String s = null;
		try {
			s = UnicastRemoteObject.getClientHost();
			Statistic.pushToFile("addAmount stat", s);
		} catch (ServerNotActiveException e) {
			e.printStackTrace();
		}

		Long nanoTimeWriteDB = System.nanoTime();
		Statistic.countOfAddRowTToDB++;
		dataBase.addAmount(id, value);
		Long deltaTimeWriteDB = System.nanoTime() - nanoTimeWriteDB;                             //ADD IN DATABASE
		Statistic.fullTimeAddInDB += deltaTimeWriteDB;

		System.out.println("Update database. Time: " + deltaTimeWriteDB + " ns" + " avg: " + Statistic.fullTimeAddInDB / Statistic.countOfAddRowTToDB + " ns" + " All hits :" + Statistic.countOfAddRowTToDB);
		System.out.println("*******************");
		return true;

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



