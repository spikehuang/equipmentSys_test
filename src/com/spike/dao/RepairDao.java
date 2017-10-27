package com.spike.dao;

import java.util.List;

import com.spike.model.PageBean;
import com.spike.model.Repair;

public interface RepairDao {

	/**
	 * 增加维修信息
	 * 
	 * @param repair
	 */
	public void add(Repair repair);

	/**
	 * 获取维修信息列表
	 * 
	 * @param pageBean
	 * @param s_repair
	 * @return
	 */
	public List<Repair> find(PageBean pageBean, Repair s_repair);

	/**
	 * 获取维修数量
	 * 
	 * @param s_repair
	 * @return
	 */
	public int count(Repair s_repair);

	/**
	 * 更新维修信息
	 * 
	 * @param repair
	 */
	public void update(Repair repair);
}
