package com.spike.dao;

import java.util.List;

import com.spike.model.Equipment;
import com.spike.model.PageBean;

public interface EquipmentDao {

	/**
	 * 获取设备信息列表
	 * 
	 * @param pageBean
	 * @param s_equipment
	 * @return
	 */
	public List<Equipment> find(PageBean pageBean, Equipment s_equipment);

	/**
	 * 获取设备数量
	 * 
	 * @param s_equipment
	 * @return
	 */
	public int count(Equipment s_equipment);

	/**
	 * 删除设备
	 * 
	 * @param id
	 */
	public void delete(int id);

	/**
	 * 增加设备
	 * 
	 * @param equipment
	 */
	public void add(Equipment equipment);

	/**
	 * 更新设备
	 * 
	 * @param equipment
	 */
	public void update(Equipment equipment);

	/**
	 * 根据设备ID获取设备信息
	 * 
	 * @param id
	 * @return
	 */
	public Equipment loadById(int id);

	/**
	 * 判断该类型ID下是否存在设备
	 * 
	 * @param typeId
	 * @return
	 */
	public boolean existEquipmentByTypeId(int typeId);
}
