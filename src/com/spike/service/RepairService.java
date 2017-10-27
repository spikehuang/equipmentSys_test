package com.spike.service;

import java.util.List;

import com.spike.model.PageBean;
import com.spike.model.Repair;

public interface RepairService {

	/**
	 * 获取维修信息
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
}
