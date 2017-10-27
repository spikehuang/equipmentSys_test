package com.spike.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spike.dao.EquipmentDao;
import com.spike.dao.RepairDao;
import com.spike.model.Equipment;
import com.spike.model.PageBean;
import com.spike.model.Repair;
import com.spike.service.EquipmentService;

@Service("equipmentService")
public class EquipmentServiceImpl implements EquipmentService {

	@Autowired
	private EquipmentDao equipmentDao;

	@Autowired
	private RepairDao repairDao;

	@Override
	public List<Equipment> find(PageBean pageBean, Equipment s_equipment) {
		return equipmentDao.find(pageBean, s_equipment);
	}

	@Override
	public int count(Equipment s_equipment) {
		return equipmentDao.count(s_equipment);
	}

	@Override
	public void delete(int id) {
		equipmentDao.delete(id);
	}

	@Override
	public void add(Equipment equipment) {
		equipmentDao.add(equipment);
	}

	@Override
	public void update(Equipment equipment) {
		equipmentDao.update(equipment);
	}

	@Override
	public Equipment loadById(int id) {
		return equipmentDao.loadById(id);
	}

	@Override
	public boolean existEquipmentByTypeId(int typeId) {
		return equipmentDao.existEquipmentByTypeId(typeId);
	}

	@Override
	public void addRepair(int id, String userMan) {
		Repair repair = new Repair();
		repair.setEquipmentId(id);
		repair.setUserMan(userMan);
		repairDao.add(repair);

		Equipment equipment = equipmentDao.loadById(id);
		equipment.setState(2); // 变更状态为维修
		equipmentDao.update(equipment);
	}

	@Override
	public void updateRepair(int id, int repairId, String repairMan, boolean success) {
		Repair repair = new Repair();

		repair.setId(repairId);
		repair.setRepairMan(repairMan);

		Equipment equipment = equipmentDao.loadById(id);

		if (success) {
			repair.setState(1); // 维修成功
			equipment.setState(1); // 变更状态为正常
		} else {
			repair.setState(2); // 设备报废
			equipment.setState(3); // 变更状态为报废
		}

		repairDao.update(repair);
		equipmentDao.update(equipment);
	}

}
