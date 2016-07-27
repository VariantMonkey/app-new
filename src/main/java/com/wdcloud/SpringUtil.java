package com.wdcloud;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 类描述：spring 工具类
 * Created by houzehong on 2016/7/26.
 */
public class SpringUtil implements ApplicationContextAware {
    //当前Ioc容器
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContextArg) throws BeansException {
        applicationContext = applicationContextArg;
    }
    //获取Bean对象
    public static Object getBean(String id) {
        Object object = null;
        object = applicationContext.getBean(id);
        return object;
    }
}
