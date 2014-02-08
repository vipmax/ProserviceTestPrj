package RMIServer;


/**
 * Created by vipmax on 08.02.14.
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AccountService extends Remote {

	Long getAmount(Integer id) throws RemoteException;

	void addAmount(Integer id, Long value) throws RemoteException;

}