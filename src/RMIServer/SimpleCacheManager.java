package RMIServer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vipmax on 2/11/14.
 */
public class SimpleCacheManager {

	private static SimpleCacheManager instance;
	private static Object monitor = new Object();
	private Map<Integer, Long> cache = Collections.synchronizedMap(new HashMap<Integer, Long>());

	private SimpleCacheManager() {
	}

	public void put(Integer cacheKey, Long value) {
		cache.put(cacheKey, value);
	}

	public Object get(Integer cacheKey) {
		return cache.get(cacheKey);
	}

	public void clear(Integer cacheKey) {
		cache.put(cacheKey, null);
	}

	public void clear() {
		cache.clear();
	}

	public static SimpleCacheManager getInstance() {
		if (instance == null) {
			synchronized (monitor) {
				if (instance == null) {
					instance = new SimpleCacheManager();
				}
			}
		}
		return instance;
	}

}
