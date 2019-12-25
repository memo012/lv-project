package com.lv.adminsys.common.utils;

import java.util.List;

/**
 * @author qiang
 * @Description: 封装分页后的数据格式
 */
public class PagedResult {

	/**
	 *  当前页数
	 */
	private int page;
	/**
	 * 总页数
	 */
	private Long total;
	/**
	 *  总记录数
	 */
	private Long records;
	/**
	 * 每行显示的内容
	 */
	private List<?> rows;
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public long getRecords() {
		return records;
	}
	public void setRecords(long records) {
		this.records = records;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}

}
