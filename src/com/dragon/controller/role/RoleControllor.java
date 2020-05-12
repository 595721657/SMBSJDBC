package com.dragon.controller.role;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.dragon.pojo.Bill;
import com.dragon.pojo.Provider;
import com.dragon.pojo.Role;
import com.dragon.service.role.RoleService;
import com.mysql.jdbc.StringUtils;

//角色的controller
@Controller
//角色板块
@RequestMapping("/role")
public class RoleControllor {
	@Autowired
	private RoleService roleservcie;
	// 展示全部订单信息
	/*
	 * @RequestMapping(value="/rolelist.html",method=RequestMethod.GET) public
	 * String query(@RequestParam(name="queryProductName",required = false) String
	 * queryProductName,
	 * 
	 * @RequestParam(name="queryProviderId",required = false) String
	 * queryProviderId,
	 * 
	 * @RequestParam(name="queryIsPayment",required = false) String queryIsPayment,
	 * Model model) { //声明一个角色集合 List<Role> roleList = new ArrayList<Role>();
	 * roleList = roleservcie.getRoleList(); model.addAttribute("providerList",
	 * providerList); if(StringUtils.isNullOrEmpty(queryProductName)){
	 * queryProductName = ""; } //创建一个订单集合 List<Bill> billList = new
	 * ArrayList<Bill>(); Bill bill = new Bill();
	 * if(StringUtils.isNullOrEmpty(queryIsPayment)){ bill.setIsPayment(0); }else{
	 * bill.setIsPayment(Integer.parseInt(queryIsPayment)); }
	 * 
	 * if(StringUtils.isNullOrEmpty(queryProviderId)){ bill.setProviderId(0); }else{
	 * bill.setProviderId(Integer.parseInt(queryProviderId)); }
	 * bill.setProductName(queryProductName); billList =
	 * billService.getBillList(bill); model.addAttribute("billList", billList);
	 * model.addAttribute("queryProductName", queryProductName);
	 * model.addAttribute("queryProviderId", queryProviderId);
	 * model.addAttribute("queryIsPayment", queryIsPayment); return "billlist"; }
	 */
	//produces设置返回值的编码
	@RequestMapping(value = "/getRole",method = RequestMethod.GET)
	@ResponseBody
	public Object FindAllRole() {
		//调用业务层中所有用户角色的信息
		List<Role> role=roleservcie.getRoleList();
		return JSONArray.toJSONString(role);
	}
}
