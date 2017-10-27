package com.spike.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spike.dao.RepairDao;
import com.spike.model.PageBean;
import com.spike.model.Repair;
import com.spike.service.RepairService;

@Service("repairService")
public class RepairServiceImpl implements RepairService {

	@Autowired
	private RepairDao repairDao;

	@Override
	public List<Repair> find(PageBean pageBean, Repair s_repair) {
		return repairDao.find(pageBean, s_repair);
	}

	@Override
	public int count(Repair s_repair) {
		return repairDao.count(s_repair);
	}

}
