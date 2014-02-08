package SocketServer;

/**
 * Created by vipmax on 06.02.14.
 */
public interface IAccountService {
	public Long getAmount(Integer id);

	public void addAmount(Integer id, Long value);
}
