package RMIServer.Client;

/**
 * Created by vipmax on 08.02.14.
 */

import RMIServer.Server.AccountService;
import RMIServer.Server.DataBase;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Client {

	private Registry registry;
	private AccountService availableServiceList;
	private static Integer id;
	private static Long amount;
	private static final Integer rCount = 0, wCount = 1, clientCount = 1;


	public static void main(String... args) throws Exception {

		for (int i = 0; i < clientCount; i++) {
			new Thread(new Runnable() {
				private Long amount;

				@Override
				public void run() {
				Client client = new Client();
				try {
					client.initRegistryAndService();
					//getAmount

					for (int i = 0; i < rCount; i++) {
						id = Integer.valueOf((int) (Math.random() * 1000));
						System.out.println("Send id to server: " + id);
						amount = client.availableServiceList.getAmount(491);
						if (amount.equals(DataBase.notFount)) {
							System.out.println("Not found such id : " + id);
						} else {
							System.out.println("Got from server: " + amount);
						}
					}

					//addAmount
					for (int i = 0; i < wCount; i++) {
						id = Integer.valueOf((int) (Math.random() * 1000));
						amount = new Long((int) (Math.random() * 1000));
						if (client.availableServiceList.addAmount(id, amount)) {
							System.out.println("Added in database " + id + ", " + amount);
						}
					}

				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (NotBoundException e) {
					e.printStackTrace();
				}


			}
		}).start();

		}


	}

	private void initRegistryAndService() throws RemoteException, NotBoundException {
		registry = LocateRegistry.getRegistry("localhost", 2099);
		availableServiceList = (AccountService) registry.lookup("Service");
	}

}