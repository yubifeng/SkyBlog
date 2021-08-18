package com.danli.service;

import java.util.Map;

/**
 * redis业务层
 *
 * @author fanfanli
 * @date  2021/4/8
 */
public interface RedisService {








	/**
	 * 通过key从set中删除一个值（对象）
	 *
	 * @param key
	 * @param value
	 * @return void
	 */
	void saveValueToSet(String key, Object value);


	/**
	 * 通过key从set中删除一个值（对象）
	 *
	 * @param key
	 * @param value
	 * @return void
	 */
	void deleteValueBySet(String key, Object value);

	/**
	 * 通过key查询set中是否有某个值（对象）
	 *
	 * @param key
	 * @param value
	 * @return void
	 */
	boolean hasValueInSet(String key, Object value);

	/**
	 * 通过key删除一个redis中的结构（set、String、hash、list、sortedList）
	 *
	 * @param key
	 * @return void
	 */
	void deleteCacheByKey(String key);

	/**
	 * 查询redis中是否存在某个key
	 *
	 * @param key
	 * @return boolean
	 */
	boolean hasKey(String key);


	boolean hasHashKey(String key, Object value);

	void saveKVToHash(String hash, Object key, Object value);

	void saveMapToHash(String hash, Map map);

	Map getMapByHash(String hash);

	Object getValueByHashKey(String hash, Object key);

	void incrementByHashKey(String hash, Object key, int increment);

	/**
	 * 通过hash和key删除一条hash记录
	 *
	 * @param hash
	 * @param key
	 */
	void deleteByHashKey(String hash, Object key);

	int countBySet(String key);

	void deleteAllKeys();

	void incrementByKey(String key, int increment);

	void saveObjectToValue(String key, Object object);
	Object getObjectByValue(String key);
    void expire(Object redisKey, int seconds);
}
