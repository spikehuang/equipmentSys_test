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

import com.spike.model.Equipment;
import com.spike.model.EquipmentType;
import com.spike.model.PageBean;
import com.spike.model.Repair;
import com.spike.model.User;
import com.spike.service.EquipmentService;
import com.spike.service.EquipmentTypeService;
import com.spike.service.RepairService;
import com.spike.util.PageUtil;
import com.spike.util.ResponseUtil;
import com.spike.util.StringUtil;

import net.sf.json.JSONObject;

@Controller("equipmentController")
@RequestMapping("/equipment")
public class EquipmentController {

	@Autowired
	private EquipmentService equipmentService;

	@Autowired
	private EquipmentTypeService equipmentTypeService;

	@Autowired
	private RepairService repairService;

	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(value = "page", required = false) String page, Equipment s_equipment,
			HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = request.getSession();

		if (StringUtil.isEmpty(page)) {
			page = "1";
			session.setAttribute("s_equipment", s_equipment);
		} else {
			s_equipment = (Equipment) session.getAttribute("s_equipment");
		}

		PageBean pageBean = new PageBean(Integer.parseInt(page), 3);
		List<Equipment> equipmentList = equipmentService.find(pageBean, s_equipment);
		int total = equipmentService.count(s_equipment);

		String pageCode = PageUtil.getPagation(request.getContextPath() + "/equipment/list.do", total,
				Integer.parseInt(page), 3);

		modelAndView.addObject("pageCode", pageCode);
		modelAndView.addObject("equipmentList", equipmentList);
		modelAndView.addObject("mainPage", "equipment/list.jsp");
		modelAndView.addObject("modeName", "设备管理");
		modelAndView.setViewName("main");

		return modelAndView;
	}

	@RequestMapping("/delete")
	public void delete(int id, HttpServletResponse response) throws Exception {
		JSONObject result = new JSONObject();

		equipmentService.delete(id);
		result.put("success", true);

		ResponseUtil.write(result, response);
	}

	@RequestMapping("/preSave")
	public ModelAndView preSave(@RequestParam(value = "id", required = false) String id) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("modeName", "设备管理");
		modelAndView.addObject("mainPage", "equipment/save.jsp");
		modelAndView.setViewName("main");

		List<EquipmentType> equipmentTypeList = equipmentTypeService.find(null, null);
		modelAndView.addObject("equipmentTypeList", equipmentTypeList);

		if (StringUtil.isNotEmpty(id)) {
			modelAndView.addObject("actionName", "设备修改");
			Equipment equipment = equipmentService.loadById(Integer.parseInt(id));
			modelAndView.addObject("equipment", equipment);
			// System.out.println(id);
		} else {
			modelAndView.addObject("actionName", "设备添加");
		}

		return modelAndView;
	}

	@RequestMapping("/save")
	public String save(Equipment equipment) {
		if (equipment.getId() != null) {
			equipmentService.update(equipment);
		} else {
			equipmentService.add(equipment);
		}
		return "redirect:/equipment/list.do";
	}

	@RequestMapping("/userList")
	public ModelAndView userList(@RequestParam(value = "page", required = false) String page, Equipment s_equipment,
			HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = request.getSession();

		if (StringUtil.isEmpty(page)) {
			page = "1";
			session.setAttribute("s_equipment", s_equipment);
		} else {
			s_equipment = (Equipment) session.getAttribute("s_equipment");
		}

		PageBean pageBean = new PageBean(Integer.parseInt(page), 3);
		List<Equipment> equipmentList = equipmentService.find(pageBean, s_equipment);
		int total = equipmentService.count(s_equipment);

		String pageCode = PageUtil.getPagation(request.getContextPath() + "/equipment/userList.do", total,
				Integer.parseInt(page), 3);

		modelAndView.addObject("pageCode", pageCode);
		modelAndView.addObject("equipmentList", equipmentList);
		modelAndView.addObject("mainPage", "equipment/userList.jsp");
		modelAndView.addObject("modeName", "使用设备管理");
		modelAndView.setViewName("main");
		return modelAndView;
	}

	@RequestMapping("/repair")
	public void repair(@RequestParam(value = "id") String id, HttpSession session, HttpServletResponse response)
			throws Exception {
		String userMan = ((User) session.getAttribute("currentUser")).getUserName();
		JSONObject result = new JSONObject();
		equipmentService.addRepair(Integer.parseInt(id), userMan);
		result.put("success", true);
		ResponseUtil.write(result, response);
	}

	@RequestMapping("/repairList")
	public ModelAndView repairList(@RequestParam(value = "page", required = false) String page, Repair s_repair,
			HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = request.getSession();

		if (StringUtil.isEmpty(page)) {
			page = "1";
			session.setAttribute("s_repair", s_repair);
		} else {
			s_repair = (Repair) session.getAttribute("s_repair");
		}

		s_repair.setFinishState(1);
		PageBean pageBean = new PageBean(Integer.parseInt(page), 3);
		List<Repair> repairList = repairService.find(pageBean, s_repair);
		int total = repairService.count(s_repair);

		String pageCode = PageUtil.getPagation(request.getContextPath() + "/equipment/repairList.do", total,
				Integer.parseInt(page), 3);

		modelAndView.addObject("pageCode", pageCode);
		modelAndView.addObject("repairList", repairList);
		modelAndView.addObject("mainPage", "equipment/repairList.jsp");
		modelAndView.addObject("modeName", "维修设备管理");
		modelAndView.setViewName("main");

		return modelAndView;
	}

	@RequestMapping("/finishRepair")
	public void finishRepair(@RequestParam("id") int id, @RequestParam("repairId") int repairId,
			@RequestParam("success") boolean success, HttpSession session, HttpServletResponse response)
			throws Exception {
		String repairMan = ((User) session.getAttribute("currentUser")).getUserName();

		JSONObject result = new JSONObject();
		equipmentService.updateRepair(id, repairId, repairMan, success);
		result.put("success", true);

		ResponseUtil.write(result, response);
	}

	@RequestMapping("/repairHistory")
	public ModelAndView repairHistory(@RequestParam(value = "page", required = false) String page, Repair s_repair,
			HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		HttpSession session = request.getSession();

		if (StringUtil.isEmpty(page)) {
			page = "1";
			session.setAttribute("s_repair", s_repair);
		} else {
			s_repair = (Repair) session.getAttribute("s_repair");
		}

		s_repair.setFinishState(2);

		PageBean pageBean = new PageBean(Integer.parseInt(page), 3);
		List<Repair> repairList = repairService.find(pageBean, s_repair);
		int total = repairService.count(s_repair);
		String pageCode = PageUtil.getPagation(request.getContextPath() + "/equipment/repairHistory.do", total,
				Integer.parseInt(page), 3);

		modelAndView.addObject("pageCode", pageCode);
		modelAndView.addObject("modeName", "维修设备历史");
		modelAndView.addObject("repairList", repairList);
		modelAndView.addObject("mainPage", "equipment/repairHistory.jsp");
		modelAndView.setViewName("main");
		return modelAndView;
	}
}
