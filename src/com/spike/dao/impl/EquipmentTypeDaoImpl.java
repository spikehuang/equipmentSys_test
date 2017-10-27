package com.spike.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.spike.dao.EquipmentTypeDao;
import com.spike.model.EquipmentType;
import com.spike.model.PageBean;
import com.spike.util.StringUtil;

@Repository("equipmentTypeDao")
public class EquipmentTypeDaoImpl implements EquipmentTypeDao {

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<EquipmentType> find(PageBean pageBean, EquipmentType s_equipmentType) {
		StringBuffer stringBuffer = new StringBuffer("select * from t_equipmentType");
		if (s_equipmentType != null) {
			if (StringUtil.isNotEmpty(s_equipmentType.getTypeName())) {
				stringBuffer.append(" and typeName like '%" + s_equipmentType.getTypeName() + "%'");
			}
		}
		if (pageBean != null) {
			stringBuffer.append(" limit " + pageBean.getStart() + "," + pageBean.getPageSize());
		}

		final List<EquipmentType> equipmentTypeList = new ArrayList<EquipmentType>();
		jdbcTemplate.query(stringBuffer.toString().replaceFirst("and", "where"), new Object[] {},
				new RowCallbackHandler() {

					@Override
					public void processRow(ResultSet rs) throws SQLException {
						EquipmentType equipmentType = new EquipmentType();
						equipmentType.setId(rs.getInt("id"));
						equipmentType.setTypeName(rs.getString("typeName"));
						equipmentType.setRemark(rs.getString("remark"));
						equipmentTypeList.add(equipmentType);
					}

				});
		return equipmentTypeList;
	}

	@Override
	public int count(EquipmentType s_equipmentType) {
		StringBuffer stringBuffer = new StringBuffer("select count(*) from t_equipmentType");
		if (s_equipmentType != null) {
			if (StringUtil.isNotEmpty(s_equipmentType.getTypeName())) {
				stringBuffer.append(" and typeName like '%" + s_equipmentType.getTypeName() + "%'");
			}
		}
		return jdbcTemplate.queryForObject(stringBuffer.toString().replaceFirst("and", "where"), Integer.class);
	}

	@Override
	public void add(EquipmentType equipmentType) {
		String sql = "insert into t_equipmentType values (null, ?, ?)";
		jdbcTemplate.update(sql, new Object[] { equipmentType.getTypeName(), equipmentType.getRemark() });
	}

	@Override
	public void update(EquipmentType equipmentType) {
		String sql = "update t_equipmentType set typeName=?,remark=? where id=?";
		jdbcTemplate.update(sql,
				new Object[] { equipmentType.getTypeName(), equipmentType.getRemark(), equipmentType.getId() });
	}

	@Override
	public void delete(int id) {
		String sql = "delete from t_equipmentType where id=?";
		jdbcTemplate.update(sql, new Object[] { id });
	}

	@Override
	public EquipmentType loadById(int id) {
		String sql = "select * from t_equipmentType where id=?";
		final EquipmentType equipmentType = new EquipmentType();
		jdbcTemplate.query(sql, new Object[] { id }, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				equipmentType.setId(rs.getInt("id"));
				equipmentType.setTypeName(rs.getString("typeName"));
				equipmentType.setRemark(rs.getString("remark"));
			}
		});
		return equipmentType;
	}

}
