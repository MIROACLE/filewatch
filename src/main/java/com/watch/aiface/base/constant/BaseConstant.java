package com.watch.aiface.base.constant;

public class BaseConstant {
	/**
	 * 实体状态,有效
	 */
	public static final Integer ENTITY_STATUS_VALID = 1;
	/**
	 * 结果状态,成功
	 */
	public static final Integer RESULT_STATUS_SUCCESS = 0;
	/**
	 * 结果状态,失败
	 */
	public static final Integer RESULT_STATUS_FAILURE = 1;
	/**
	 * 文件删除队列
	 */
	public static final String JPATH_DELETE= "JPATH_DELETE";
	/**
	 * 延时交换机
	 */
	public static final String DEAD_LETTER_EXCHANGE = "DEAD_LETTER_EXCHANGE";
	/**
	 * 延时路由
	 */
	public static final String DELAY_ROUTING_KEY = "DELAY_ROUTING_KEY";
	/**
	 *立即交换机
	 */
	public static final String IMMEDIATE_EXCHANGE="IMMEDIATE_EXCHANGE";
	/**
	 *立即路由
	 */
	public static final String IMMEDIATE_ROUTING_KEY="IMMEDIATE_ROUTING_KEY";
	/**
	 *延时删除交换机
	 */
	public static final String DEAD_DELETE_LETTER_EXCHANGE="DEAD_DELETE_LETTER_EXCHANGE";
	/**
	 * 延时删除路由
	 */
	public static final String DELAY_DELETE_ROUTING_KEY="DELAY_DELETE_ROUTING_KEY";
	/**
	 *立即删除路由
	 */
	public static final String IMMEDIATE_DELETE_ROUTING_KEY="IMMEDIATE_DELETE_ROUTING_KEY";
	/**
	 *立即删除交换机
	 */
	public static final String IMMEDIATE_DELETE_EXCHANGE="IMMEDIATE_DELETE_EXCHANGE";


}
