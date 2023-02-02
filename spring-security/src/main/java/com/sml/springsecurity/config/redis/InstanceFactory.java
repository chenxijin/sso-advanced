/**
 * 
 */
package com.sml.springsecurity.config.redis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;


@Service
public class InstanceFactory implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
    public void setApplicationContext(ApplicationContext context) {
		applicationContext = context;
	}
	
	/**
	 * 获取托管bean
	 * @param beanType
	 * @return
	 */
	public static <T> T getInstance(Class<T> beanType){
		return applicationContext.getBean(beanType);
	}
	
	/**
	 * 根据beanName获取托管bean
	 * @param beanName
	 * @return
	 */
	public static Object getInstance(String beanName){
		return applicationContext.getBean(beanName);
	}
	
	/**
	 * 根据bean类型和beanname获取托管bean
	 * @param beanType
	 * @param beanName
	 * @return
	 */
	public static <T> T getInstance(Class<T> beanType, String beanName){
		return applicationContext.getBean(beanName, beanType);
	}
	
	/**
	 * 获取spring上下文
	 * @return ApplicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	/**
	 * 获取环境信息
	 * @return Environment
	 */
	public static Environment getEnvironment() {
		return applicationContext.getEnvironment();
	}
	
	/** 
	 * 
	 * @Title: 发布事件 
	 * @param event  void    返回类型  
	 */
	public static void publishEvent(Object event){
		applicationContext.publishEvent(event);
	}
}
