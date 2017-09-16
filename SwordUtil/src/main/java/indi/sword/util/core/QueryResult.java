package indi.sword.util.core;

import java.util.List;
/**
 * @Description  查询结果集，包括数据和总记录数
 * @Author:rd_jianbin_lin
 * @Date: 11:05 2017/9/14
 */
public class QueryResult<T>
{
	/** 查询得出的数据List **/
	private List<T> resultList;
	
	/** 查询得出的总记录数 **/
	private int totalRecords;

	public List<T> getResultList()
	{
		return resultList;
	}

	public void setResultList(List<T> resultList)
	{
		this.resultList = resultList;
	}

	public int getTotalRecords()
	{
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords)
	{
		this.totalRecords = totalRecords;
	}
}
