package RMIServer;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by vipmax on 2/11/14.
 */
public class CacheManager {

	private static CacheManager instance;
	private static Object monitor = new Object();
	private Map<Integer, Long> cache = Collections.synchronizedMap(new HashMap<Integer, Long>());


	private CacheManager() {
	}

	public void put(Integer cacheKey, Long value) {
		cache.put(cacheKey, value);
	}

	public Long get(Integer cacheKey) {
		return cache.get(cacheKey);
	}

	public void clear(Integer cacheKey) {
		cache.put(cacheKey, null);
	}

	public void clear() {
		cache.clear();
	}

	public static CacheManager getInstance() {
		if (instance == null) {
			synchronized (monitor) {
				if (instance == null) {
					instance = new CacheManager();
				}
			}
		}
		return instance;
	}

	public void loadCache(DataBase dataBase) throws SQLException {
		//Dо избежании исключения, необходимо реализовать кэш с помощью Ehcache
		//by avoiding exceptionneed to implement the cache with some help Ehcache
		ResultSet r = dataBase.getInfoFromDB("Select * from  account_balance");
		while (r.next()) {
			cache.put(r.getInt("id"), r.getLong("value"));  //may be OutOfMemoryError

			map.put(r.getInt("id"), r.getLong("value"));
		}

	}


	public void updateValue(Integer id, Long value) {
		cache.put(id, value);
	}

	Config config = new Config();
	HazelcastInstance h = Hazelcast.newHazelcastInstance(config);
	ConcurrentMap<Integer, Long> map = h.getMap("my-distributed-map");


	public void puth(Integer cacheKey, Long value) {
		map.put(cacheKey, value);
	}

	public Long geth(Integer cacheKey) {
		return map.get(cacheKey);
	}
}
