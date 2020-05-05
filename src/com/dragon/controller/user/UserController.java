package com.dragon.controller.user;

import java.io.IOException;
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

import com.dragon.pojo.Role;
import com.dragon.pojo.User;
import com.dragon.service.role.RoleService;
import com.dragon.service.user.UserService;
import tools.PageSupport;

//用户的controller
@Controller
//用户板块
@RequestMapping("/user")
public class UserController {
	//生成唯一标识
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	//实现登录跳转的方法
	@RequestMapping(value = "/login.html")
	public String login() {
		//鐩存帴璁块棶login銆俲sp椤甸潰
		return "login";
	}
	//进行登录验证的方法
	@RequestMapping(value ="/dologin.html",method=RequestMethod.POST)
	public String dologin(@RequestParam("userCode")String userCode,
			@RequestParam("userPassword") String userPassword,
			HttpServletRequest req,
			HttpSession session) throws Exception {
	//验证密码和用户名是否正确	
	User user=userService.login(userCode, userPassword);
	//登陆成功
	if(user != null) {
		//将登录的用户对象保存到session中
		session.setAttribute("user", user);
		return "redirect:frame.html";
	}else {
		//登录失败
		/*
		 * req.setAttribute("error", "鐢ㄦ埛鍚嶆垨瀵嗙爜閿欒"); return "login";
		 */
		throw new Exception("用户名或者密码错误");
	}
	}
	
	//瀹氫箟涓�涓疄鐜拌浆鍙戠殑璇锋眰鏂规硶
	@RequestMapping("/frame.html")
	public String main() {
		return "frame";
	}
	
	//妯℃嫙寮傚父鐧诲綍鐨勬柟娉�
	@RequestMapping(value="/exLogin.html",method=RequestMethod.GET)
	public String exLogin(@RequestParam("userCode")String userCode,
			@RequestParam("userPassword") String userPassword) {
		User user=userService.login(userCode, userPassword);
		if(user==null) {
			//鎶涘嚭涓�涓紓甯�
			throw new RuntimeException("鐢ㄦ埛鍚嶆垨瀵嗙爜閿欒");
		}
		return "redirect:frame.html";
	}
	
	//注销账户
	@RequestMapping(value="/Loginout",method=RequestMethod.GET)
	public String loginout(HttpSession session) {
		if(session !=null) {
			//娓呯┖session
			session.invalidate();
		}
		return "redirect:login.html";
	}
	
	//用户名展示
	@RequestMapping(value="/userlist.html",method=RequestMethod.GET)
	public String query(
			@RequestParam(name="queryname",required = false) String queryname,
			@RequestParam(name="queryUserRole",required = false) String queryUserRole,
			@RequestParam(name="pageIndex",required = false) String pageIndex,
			Model model) throws IOException{
		//获取roleid
		int _queryUserRole = 0;
		List<User> userList = null;
		//页面展示的条数
    	int pageSize = 5;
    	//页码
    	int currentPageNo = 1;
		if(queryname == null){
			queryname = "";
		}
		//判断角色id是否为空
		if(queryUserRole != null && !queryUserRole.equals("")){
			_queryUserRole = Integer.parseInt(queryUserRole);
		}
		//判断页面是否为空
    	if(pageIndex != null){
    		try{
    			currentPageNo = Integer.valueOf(pageIndex);
    		}catch(NumberFormatException e){
    			//浣跨敤鍏ㄥ眬寮傚父鏉ヨ繘琛屽紓甯镐俊鎭殑鏄剧ず
    			throw new RuntimeException("椤电爜鐨勫�间笉姝ｇ‘锛侊紒锛侊紒锛�");
    			//response.sendRedirect("error.jsp");
    		}
    	}	
    	//获取数据总数
    	int totalCount	= userService.getUserCount(queryname,_queryUserRole);
    	//创建一个分页对象
    	PageSupport pages=new PageSupport();
    	pages.setCurrentPageNo(currentPageNo);
    	pages.setPageSize(pageSize);
    	pages.setTotalCount(totalCount);
    	//计算页面的数据条数
    	int totalPageCount = pages.getTotalPageCount();    	
    	//判断页码是否不规范
    	if(currentPageNo < 1){
    		currentPageNo = 1;
    	}else if(currentPageNo > totalPageCount){
    		currentPageNo = totalPageCount;
    	}
		
		//通过多条件实现数据查询
		userList = userService.getUserList(queryname,_queryUserRole,currentPageNo, pageSize);
		//将数据保存到 作用域
		model.addAttribute("userList", userList);
		List<Role> roleList = null;
		roleList = roleService.getRoleList();
		model.addAttribute("roleList", roleList);
		model.addAttribute("queryUserName", queryname);
		model.addAttribute("queryUserRole", queryUserRole);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("currentPageNo", currentPageNo);
		return "userlist";
	}
	
