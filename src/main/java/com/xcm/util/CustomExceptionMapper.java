package com.xcm.util;


import com.xcm.model.exception.BusinessException;
import com.xcm.model.exception.ErrorCode;
import com.xcm.model.resource.entity.ErrorRes;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

@Component("exception")
public class CustomExceptionMapper implements ExceptionMapper<Throwable> {

	Logger logger = LoggerManage.getLogger();

	@Override
	public Response toResponse(Throwable e) {

		BusinessException exception;
		ErrorRes errorRes;
		if (e instanceof BusinessException) {
			exception = (BusinessException) e;
			errorRes = new ErrorRes("" + exception.getErrorCode(),
					exception.getMessage());
			logger.warn(
					String.format("error code:%s", exception.getErrorCode()), e);
		} else {
			exception = new BusinessException(ErrorCode.SYSTEM_ERROR, e);
			errorRes = new ErrorRes("" + exception.getErrorCode(),
					exception.getMessage());
			// 获取异常名称
			String exceptionName = e.getClass().getName();
			if ("org.apache.catalina.connector.ClientAbortException"
					.equalsIgnoreCase(exceptionName)) {
				logger.warn("system warn:", e);
			} else {
				logger.error("system error:", e);
			}
		}

		return Response.ok(errorRes, MediaType.APPLICATION_JSON)
				.status(Status.OK).build();
	}
}
