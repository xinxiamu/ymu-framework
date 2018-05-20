package com.ymu.framework.dao;

import java.util.List;

public class PageModel<T> {
	/**
	 * 当前页
	 */
	private Integer pageNo;
	/**
	 * 每页显示的记录数
	 */
	private Integer pageSize;
	/**
	 * 总页数
	 */
	private Integer pageCount;
	/**
	 * 显示分页的总个数
	 */
	private Integer showPageNo;
	/**
	 * 总记录数
	 */
	private Integer total;
	/**
	 * 记录列表
	 */
	private List<T> dataList;
	/**
	 * 分页起止页索引
	 */
	private Integer[] showPageIndexs;
	
	
	public static final Integer DEFAULT_PAGE_NO = 1;
	public static final Integer DEFAULT_PAGE_SIZE = 10;
	public static final Integer DEFAULT_SHOW_PAGE_NO = 5;
	

	public PageModel() {
	}
	
	public PageModel(Integer pageNo, Integer pageSize,
                     Integer total, List<T> dataList) {
		this(pageNo, pageSize, DEFAULT_SHOW_PAGE_NO, total, dataList);
	}
	
	public PageModel(Integer pageNo, Integer pageSize,
                     Integer showPageNo, Integer total, List<T> dataList) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.showPageNo = showPageNo;
		this.total = total;
		this.dataList = dataList;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	public Integer getPageCount() {
		return (int) Math.ceil(((double)total)/pageSize);
	}

	public Integer[] getShowPageIndexs() {
		int pageCount = getPageCount();
		
		int half = showPageNo / 2;
		int startIndex = pageNo - half;
		int endIndex = pageNo + half;
		
		if (startIndex < 1) {
			startIndex = 1;
		}
		/**
		 * currentLeftPage = pageNo - startIndex + 1
		 * 当前页到开始页的页数：(当前页-开始页 +1) 
		 * remainPageNum = showPageNo - currentLeftPage
		 * 剩下的页数:（显示的页数-当前页到开始页的页数）
		 * endIndex = pageNo + remainPageNum;
		 * 结束页:(当前页+剩下的页数 )
		 * 【总式：endIndex = pageNo + showPageNo - (pageNo - startIndex + 1);】
		 * 【简式：endIndex = showPageNo + startIndex - 1;】
		 */
		endIndex = showPageNo + startIndex - 1;
		
		if (endIndex > pageCount) {
			endIndex = pageCount;
			//如果当前页右边的页码显示不完，剩余的补充到当前页左边显示
			startIndex = endIndex - showPageNo;
			startIndex = startIndex < 1 ? 1: startIndex;
		}
		if (startIndex > pageCount) {
			startIndex = pageCount;
		}
		if (pageNo > endIndex) {
			startIndex = endIndex = pageNo;
		}
		
        return new Integer[]{startIndex, endIndex};
	}
	
	public static void main(String[] args) {
		PageModel<Object> p = new PageModel<Object>(5, 1, 5, 7, null);
		Integer[] pl = p.getShowPageIndexs();
		for (int i = pl[0]; i <= pl[1]; i++) {
			System.out.print(i + " ");
		}
		
	}
}