	//实现增加用户页码跳转的方法
	@RequestMapping(value="/adduser.html",method=RequestMethod.GET)
	public String addUser(@ModelAttribute("user") User user){
		return "useradd";
	}
	//保存用户的方法
	@RequestMapping(value="/usersave.html",method=RequestMethod.POST)
	public String userSave(User user,HttpSession session) {
		//获取保存到作用域的id
		int createdBy=((User)session.getAttribute("user")).getId();
		//为创建者和创建时间赋值
		user.setCreatedBy(createdBy);
		user.setCreationDate(new Date());
		//调用增加的方法
		boolean isOk=userService.add(user);
		//增加成功
		if(isOk) {
			//实现页面跳转
			return "redirect:userlist.html";
		}else {
			return "useradd";
		}
	}
	//通过spring实现增加的方法
	@RequestMapping(value="/add.html",method=RequestMethod.GET)
	public String add(@ModelAttribute("user") User user) {
		return "user/useradd";
	}
	@RequestMapping(value="/add.html",method=RequestMethod.POST)
	public String addsave(User user,HttpSession session) {
		int createdBy=((User)session.getAttribute("user")).getId();
		//闇�瑕佸垱寤烘椂闂�,鑾峰緱绯荤粺鐨勫綋鍓嶆椂闂�
		user.setCreatedBy(createdBy);
		user.setCreationDate(new Date());
		//璋冪敤澧炲姞鐢ㄦ埛鐨勬柟娉�
		boolean isOk=userService.add(user);
		if(isOk) {
			//鎴愬姛鍚庨〉闈笂鐨勬暟鎹篃瑕佸埛鏂帮紝浣跨敤杩欓噷鎴戜滑璁块棶url			
			return "redirect:userlist.html";
		}else {
			return "user/useradd";
		}
	}
	
	//通过id查询用户信息的方法
	@RequestMapping(value = "/view.html",method = RequestMethod.GET)
	public String FindById(@RequestParam("userid") String userid,HttpServletRequest req) {
		 //通过id查询数据的方法
		 User user=userService.getUserById(userid);
		 //保存到作用域
		 req.setAttribute("user", user);
		 return "userview";
	}
	
	//修改跳转页面
	@RequestMapping(value="/updateuser.html",method=RequestMethod.GET)
	public String updateUser(@ModelAttribute("user") User user,HttpServletRequest req,@RequestParam("userid") String userid){
		 //通过id查询数据的方法
		 User users=userService.getUserById(userid);
		 //保存到作用域
		 req.setAttribute("users", users);
		return "usermodify";
	}
	
	//修改用户的方法
	@RequestMapping(value="/usermodify.html",method=RequestMethod.POST)
	public String userModify(User user,HttpSession session) {
		//获取保存到作用域的id
		int createdBy=((User)session.getAttribute("user")).getId();
		//为修改建者和修改时间赋值
		user.setModifyBy(createdBy);
		user.setModifyDate(new Date());
		//调用修改的方法
		boolean isOk=userService.modify(user);
		//修改成功
		if(isOk) {
			//实现页面跳转
			return "redirect:userlist.html";
		}else {
			return "redirect:usermodify.html";
		}
	}
	
	//删除用户的方法
	@RequestMapping(value = "/deluser.html",method = RequestMethod.GET)
	public String delUser(@RequestParam("userid") String userid) {
		//删除失败
		if(userService.deleteUserById(Integer.parseInt(userid))) {
			return "redirect:userlist.html";
		}else {
			return "redirect:userlist.html";
		}
		
	}
}
