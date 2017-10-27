package com.spike.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.spike.dao.RepairDao;
import com.spike.model.PageBean;
import com.spike.model.Repair;
import com.spike.util.DateUtil;
import com.spike.util.StringUtil;

@Repository("repairDao")
public class RepairDaoImpl implements RepairDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void add(Repair repair) {
		String sql = "insert into t_repair values(null,?,?,null,now(),null,0,1)";
		jdbcTemplate.update(sql, new Object[] { repair.getEquipmentId(), repair.getUserMan() });
	}

	@Override
	public List<Repair> find(PageBean pageBean, Repair s_repair) {
		StringBuffer sql = new StringBuffer("select * from t_repair t1,t_equipment t2 where t1.equipmentId=t2.id");

		if (s_repair != null) {
			if (StringUtil.isNotEmpty(s_repair.getEquipmentName())) {
				sql.append(" and t2.name like '%" + s_repair.getEquipmentName() + "%'");
			}
			if (s_repair.getFinishState() != null) {
				sql.append(" and t1.finishState =" + s_repair.getFinishState());
			}
		}

		if (pageBean != null) {
			sql.append(" limit " + pageBean.getStart() + "," + pageBean.getPageSize());
		}

		final List<Repair> repairList = new ArrayList<Repair>();

		jdbcTemplate.query(sql.toString(), new Object[] {}, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				Repair repair = new Repair();
				repair.setId(rs.getInt("id"));
				repair.setEquipmentId(rs.getInt("equipmentId"));
				repair.setEquipmentName(rs.getString("name"));
				repair.setEquipmentNo(rs.getString("no"));
				repair.setUserMan(rs.getString("userMan"));
				repair.setRepairMan(rs.getString("repairMan"));
				repair.setRepairTime(DateUtil.formatString(rs.getString("repairTime"), "yyyy-MM-dd HH:mm:ss"));
				repair.setFinishTime(DateUtil.formatString(rs.getString("finishTime"), "yyyy-MM-dd HH:mm:ss"));
				repair.setState(rs.getInt("state"));
				repairList.add(repair);
			}
		});
		return repairList;
	}

	@Override
	public int count(Repair s_repair) {
		StringBuffer sql = new StringBuffer(
				"select count(*) from t_repair t1,t_equipment t2 where t1.equipmentId=t2.id");
		if (s_repair != null) {
			if (StringUtil.isNotEmpty(s_repair.getEquipmentName())) {
				sql.append(" and t2.name like '%" + s_repair.getEquipmentName() + "%'");
			}
			if (s_repair.getFinishState() != null) {
				sql.append(" and t1.finishState =" + s_repair.getFinishState());
			}
		}
		return jdbcTemplate.queryForObject(sql.toString(), Integer.class);
	}

	@Override
	public void update(Repair repair) {
		String sql = "update t_repair set repairMan=?, finishTime=now(), state=?, finishState=2 where id=?";
		jdbcTemplate.update(sql, new Object[] { repair.getRepairMan(), repair.getState(), repair.getId() });
	}

}
