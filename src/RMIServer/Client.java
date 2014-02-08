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
	private AccountService service;

	public static void main(String... args) throws Exception {

//		for(int i = 0; i<2;i++) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				Client client = new Client();
				try {
					client.initRegistryAndService();
					Integer id = Integer.valueOf((int) (Math.random() * 5 + 1));
					Long amount = client.service.getAmount(id);
					System.out.println(amount);

				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (NotBoundException e) {
					e.printStackTrace();
				}


			}
		}).start();

//		}
	}

	private void initRegistryAndService() throws RemoteException, NotBoundException {
		registry = LocateRegistry.getRegistry("localhost", 2099);
		service = (AccountService) registry.lookup("Service");
	}

}