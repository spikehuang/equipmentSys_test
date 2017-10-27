package com.spike.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.spike.model.Department;
import com.spike.model.PageBean;
import com.spike.service.DepartmentService;
import com.spike.service.UserService;
import com.spike.util.PageUtil;
import com.spike.util.ResponseUtil;
import com.spike.util.StringUtil;

import net.sf.json.JSONObject;

@Controller("departmentController")
@RequestMapping("/department")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private UserService userService;

	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(value = "page", required = false) String page, Department s_department,
			HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = request.getSession();

		if (StringUtil.isEmpty(page)) {
			page = "1";
			session.setAttribute("s_department", s_department);
		} else {
			s_department = (Department) session.getAttribute("s_department");
		}

		PageBean pageBean = new PageBean(Integer.parseInt(page), 3);
		List<Department> departmentList = departmentService.find(pageBean, s_department);
		int total = departmentService.count(s_department);

		String pageCode = PageUtil.getPagation(request.getContextPath() + "/department/list.do", total,
				Integer.parseInt(page), 3); // 获取分页代码
		modelAndView.addObject("pageCode", pageCode);
		modelAndView.addObject("modeName", "部门管理");
		modelAndView.addObject("departmentList", departmentList);
		modelAndView.addObject("mainPage", "department/list.jsp");
		modelAndView.setViewName("main"); // 保存视图名称
		return modelAndView;
	}

	@RequestMapping("/preSave")
	public ModelAndView preSave(@RequestParam(value = "id", required = false) String id) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("mainPage", "department/save.jsp");
		modelAndView.addObject("modeName", "部门管理");
		modelAndView.setViewName("main");
		if (StringUtil.isNotEmpty(id)) {
			modelAndView.addObject("actionName", "部门修改");
			Department department = departmentService.loadById(Integer.parseInt(id));
			modelAndView.addObject("department", department);
		} else {
			modelAndView.addObject("actionName", "部门添加");
		}
		return modelAndView;
	}

	@RequestMapping("/save")
	public String save(Department department) {
		if (department.getId() == null) {
			departmentService.add(department);
		} else {
			departmentService.update(department);
		}
		return "redirect:/department/list.do";
	}

	@RequestMapping("/delete")
	public void delete(@RequestParam(value = "id") String id, HttpServletResponse response) throws Exception {
		JSONObject result = new JSONObject();
		if (userService.existUserByDeptId(Integer.parseInt(id))) {
			result.put("errorInfo", "该部门下有用户，不能删除");
		} else {
			departmentService.delete(Integer.parseInt(id));
			result.put("success", true);
		}
		ResponseUtil.write(result, response);
	}
}
