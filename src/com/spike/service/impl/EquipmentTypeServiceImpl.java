package com.spike.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.spike.dao.EquipmentTypeDao;
import com.spike.model.EquipmentType;
import com.spike.model.PageBean;
import com.spike.service.EquipmentTypeService;

@Service("equipmentTypeService")
public class EquipmentTypeServiceImpl implements EquipmentTypeService {

	@Resource
	private EquipmentTypeDao equipmentTypeDao;

	@Override
	public List<EquipmentType> find(PageBean pageBean, EquipmentType s_equipmentType) {
		return equipmentTypeDao.find(pageBean, s_equipmentType);
	}

	@Override
	public int count(EquipmentType s_equipmentType) {
		return equipmentTypeDao.count(s_equipmentType);
	}

	@Override
	public void add(EquipmentType equipmentType) {
		equipmentTypeDao.add(equipmentType);
	}

	@Override
	public void update(EquipmentType equipmentType) {
		equipmentTypeDao.update(equipmentType);
	}

	@Override
	public void delete(int id) {
		equipmentTypeDao.delete(id);
	}

	@Override
	public EquipmentType loadById(int id) {
		return equipmentTypeDao.loadById(id);
	}

}
