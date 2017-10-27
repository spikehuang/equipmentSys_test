package com.spike.service;

import java.util.List;

import com.spike.model.Department;
import com.spike.model.PageBean;

public interface DepartmentService {

	/**
	 * 获取部门信息列表
	 * 
	 * @param pageBean
	 * @param s_department
	 * @return
	 */
	public List<Department> find(PageBean pageBean, Department s_department);

	/**
	 * 获取部门数量
	 * 
	 * @param s_department
	 * @return
	 */
	public int count(Department s_department);

	/**
	 * 增加部门
	 * 
	 * @param department
	 */
	public void add(Department department);

	/**
	 * 更新部门
	 * 
	 * @param department
	 */
	public void update(Department department);

	/**
	 * 删除部门
	 * 
	 * @param id
	 */
	public void delete(int id);

	/**
	 * 通过部门ID获取部门信息
	 * 
	 * @param id
	 * @return
	 */
	public Department loadById(int id);
}
