package com.spike.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.spike.dao.EquipmentDao;
import com.spike.model.Equipment;
import com.spike.model.PageBean;
import com.spike.util.StringUtil;

@Repository("equipmentDao")
public class EquipmentDaoImpl implements EquipmentDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Equipment> find(PageBean pageBean, Equipment s_equipment) {
		StringBuffer sql = new StringBuffer("select * from t_equipment t1,t_equipmentType t2 where t1.typeId=t2.id");

		if (s_equipment != null) {
			if (StringUtil.isNotEmpty(s_equipment.getName())) {
				sql.append(" and t1.name like '%" + s_equipment.getName() + "%'");
			}
		}
		if (pageBean != null) {
			sql.append(" limit " + pageBean.getStart() + "," + pageBean.getPageSize());
		}

		final List<Equipment> equipmentList = new ArrayList<Equipment>();
		jdbcTemplate.query(sql.toString(), new Object[] {}, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				Equipment equipment = new Equipment();
				equipment.setId(rs.getInt("id"));
				equipment.setName(rs.getString("name"));
				equipment.setNo(rs.getString("no"));
				equipment.setTypeId(rs.getInt("typeId"));
				equipment.setTypeName(rs.getString("typeName"));
				equipment.setState(rs.getInt("state"));
				equipment.setRemark(rs.getString("remark"));
				equipmentList.add(equipment);
			}
		});
		return equipmentList;
	}

	@Override
	public int count(Equipment s_equipment) {
		StringBuffer sql = new StringBuffer(
				"select count(*) from t_equipment t1,t_equipmentType t2 where t1.typeId=t2.id");

		if (s_equipment != null) {
			if (StringUtil.isNotEmpty(s_equipment.getName())) {
				sql.append(" and t1.name like '%" + s_equipment.getName() + "%'");
			}
		}

		return jdbcTemplate.queryForObject(sql.toString(), Integer.class);
	}

	@Override
	public void delete(int id) {
		String sql = "delete from t_equipment where id=?";
		jdbcTemplate.update(sql, new Object[] { id });
	}

	@Override
	public void add(Equipment equipment) {
		String sql = "insert into t_equipment values(null, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, new Object[] { equipment.getName(), equipment.getNo(), equipment.getTypeId(),
				equipment.getState(), equipment.getRemark() });
	}

	@Override
	public void update(Equipment equipment) {
		String sql = "update t_equipment set name=?,no=?,typeId=?,state=?,remark=? where id=?";
		jdbcTemplate.update(sql, new Object[] { equipment.getName(), equipment.getNo(), equipment.getTypeId(),
				equipment.getState(), equipment.getRemark(), equipment.getId() });
	}

	@Override
	public Equipment loadById(int id) {
		String sql = "select * from t_equipment where id=?";
		Equipment equipment = new Equipment();
		jdbcTemplate.query(sql, new Object[] { id }, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				equipment.setId(rs.getInt("id"));
				equipment.setName(rs.getString("name"));
				equipment.setNo(rs.getString("no"));
				equipment.setTypeId(rs.getInt("typeId"));
				equipment.setState(rs.getInt("state"));
				equipment.setRemark(rs.getString("remark"));
			}
		});
		return equipment;
	}

	@Override
	public boolean existEquipmentByTypeId(int typeId) {
		String sql = "select count(*) from t_equipment where typeId=?";
		int result = jdbcTemplate.queryForObject(sql, new Object[] { typeId }, Integer.class);
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

}
