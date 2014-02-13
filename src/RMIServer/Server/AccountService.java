package RMIServer.Server;


/**
 * Created by vipmax on 08.02.14.
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AccountService extends Remote {

	Long getAmount(Integer id) throws RemoteException;

	boolean addAmount(Integer id, Long value) throws RemoteException;

}