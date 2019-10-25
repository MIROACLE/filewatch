package com.watch.aiface.base.pojo.dto;

import com.watch.aiface.base.constant.BaseConstant;

public class Result {
	/**
	 * 结果状态,成功:0,失败:1
	 */
	private Integer status;
	/**
	 * 消息
	 */
	private String message;
	/**
	 * 数据
	 */
	private Object data;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Result(Integer status, String message, Object data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public static Result success() {
		return success("成功", "");
	}

	public static Result success(Object data) {
		if (data == null) {
			return success("成功", "");
		} else {
			return success("成功", data);
		}
	}

	public static Result success(String message, Object data) {
		if (data == null) {
			return new Result(BaseConstant.RESULT_STATUS_SUCCESS, message, "");
		} else {
			return new Result(BaseConstant.RESULT_STATUS_SUCCESS, message, data);
		}
	}

	public static Result failure() {
		return failure("失败", "");
	}

	public static Result failure(Object data) {
		if (data == null) {
			return failure("失败", "");
		} else {
			return failure("失败", data);
		}
	}

	public static Result failure(String message, Object data) {
		if (data == null) {
			return new Result(BaseConstant.RESULT_STATUS_FAILURE, message, "");
		} else {
			return new Result(BaseConstant.RESULT_STATUS_FAILURE, message, data);
		}
	}

	public static Result failure(Integer status, String message, Object data) {
		if (data == null) {
			return new Result(status, message, "");
		} else {
			return new Result(status, message, data);
		}
	}
}
