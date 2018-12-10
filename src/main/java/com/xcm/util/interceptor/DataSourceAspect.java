package com.xcm.util.interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

public class DataSourceAspect {
	/**
	 * 在dao层方法之前获取datasource对象之前在切面中指定当前线程数据源路由的key
	 */
	public void beforeDaoMethod(JoinPoint point) {
		// dao方法上配置的注解
		String datasourceValue = "dataSource";
		MethodSignature sig = ((MethodSignature) point.getSignature());
		try {
			Method method = point.getTarget().getClass()
					.getDeclaredMethod(sig.getName(), sig.getParameterTypes());
			DataSource datasource = method.getAnnotation(DataSource.class);

			if (null == datasource) {
				datasource = point.getTarget().getClass()
						.getAnnotation(DataSource.class);
			}

			if (null != datasource) {
				datasourceValue = datasource.value();
			} else {

				Transactional transactional = method
						.getAnnotation(Transactional.class);

				if (null == transactional) {
					transactional = point.getTarget().getClass()
							.getAnnotation(Transactional.class);
				}

				if (null != transactional) {
					if (transactional.readOnly()) {
						datasourceValue = "dataSource";
					}
				}
			}
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}

		RouteHolder.setRouteKey(datasourceValue);
	}
}
