/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sml.security.config.redis;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 */

public class RedisUtils {
	@SuppressWarnings("rawtypes")
	private static RedisTemplate redisTemplate = (RedisTemplate) InstanceFactory.getInstance("redisTemplate");

	private static RedisTemplate redisTemplateForObject = (RedisTemplate) InstanceFactory.getInstance("redisTemplateForObject");

	private static RedisConnection redisConnection;

	private static long LOCK_TIME = 10;

	/**
	 * @功能：带参数的构造函数
	 * @参数：host，主机名或主机IP
	 * @参数：port，端口
	 * @参数：password，访问Redis数据库的密码
	 */
	public RedisUtils() {
		if (redisConnection == null) {
			redisConnection = redisTemplate.getConnectionFactory().getConnection();
		}
	}

	/**
	 * 
	 * 
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static void put(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);
	}

	/**
	 * 判断是否存在缓存
	 * 
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean checkCacheExist(String key) {
		boolean flag = false;
		flag = redisTemplate.hasKey(key);
		return flag;
	}

	@SuppressWarnings("unchecked")
	public static void clearCache(String key) {
		redisTemplate.delete(key);
	}

	/**
	 * 获取值
	 * 
	 * @param key
	 * @return
	 */

	public static String get(String key) {
		return StrUtil.toStringOrNull(redisTemplate.opsForValue().get(key));
	}

	/**
	 * @param key
	 * @return
	 */
	public static String pop(String key) {
		Object ooObject = redisTemplate.opsForList().leftPop(key);
		String str = ooObject == null ? "" : ooObject.toString();
		return str;
	}

	/**
	 * 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定.
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<String> lRange(String key, long start, long end) {
		List<String> datas = redisTemplate.opsForList().range(key, start, end);
		return datas;
	}

	/**
	 * 从list队列中获取所有的值,但是不移除数据
	 * @param key
	 * @return
	 */
	public static List<String> getAllForList(String key) {
		List<String> datas = null;
		long size = redisTemplate.opsForList().size(key);
		if (size > 0) {
			datas = redisTemplate.opsForList().range(key, 0, size);
		}
		return datas;
	}

	/**
	 * 从list队列中获取所有的值,但是不移除数据
	 * @param key
	 * @return
	 */
	public static List<String> getDefaultSizeForList(String key) {
		List<String> datas = redisTemplate.opsForList().range(key, 0, 100000);
		return datas;
	}

	/**
	 * 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定.
	 * @param key
	 * @return
	 */
	public static long size(String key) {
		return redisTemplate.opsForList().size(key);
	}

	/**
	 * @param key
	 * @param value
	 */
	public static void push(String key, Object value) {
		redisTemplate.opsForList().rightPush(key, value);
	}
	
	public static void pushAll(String key,List<String> value){
		redisTemplate.opsForList().leftPushAll(key, value);
	}

	/**
	 * 获取缓存
	 * 
	 * @param cacheName
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String get(String cacheName, String key) {
		String str = "";
		str = redisTemplate.opsForHash().get(cacheName, key) == null ? ""
				: redisTemplate.opsForHash().get(cacheName, key).toString();
		return str == null ? "" : str;
	}

	/**
	 * 获取缓存 -- 存什么就返回什么
	 *
	 * @param cacheName
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object getFromCache(String cacheName, String key) {
		return redisTemplate.opsForHash().get(cacheName, key) == null ? null
				: redisTemplate.opsForHash().get(cacheName, key);
	}

	/**
	 * 写入缓存 value不支持 map
	 * 
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	public static void put(String cacheName, String key, Object value) {
		redisTemplate.opsForHash().put(cacheName, key, JSONUtil.toJsonStr(value));

	}

	/**
	 * 设置redis中key的过期时间
	 * @param key
	 * @param timeout
	 * @param unit
	 */
	@SuppressWarnings("unchecked")
	public static void putForExpire(String key, final long timeout, final TimeUnit unit) {
		redisTemplate.expire(key, timeout, unit);
	}

