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

//��ɫ��controller
@Controller
//��ɫ���
@RequestMapping("/role")
public class RoleControllor {
	@Autowired
	private RoleService roleservcie;
	// չʾȫ��������Ϣ
	/*
	 * @RequestMapping(value="/rolelist.html",method=RequestMethod.GET) public
	 * String query(@RequestParam(name="queryProductName",required = false) String
	 * queryProductName,
	 * 
	 * @RequestParam(name="queryProviderId",required = false) String
	 * queryProviderId,
	 * 
	 * @RequestParam(name="queryIsPayment",required = false) String queryIsPayment,
	 * Model model) { //����һ����ɫ���� List<Role> roleList = new ArrayList<Role>();
	 * roleList = roleservcie.getRoleList(); model.addAttribute("providerList",
	 * providerList); if(StringUtils.isNullOrEmpty(queryProductName)){
	 * queryProductName = ""; } //����һ���������� List<Bill> billList = new
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
	//produces���÷���ֵ�ı���
	@RequestMapping(value = "/getRole",method = RequestMethod.GET)
	@ResponseBody
	public Object FindAllRole() {
		//����ҵ����������û���ɫ����Ϣ
		List<Role> role=roleservcie.getRoleList();
		return JSONArray.toJSONString(role);
	}
}
