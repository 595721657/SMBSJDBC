package com.dragon.controller.provider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysql.jdbc.StringUtils;

import com.dragon.pojo.Provider;
import com.dragon.service.provider.ProviderService;
//供应商的controller 
@Controller
//供应商板块
@RequestMapping("/provider")
public class ProviderController {
	@Autowired
	private ProviderService providerService;
	//查询所有数据的方法
	@RequestMapping(value="/providerlist.html",method=RequestMethod.GET)
	public String query(
			@RequestParam(name="queryProName",required = false) String queryProName,
			@RequestParam(name="queryProCode",required = false) String queryProCode,
			Model model) {

		if(StringUtils.isNullOrEmpty(queryProName)){
			queryProName = "";
		}
		if(StringUtils.isNullOrEmpty(queryProCode)){
			queryProCode = "";
		}
		List<Provider> providerList = new ArrayList<Provider>();
		
		providerList = providerService.getProviderList(queryProName,queryProCode);
		model.addAttribute("providerList", providerList);
		model.addAttribute("queryProName", queryProName);
		model.addAttribute("queryProCode", queryProCode);
		return "providerlist";
	}
	//实现供应商页面跳转的方法
	@RequestMapping(value="/addprovider.html",method=RequestMethod.GET)	
	public String addProvider(@ModelAttribute("provider") Provider provider,Model model){
		return "provideradd";
	}
	//供应商保存的方方法
	@RequestMapping(value="/providersave.html",method=RequestMethod.POST)
	public String providerSave(Provider provider) {
		//为供应商的创建时间赋值
		provider.setCreationDate(new Date());
		//调用保存供应商的方法
		boolean isOk=providerService.add(provider);
		if(isOk) {
			//保存成功
		    return "redirect:providerlist.html";
		}else {
			//保存失败
			return "provideradd";
		}
	}
}
