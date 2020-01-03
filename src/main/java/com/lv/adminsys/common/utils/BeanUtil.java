package com.lv.adminsys.common.utils;

import org.springframework.beans.BeanUtils;

/**
 *  E转换T 工具类
 * @author qiang
 * @param <Dto>
 * @param <Do>
 */
public class BeanUtil<E, T> {

	/**
	 * E 转换为T 工具类
	 * 
	 * @param dtoEntity
	 * @param doClass
	 * @return
	 */
	public static <T> T EtoT(Object dtoEntity, Class<T> doClass) {
		// 判断dto是否为空!
		if (dtoEntity == null) {
			return null;
		}
		// 判断DoClass 是否为空
		if (doClass == null) {
			return null;
		}
		try {
			T newInstance = doClass.newInstance();
			BeanUtils.copyProperties(dtoEntity, newInstance);
			return newInstance;
		} catch (Exception e) {
			return null;
		}
	}
}