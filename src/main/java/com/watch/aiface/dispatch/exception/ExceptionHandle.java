package com.watch.aiface.dispatch.exception;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import com.watch.aiface.base.exception.MyRuntimeException;
import com.watch.aiface.base.pojo.dto.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ExceptionHandle {
	private static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

	String info0 = "数据被关联引用,不能操作";

	String info1 = "业务处理异常";

	String info2 = "请求参数异常";

	@ExceptionHandler(DataIntegrityViolationException.class)
	public Result handleDataIntegrityViolationException(DataIntegrityViolationException e) {
		logger.error("DataIntegrityViolationException", e);
		return Result.failure(info0, e.getMessage());
	}

	@ExceptionHandler(MyRuntimeException.class)
	public Result handleMyRuntimeException(MyRuntimeException e) {
		logger.error("MyRuntimeException", e);
		return Result.failure(info1, e.getMessage());
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public Result handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
		logger.error("MissingServletRequestParameterException", e);
		return Result.failure(info2, e.getMessage());
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public Result handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
		logger.error("MethodArgumentTypeMismatchException", e);
		return Result.failure(info2, e.getMessage());
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public Result handleConstraintViolationException(ConstraintViolationException e) {
		logger.error("ConstraintViolationException", e);
		Map<String, String> msg = new HashMap<>();
		for (ConstraintViolation<?> cv : e.getConstraintViolations()) {
			msg.put(cv.getPropertyPath().toString(), cv.getMessage());
		}
		return Result.failure(info2, msg);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public Result handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		logger.error("HttpMessageNotReadableException", e);
		return Result.failure(info2, e.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		logger.error("MethodArgumentNotValidException", e);
		Map<String, String> msg = new HashMap<>();
		for (FieldError fe : e.getBindingResult().getFieldErrors()) {
			msg.put(fe.getField(), fe.getDefaultMessage());
		}
		return Result.failure(info2, msg);
	}

	@ExceptionHandler(BindException.class)
	public Result handleBindException(BindException e) {
		logger.error("BindException", e);
		Map<String, String> msg = new HashMap<>();
		for (FieldError fe : e.getBindingResult().getFieldErrors()) {
			msg.put(fe.getField(), fe.getDefaultMessage());
		}
		return Result.failure(info2, msg);
	}
}
