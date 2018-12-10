package com.xcm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

public class JobOfferLogger implements Logger {
	private static Logger log4jError = LoggerFactory.getLogger("error");
	private static Logger log4jInfo = LoggerFactory.getLogger("info");
	@Override
	public void debug(String s) {
		log4jInfo.debug(s);
	}

	@Override
	public void debug(String s, Object obj) {
		log4jInfo.debug(s, obj);
	}

	@Override
	public void debug(String s, Object... aobj) {
		log4jInfo.debug(s, aobj);
	}

	@Override
	public void debug(String s, Throwable throwable) {
		log4jInfo.debug(s, throwable);
	}

	@Override
	public void debug(Marker marker, String s) {
		log4jInfo.debug(marker, s);
	}

	@Override
	public void debug(String s, Object obj, Object obj1) {
		log4jInfo.debug(s, obj, obj1);
	}

	@Override
	public void debug(Marker marker, String s, Object obj) {
		log4jInfo.debug(marker, s, obj);
	}

	@Override
	public void debug(Marker marker, String s, Object... aobj) {
		log4jInfo.debug(marker, s, aobj);
	}

	@Override
	public void debug(Marker marker, String s, Throwable throwable) {
		log4jInfo.debug(marker, s, throwable);
	}

	@Override
	public void debug(Marker marker, String s, Object obj, Object obj1) {
		log4jInfo.debug(marker, s, obj, obj1);
	}

	@Override
	public void error(String s) {
		log4jError.error(s);
	}

	@Override
	public void error(String s, Object obj) {
		log4jError.error(s, obj);
	}

	@Override
	public void error(String s, Object... aobj) {
		log4jError.error(s, aobj);
	}

	@Override
	public void error(String s, Throwable throwable) {
		log4jError.error(s, throwable);
	}

	@Override
	public void error(Marker marker, String s) {
		log4jError.error(marker, s);
	}

	@Override
	public void error(String s, Object obj, Object obj1) {
		log4jError.error(s, obj, obj1);
	}

	@Override
	public void error(Marker marker, String s, Object obj) {
		log4jError.error(marker, s, obj);
	}

	@Override
	public void error(Marker marker, String s, Object... aobj) {
		log4jError.error(marker, s, aobj);
	}

	@Override
	public void error(Marker marker, String s, Throwable throwable) {
		log4jError.error(marker, s, throwable);
	}

	@Override
	public void error(Marker marker, String s, Object obj, Object obj1) {
		log4jError.error(marker, s, obj, obj1);
	}

	@Override
	public String getName() {
		return log4jInfo.getName();
	}

	@Override
	public void info(String s) {
		log4jInfo.info(s);
	}

	@Override
	public void info(String s, Object obj) {
		log4jInfo.info(s, obj);
	}

	@Override
	public void info(String s, Object... aobj) {
		log4jInfo.info(s, aobj);
	}

	@Override
	public void info(String s, Throwable throwable) {
		log4jInfo.info(s, throwable);
	}

	@Override
	public void info(Marker marker, String s) {
		log4jInfo.info(marker, s);
	}

	@Override
	public void info(String s, Object obj, Object obj1) {
		log4jInfo.info(s, obj, obj1);
	}

	@Override
	public void info(Marker marker, String s, Object obj) {
		log4jInfo.info(marker, s, obj);
	}

	@Override
	public void info(Marker marker, String s, Object... aobj) {
		log4jInfo.info(marker, s, aobj);
	}

	@Override
	public void info(Marker marker, String s, Throwable throwable) {
		log4jInfo.info(marker, s, throwable);
	}

	@Override
	public void info(Marker marker, String s, Object obj, Object obj1) {
		log4jInfo.info(marker, s, obj, obj1);
	}

	@Override
	public boolean isDebugEnabled() {
		return log4jInfo.isDebugEnabled();
	}

	@Override
	public boolean isDebugEnabled(Marker marker) {
		return log4jInfo.isDebugEnabled(marker);
	}

	@Override
	public boolean isErrorEnabled() {
		return log4jInfo.isErrorEnabled();
	}

	@Override
	public boolean isErrorEnabled(Marker marker) {
		return log4jInfo.isErrorEnabled(marker);
	}

	@Override
	public boolean isInfoEnabled() {
		return log4jInfo.isInfoEnabled();
	}

	@Override
	public boolean isInfoEnabled(Marker marker) {
		return log4jInfo.isInfoEnabled(marker);
	}

	@Override
	public boolean isTraceEnabled() {
		return log4jInfo.isTraceEnabled();
	}

	@Override
	public boolean isTraceEnabled(Marker marker) {
		return log4jInfo.isTraceEnabled(marker);
	}

	@Override
	public boolean isWarnEnabled() {
		return log4jInfo.isWarnEnabled();
	}

	@Override
	public boolean isWarnEnabled(Marker marker) {
		return log4jInfo.isWarnEnabled(marker);
	}

	@Override
	public void trace(String s) {
		log4jInfo.trace(s);
		;
	}

	@Override
	public void trace(String s, Object obj) {
		log4jInfo.trace(s, obj);
	}

	@Override
	public void trace(String s, Object... aobj) {
		log4jInfo.trace(s, aobj);
	}

	@Override
	public void trace(String s, Throwable throwable) {
		log4jInfo.trace(s, throwable);
	}

	@Override
	public void trace(Marker marker, String s) {
		log4jInfo.trace(marker, s);
	}

	@Override
	public void trace(String s, Object obj, Object obj1) {
		log4jInfo.trace(s, obj, obj1);
	}

	@Override
	public void trace(Marker marker, String s, Object obj) {
		log4jInfo.trace(marker, s, obj);
	}

	@Override
	public void trace(Marker marker, String s, Object... aobj) {
		log4jInfo.trace(marker, s, aobj);
	}

	@Override
	public void trace(Marker marker, String s, Throwable throwable) {
		log4jInfo.trace(marker, s, throwable);
	}

	@Override
	public void trace(Marker marker, String s, Object obj, Object obj1) {
		log4jInfo.trace(marker, s, obj, obj1);
	}

	@Override
	public void warn(String s) {
		log4jInfo.warn(s);
	}

	@Override
	public void warn(String s, Object obj) {
		log4jInfo.warn(s, obj);
	}

	@Override
	public void warn(String s, Object... aobj) {
		log4jInfo.warn(s, aobj);
	}

	@Override
	public void warn(String s, Throwable throwable) {
		log4jInfo.warn(s, throwable);
	}

	@Override
	public void warn(Marker marker, String s) {
		log4jInfo.warn(marker, s);
	}

	@Override
	public void warn(String s, Object obj, Object obj1) {
		log4jInfo.warn(s, obj, obj1);
	}

	@Override
	public void warn(Marker marker, String s, Object obj) {
		log4jInfo.warn(marker, s, obj);
	}

	@Override
	public void warn(Marker marker, String s, Object... aobj) {
		log4jInfo.warn(marker, s, aobj);
	}

	@Override
	public void warn(Marker marker, String s, Throwable throwable) {
		log4jInfo.warn(marker, s, throwable);
	}

	@Override
	public void warn(Marker marker, String s, Object obj, Object obj1) {
		log4jInfo.warn(marker, s, obj, obj1);
	}

}
