package RMIServer;

/**
 * Created by vipmax on 08.02.14.
 */

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Client {

	private Registry registry;
	private AccountService availableServiceList;

	public static void main(String... args) throws Exception {


		new Thread(new Runnable() {
			@Override
			public void run() {
				Client client = new Client();
				try {
					client.initRegistryAndService();
					// getAmount
					Integer id = Integer.valueOf((int) (Math.random() * 5 + 1));
					System.out.println("Send id to server: " + id);
					Long amount = client.availableServiceList.getAmount(3);
					System.out.println("Got from server: " + amount);
					// getAmount
					id = Integer.valueOf((int) (Math.random() * 5 + 1));
					System.out.println("Send id to server: " + id);
					amount = client.availableServiceList.getAmount(3);
					System.out.println("Got from server: " + amount);
					// getAmount
					id = Integer.valueOf((int) (Math.random() * 5 + 1));
					System.out.println("Send id to server: " + id);
					amount = client.availableServiceList.getAmount(3);
					System.out.println("Got from server: " + amount);
					// addAmount
					id = Integer.valueOf((int) (Math.random() * 1000));
					amount = new Long((int) (Math.random() * 1000) + 1);
					if (client.availableServiceList.addAmount(id, amount)) {
						System.out.println("Added in database " + id + ", " + amount);
					}

				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (NotBoundException e) {
					e.printStackTrace();
				}


			}
		}).start();


	}

	private void initRegistryAndService() throws RemoteException, NotBoundException {
		registry = LocateRegistry.getRegistry("localhost", 2099);
		availableServiceList = (AccountService) registry.lookup("Service");
	}

}