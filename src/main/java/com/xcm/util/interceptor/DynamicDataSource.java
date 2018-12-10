package com.xcm.util.interceptor;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by 薛岑明 on 2017/3/14.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return RouteHolder.getRouteKey();
    }

}
