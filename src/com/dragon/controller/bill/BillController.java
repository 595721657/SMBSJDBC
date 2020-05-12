package com.dragon.controller.bill;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysql.jdbc.StringUtils;

import com.dragon.pojo.Bill;
import com.dragon.pojo.Provider;
import com.dragon.pojo.User;
import com.dragon.service.bill.BillService;
import com.dragon.service.provider.ProviderService;

//订单的controller
@Controller
//订单板块
@RequestMapping("/bill")
public class BillController {
	@Autowired
	private BillService billService;
	@Autowired
	private ProviderService providerService;
	
	//展示全部订单信息
	@RequestMapping(value="/billlist.html",method=RequestMethod.GET)
	public String query(@RequestParam(name="queryProductName",required = false) String queryProductName,
			@RequestParam(name="queryProviderId",required = false) String queryProviderId,
			@RequestParam(name="queryIsPayment",required = false) String queryIsPayment,
			Model model,HttpServletRequest req) {
		//声明一个供应商集合
		List<Provider> providerList = new ArrayList<Provider>();	
		providerList = providerService.getProviderList("","");
		req.setAttribute("providerList", providerList);
		if(StringUtils.isNullOrEmpty(queryProductName)){
			queryProductName = "";
		}
		//创建一个订单集合
		List<Bill> billList = new ArrayList<Bill>();
		Bill bill = new Bill();
		if(StringUtils.isNullOrEmpty(queryIsPayment)){
			bill.setIsPayment(0);
		}else{
			bill.setIsPayment(Integer.parseInt(queryIsPayment));
		}
		
		if(StringUtils.isNullOrEmpty(queryProviderId)){
			bill.setProviderId(0);
		}else{
			bill.setProviderId(Integer.parseInt(queryProviderId));
		}
		bill.setProductName(queryProductName);
		billList = billService.getBillList(bill);
		model.addAttribute("billList", billList);
		model.addAttribute("queryProductName", queryProductName);
		model.addAttribute("queryProviderId", queryProviderId);
		model.addAttribute("queryIsPayment", queryIsPayment);
		return "billlist";
	}
	
	//实现订单页面跳转的方法
	@RequestMapping(value="/addbill.html",method=RequestMethod.GET)	
	public String addBill(@ModelAttribute("bill") Bill bill,Model model){
		List<Provider> providerList = new ArrayList<Provider>();		
		providerList = providerService.getProviderList("","");
		model.addAttribute("providerList", providerList);
		return "billadd";
	}
	//订单增加的方法
	@RequestMapping(value="/billsave.html",method=RequestMethod.POST)
	public String billSave(Bill bill,HttpSession session) {
		//为订单创建时间赋值
		bill.setCreationDate(new Date());
		boolean isOk=billService.add(bill);
		if(isOk) {
            //增加成功
			return "redirect:billlist.html";
		}else {
			//增加失败
			return "billadd";
		}
	}
	
	//通过id查询订单信息的方法
	@RequestMapping(value = "/view.html",method = RequestMethod.GET)
	public String FindById(@RequestParam("billid") String billid,HttpServletRequest req) {
		 //通过id查询数据的方法
		 Bill bill=billService.getBillById(billid);
		 //保存到作用域
		 req.setAttribute("bill", bill);
		 return "billview";
	}
	
	//修改跳转页面
	@RequestMapping(value="/updatebill.html",method=RequestMethod.GET)
	public String updateUser(@ModelAttribute("bill") Bill bill,HttpServletRequest req,@RequestParam("billid") String billid){
		 //通过id查询数据的方法
		 Bill bills=billService.getBillById(billid);
		 List<Provider> provider=providerService.getProviderList("", "");
		 //保存到作用域
		 req.setAttribute("bills", bills);
		 req.setAttribute("provider", provider);
		return "billmodify";
	}
	
	//修改订单的方法
	@RequestMapping(value="/billmodify.html",method=RequestMethod.POST)
	public String userModify(Bill bill,HttpSession session) {
		int modifyid=((User)session.getAttribute("loginuser")).getId();
		//为修改建者和修改时间赋值
		bill.setModifyBy(modifyid);
		bill.setModifyDate(new Date());
		//调用修改的方法
		boolean isOk=billService.modify(bill);
		//修改成功
		if(isOk) {
			//实现页面跳转
			return "redirect:billlist.html";
		}else {
			return "redirect:updatebill.html?billid="+bill.getId().toString();
		}
	}
	
	//删除订单的方法
	@RequestMapping(value = "/delbill.html",method = RequestMethod.GET)
	public String delUser(@RequestParam("billid") String billid) {
		//删除成功
		if(billService.deleteBillById(billid)) {
			return "redirect:billlist.html";
		}else {
			//删除失败
			return "redirect:billlist.html";
		}		
	}
}
