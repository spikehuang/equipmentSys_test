package com.spike.controller;

import java.util.List;

import javax.annotation.Resource;
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
import com.spike.model.User;
import com.spike.service.DepartmentService;
import com.spike.service.UserService;
import com.spike.util.PageUtil;
import com.spike.util.ResponseUtil;
import com.spike.util.StringUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/user")
public class UserController {

	@Resource
	private UserService userService;

	@Autowired
	private DepartmentService departmentService;

	@RequestMapping("/login")
	public String login(User user, HttpServletRequest request) {
		User resultUser = userService.login(user);
		if (resultUser.getId() == null) {
			request.setAttribute("user", user);
			request.setAttribute("errorMsg", "用户名或密码错误！");
			return "login";
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("currentUser", resultUser);
			return "redirect:/main.jsp";
		}
	}

	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(value = "page", required = false) String page, User s_user,
			HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = request.getSession();

		if (StringUtil.isEmpty(page)) {
			page = "1";
			session.setAttribute("s_user", s_user);
		} else {
			s_user = (User) session.getAttribute("s_user");
		}

		PageBean pageBean = new PageBean(Integer.parseInt(page), 3);

		List<User> userList = userService.find(pageBean, s_user);
		int total = userService.count(s_user);

		String pageCode = PageUtil.getPagation(request.getContextPath() + "/user/list.do", total,
				Integer.parseInt(page), 3);

		modelAndView.addObject("pageCode", pageCode);
		modelAndView.addObject("modeName", "用户管理");
		modelAndView.addObject("userList", userList);
		modelAndView.addObject("mainPage", "user/list.jsp");
		modelAndView.setViewName("main");

		return modelAndView;
	}

	@RequestMapping("/delete")
	public void delete(@RequestParam(value = "id") String id, HttpServletResponse response) throws Exception {
		JSONObject result = new JSONObject();
		userService.delete(Integer.parseInt(id));
		result.put("success", true);
		// System.out.println(result); {"success",true}
		ResponseUtil.write(result, response);
	}

	@RequestMapping("/preSave")
	public ModelAndView preSave(@RequestParam(value = "id", required = false) String id) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("mainPage", "user/save.jsp");
		modelAndView.addObject("modeName", "用户管理");
		modelAndView.setViewName("main");

		if (StringUtil.isNotEmpty(id)) {
			modelAndView.addObject("actionName", "用户修改");
			User user = userService.loadById(Integer.parseInt(id));
			modelAndView.addObject("user", user);
		} else {
			modelAndView.addObject("actionName", "用户添加");
		}

		List<Department> departmentList = departmentService.find(null, null);
		modelAndView.addObject("departmentList", departmentList);
		return modelAndView;
	}

	@RequestMapping("/save")
	public String save(User user) {
		if (user.getId() == null) {
			userService.add(user);
		} else {
			userService.update(user);
		}
		return "redirect:/user/list.do";
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate(); // 销毁session
		return "redirect:/login.jsp";
	}
}
