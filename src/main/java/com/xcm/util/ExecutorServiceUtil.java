package com.xcm.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 薛岑明 on 2017/4/5.
 */
public class ExecutorServiceUtil {
    public static final int DEFAULT = 10;
    private static Map<Integer, ExecutorService> executorServiceMap = new HashMap<>();

    public static ExecutorService getExecutorServiceByThreadCount(int count) {
        ExecutorService service = executorServiceMap.get(count);
        if (service == null || service.isShutdown()) {
            synchronized (executorServiceMap) {
                service = Executors.newFixedThreadPool(count);
                executorServiceMap.put(count, service);
            }
        }
        return service;
    }
}
