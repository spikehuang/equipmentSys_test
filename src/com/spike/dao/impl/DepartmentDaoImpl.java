package com.spike.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.spike.dao.DepartmentDao;
import com.spike.model.Department;
import com.spike.model.PageBean;
import com.spike.util.StringUtil;

@Repository("departmentDao")
public class DepartmentDaoImpl implements DepartmentDao {

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Department> find(PageBean pageBean, Department s_department) {
		StringBuffer stringBuffer = new StringBuffer("select * from t_department");
		if (s_department != null) {
			if (StringUtil.isNotEmpty(s_department.getDeptName())) {
				stringBuffer.append(" and deptName like '%" + s_department.getDeptName() + "%'");
			}
		}
		if (pageBean != null) {
			stringBuffer.append(" limit " + pageBean.getStart() + "," + pageBean.getPageSize());
		}

		final List<Department> departmentList = new ArrayList<Department>();
		jdbcTemplate.query(stringBuffer.toString().replaceFirst("and", "where"), new Object[] {},
				new RowCallbackHandler() {

					@Override
					public void processRow(ResultSet rs) throws SQLException {
						Department department = new Department();
						department.setId(rs.getInt("id"));
						department.setDeptName(rs.getString("deptName"));
						department.setRemark(rs.getString("remark"));
						departmentList.add(department);
					}

				});
		return departmentList;
	}

	@Override
	public int count(Department s_department) {
		StringBuffer stringBuffer = new StringBuffer("select count(*) from t_department");
		if (s_department != null) {
			if (StringUtil.isNotEmpty(s_department.getDeptName())) {
				stringBuffer.append(" and deptName like '%" + s_department.getDeptName() + "%'");
			}
		}

		return jdbcTemplate.queryForObject(stringBuffer.toString().replaceFirst("and", "where"), Integer.class); // 返回Integer类型
	}

	@Override
	public void add(Department department) {
		String sql = "insert into t_department values (null, ?, ?)";
		jdbcTemplate.update(sql, new Object[] { department.getDeptName(), department.getRemark() });
	}

	@Override
	public void update(Department department) {
		String sql = "update t_department set deptName=?,remark=? where id=?";
		jdbcTemplate.update(sql, new Object[] { department.getDeptName(), department.getRemark(), department.getId() });
	}

	@Override
	public void delete(int id) {
		String sql = "delete from t_department where id=?";
		jdbcTemplate.update(sql, new Object[] { id });
	}

	@Override
	public Department loadById(int id) {
		String sql = "select * from t_department where id=?";
		final Department department = new Department();
		jdbcTemplate.query(sql, new Object[] { id }, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				department.setId(rs.getInt("id"));
				department.setDeptName(rs.getString("deptName"));
				department.setRemark(rs.getString("remark"));
			}
		});
		return department;
	}

}
