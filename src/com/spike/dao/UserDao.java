package com.spike.dao;

import java.util.List;

import com.spike.model.PageBean;
import com.spike.model.User;

public interface UserDao {

	/**
	 * 获取当前登录用户
	 * 
	 * @param user
	 * @return
	 */
	public User login(User user);

	/**
	 * 获取用户信息列表
	 * 
	 * @param pageBean
	 * @param s_user
	 * @return
	 */
	public List<User> find(PageBean pageBean, User s_user);

	/**
	 * 获取用户总数
	 * 
	 * @param s_user
	 * @return
	 */
	public int count(User s_user);

	/**
	 * 删除用户
	 * 
	 * @param id
	 */
	public void delete(int id);

	/**
	 * 添加用户
	 * 
	 * @param user
	 */
	public void add(User user);

	/**
	 * 更新用户
	 * 
	 * @param user
	 */
	public void update(User user);

	/**
	 * 根据用户ID获取用户信息
	 * 
	 * @param id
	 * @return
	 */
	public User loadById(int id);

	/**
	 * 判断该部门ID下是否存在用户
	 * 
	 * @param deptId
	 * @return
	 */
	public boolean existUserByDeptId(int deptId);
}