	/**
	 * 写入缓存的时候同时设置key的时间
	 * @param key
	 * @param value
	 * @param timeout
	 * @param unit
	 */
	@SuppressWarnings("unchecked")
	public static void putValueForExpire(String key, Object value, final long timeout, final TimeUnit unit) {
		redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value), timeout, unit);
	}

	/**
	 * 写入缓存  -- 写入的对象不会做转化
	 *
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	public static void putIntoCache(String cacheName, String key, Object value) {
		redisTemplate.opsForHash().put(cacheName, key, value);
	}

	/**
	 * 从缓存中移除
	 * 
	 * @param cacheName
	 * @param key
	 */
	@SuppressWarnings("unchecked")
	public static void remove(String cacheName, String key) {
		redisTemplate.opsForHash().delete(cacheName, key);
	}

	/**
	 * 从缓存中移除--根据cacheKey全部移除
	 * @param cacheName
	 */
	public static void removeAll(String cacheName){
		redisTemplate.delete(cacheName);
	}

	@SuppressWarnings("rawtypes")
	public static RedisTemplate getRedisTemplate() {
		return redisTemplate;
	}

	@SuppressWarnings("unchecked")
	public static String getByCacheName(String cacheName) {
		String str = "";
		str = redisTemplate.opsForHash().values(cacheName) == null ? ""
				: redisTemplate.opsForHash().values(cacheName).toString();
		return str;
	}

	/**
	 * 发布消息
	 * 
	 * @param channel
	 * @param msg
	 */
	public static void publishMsg(String channel, Object msg) {
		try {
			getConnection().publish(channel.getBytes(), JSONUtil.toJsonStr(msg).getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			try {
				redisConnection.close();
			} catch (Exception e2) {
			} finally {
				redisConnection = null;
			}
		}
	}

	private static RedisConnection getConnection() {
		if (null == redisConnection || redisConnection.isClosed()) {
			redisConnection = redisTemplate.getConnectionFactory().getConnection();
		}
		return redisConnection;
	}


	/**
	 * 设置锁的key和value
	 * 并设置key的过期时间为10s
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean setNx(String key, String value){
		boolean res = getConnection().setNX(key.getBytes(), value.getBytes());
		if(res){
			redisConnection.expire(key.getBytes(), LOCK_TIME);
		}
		return res;
	}

	/**
	 * 存放值
	 * @param key
	 * @param value
	 */
	public static void putOb(String key, Object value) {
		redisTemplateForObject.opsForValue().set(key, value);
	}

	/**
	 * 存放值，10分钟过期时间
	 * @param key
	 * @param value
	 */
	public static void putObject(String key, Object value) {
		redisTemplateForObject.opsForValue().set(key, value, 10, TimeUnit.MINUTES);
	}

	/**
	 * 获取值
	 * @param key
	 * @return
	 */
	public static Object getObject(String key) {
		return redisTemplateForObject.opsForValue().get(key);
	}

	/**
	 * 判断是否存在key
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean checkObjectCacheExist(String key) {
		boolean flag = false;
		flag = redisTemplateForObject.hasKey(key);
		return flag;
	}

	/**
	 * 根据key清空redis值
	 * @param key
	 */
	public static void clearRedis(String key) {
		if (redisTemplateForObject.hasKey(key)) {
			redisTemplateForObject.delete(key);
		}
	}

	/**
	 * 根据key集合清空redis值
	 * @param keyList
	 */
	public static void clearRedisList(List<String> keyList) {
		for (String key : keyList) {
			if (redisTemplateForObject.hasKey(key)) {
				redisTemplateForObject.delete(key);
			}
		}
	}

	/**
	 * 写入缓存-- 写入的对象不会做转化，并设置过期时间
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	public static void putCache(String cacheName, String key, Object value) {
		redisTemplateForObject.opsForHash().put(cacheName, key, value);
		redisTemplateForObject.expire(cacheName, 10, TimeUnit.MINUTES);
	}

	/**
	 * 获取缓存-- 存什么就返回什么
	 * @param cacheName
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Object getCache(String cacheName, String key) {
		return redisTemplateForObject.opsForHash().get(cacheName, key) == null ? null
				: redisTemplateForObject.opsForHash().get(cacheName, key);
	}


	public static boolean setIfAbsent(String key, String value){
		return redisTemplate.opsForValue().setIfAbsent(key,value,LOCK_TIME, TimeUnit.SECONDS);
	}
}
