package com.watch.aiface.base.util;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class EntityUtil {
	/**
	 * 查找指定实体为null的字段
	 */
	public static String[] findNullProperty(Object entity) {
		BeanWrapper bw = new BeanWrapperImpl(entity);
		PropertyDescriptor[] ps = bw.getPropertyDescriptors();
		List<String> nps = new ArrayList<String>();
		for (PropertyDescriptor p : ps) {
			if (bw.getPropertyValue(p.getName()) == null) {
				nps.add(p.getName());
			}
		}
		String[] result = new String[nps.size()];
		return nps.toArray(result);
	}
}
