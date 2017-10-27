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

import com.spike.model.EquipmentType;
import com.spike.model.PageBean;
import com.spike.service.EquipmentService;
import com.spike.service.EquipmentTypeService;
import com.spike.util.PageUtil;
import com.spike.util.ResponseUtil;
import com.spike.util.StringUtil;

import net.sf.json.JSONObject;

@Controller("equipmentTypeController")
@RequestMapping("/equipmentType")
public class EquipmentTypeController {

	@Autowired
	private EquipmentTypeService equipmentTypeService;

	@Autowired
	private EquipmentService equipmentService;

	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(value = "page", required = false) String page, EquipmentType s_equipmentType,
			HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = request.getSession();

		if (StringUtil.isEmpty(page)) {
			page = "1";
			session.setAttribute("s_equipmentType", s_equipmentType);
		} else {
			s_equipmentType = (EquipmentType) session.getAttribute("s_equipmentType");
		}

		PageBean pageBean = new PageBean(Integer.parseInt(page), 3);
		List<EquipmentType> equipmentTypeList = equipmentTypeService.find(pageBean, s_equipmentType);
		int total = equipmentTypeService.count(s_equipmentType);

		String pageCode = PageUtil.getPagation(request.getContextPath() + "/equipmentType/list.do", total,
				Integer.parseInt(page), 3); // 获取分页代码
		modelAndView.addObject("pageCode", pageCode);
		modelAndView.addObject("modeName", "设备类型管理");
		modelAndView.addObject("equipmentTypeList", equipmentTypeList);
		modelAndView.addObject("mainPage", "equipmentType/list.jsp");
		modelAndView.setViewName("main"); // 保存视图名称
		return modelAndView;
	}

	@RequestMapping("/delete")
	public void delete(@RequestParam(value = "id") String id, HttpServletResponse response) throws Exception {
		JSONObject result = new JSONObject();

		if (equipmentService.existEquipmentByTypeId(Integer.parseInt(id))) {
			result.put("errorInfo", "该类型下存在设备，不能删除！");
		} else {
			equipmentTypeService.delete(Integer.parseInt(id));
			result.put("success", true);
		}
		ResponseUtil.write(result, response);
	}

	@RequestMapping("/preSave")
	public ModelAndView preSave(@RequestParam(value = "id", required = false) String id) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("mainPage", "equipmentType/save.jsp");
		modelAndView.addObject("modeName", "设备类型管理");
		modelAndView.setViewName("main");
		if (StringUtil.isNotEmpty(id)) {
			modelAndView.addObject("actionName", "设备类型修改");
			EquipmentType equipmentType = equipmentTypeService.loadById(Integer.parseInt(id));
			modelAndView.addObject("equipmentType", equipmentType);
		} else {
			modelAndView.addObject("actionName", "设备类型添加");
		}
		return modelAndView;
	}

	@RequestMapping("/save")
	public String save(EquipmentType equipmentType) {
		if (equipmentType.getId() == null) {
			equipmentTypeService.add(equipmentType);
		} else {
			equipmentTypeService.update(equipmentType);
		}
		return "redirect:/equipmentType/list.do";
	}

}
