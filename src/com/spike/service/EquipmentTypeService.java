package com.spike.service;

import java.util.List;

import com.spike.model.EquipmentType;
import com.spike.model.PageBean;

public interface EquipmentTypeService {

	/**
	 * 获取设备类型信息列表
	 * 
	 * @param pageBean
	 * @param s_equipment
	 * @return
	 */
	public List<EquipmentType> find(PageBean pageBean, EquipmentType s_equipmentType);

	/**
	 * 获取设备类型数量
	 * 
	 * @param s_equipment
	 * @return
	 */
	public int count(EquipmentType s_equipmentType);

	/**
	 * 增加设备类型
	 * 
	 * @param equipment
	 */
	public void add(EquipmentType equipmentType);

	/**
	 * 更新设备类型
	 * 
	 * @param equipment
	 */
	public void update(EquipmentType equipmentType);

	/**
	 * 删除设备类型
	 * 
	 * @param id
	 */
	public void delete(int id);

	/**
	 * 根据设备ID获取设备类型信息
	 * 
	 * @param id
	 * @return
	 */
	public EquipmentType loadById(int id);
}
