package com.dragon.controller.user;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.dragon.pojo.Role;
import com.dragon.pojo.User;
import com.dragon.service.role.RoleService;
import com.dragon.service.user.UserService;
import com.mysql.jdbc.StringUtils;

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
		session.setAttribute("loginuser", user);
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
	public String userSave(User user,HttpSession session,
	        HttpServletRequest request,@RequestParam(name = "attchs",required = false) MultipartFile[] attchs
	        /*@RequestParam(value="a_idPicPath",required=false) MultipartFile attch*/) {
		//声明两个上传的文件变量
		String idpicpath=null;
		String workpicpath=null;
		//声明错误变量
		String errorInfo=null;
		//声明一个是否跳转到增加页面
		boolean flag=true;
		//声明文件上传的路劲
		String path=session.getServletContext().getRealPath("static"+File.separator+"upload");
		//遍历数组
		for (int i = 0; i < attchs.length; i++) {
			//获得数组中的mulitpartfile对象
			MultipartFile attch=attchs[i];
			if (i==0) {
				//证件照
				errorInfo="idError";
			}else if(i==1){
				//工作证
				errorInfo="workError";
			}
			//判断上传文件对象是否为空
			if(!attch.isEmpty()) {
				//获得源文件
				String oldFile=attch.getOriginalFilename();
				//获得源文件的后缀
				String suffix=FilenameUtils.getExtension(oldFile);
				//设置文件的大小
				int fileSize=512000;
				//判断文件大小是否超了
				if(attch.getSize()>fileSize) {
					request.setAttribute("error", "上传文件的大小超过！！");
					flag=false;
				}else if(suffix.equalsIgnoreCase("jpg") || suffix.equalsIgnoreCase("png") ||
						suffix.equalsIgnoreCase("gif")) {
					//对源文件进行处理
					String fileName = System.currentTimeMillis() +RandomUtils.nextInt(0,1000000)+oldFile;
				    //创建一个文件对象
					File uploadFile=new File(path,fileName);
					//判断文件是否存在
					if (!uploadFile.exists()) {
						// 创建文件以及文件夹
						 uploadFile.mkdir();
					}
					//实现文件上传
					try {
						attch.transferTo(uploadFile);
					} catch (Exception e) {
						e.printStackTrace();
						request.setAttribute(errorInfo, "上传文件的大小超过！！");
						flag=false;
					}
					//为保存到数据库中的路劲变量赋值
					if(i==0) {
						idpicpath=path+File.separator+fileName;
					}else if(i==2) {
						workpicpath=path+File.separator+fileName;
					}
				}else {
					request.setAttribute("error", "图片格式不正确"); 
					flag=false;
				}
			}
		}
		/*
		 * // 保存上传文件的名称 String idPicPath = null; System.out.println("attch ===== >" +
		 * attch); // File.separator 就相当于/，这样写的目的是为了方便系统的迁移 String path =
		 * session.getServletContext().getRealPath("static"+File.separator+"upload"); //
		 * 判断上传文件是否为非 if(!attch.isEmpty()) { // 获得上传文件的名称 String oldFileName =
		 * attch.getOriginalFilename(); System.out.println("上传文件的名字是："+ oldFileName); //
		 * 获得上传文件的后缀名 String suffix = FilenameUtils.getExtension(oldFileName);
		 * System.out.println("上传文件的后缀名是：" + suffix); // 设置文件上传的大小 int fileSize =
		 * 521000; // 上传文件的后缀名只能是 jpg,png,gif // 判断上传文件的大小是否超过了设置的大小，响应一个错误信息
		 * if(attch.getSize() > fileSize) { request.setAttribute("error",
		 * "上传文件的大小不能超过500K"); // 重新回到添加页面 return "useradd"; }else
		 * if(suffix.equalsIgnoreCase("jpg") || suffix.equalsIgnoreCase("png") ||
		 * suffix.equalsIgnoreCase("gif")) { // 对上传的文件进行一个设置 时间戳+随机数+head.jpg String
		 * fileName = System.currentTimeMillis() +RandomUtils.nextInt(0,
		 * 1000000)+oldFileName; // 创建一个上传文件的名称 File tagfile = new File(path, fileName);
		 * // 判断上传文件是否存在，如果不存在就创建 if (!tagfile.exists()) { // 创建文件以及文件夹
		 * tagfile.mkdirs(); } try { // 进行文件的上传 attch.transferTo(tagfile); } catch
		 * (Exception e) { request.setAttribute("error", "上传文件失败！！"); return "useradd";
		 * } // 为保存到数据库中的变量赋值 idPicPath = path+File.separator+fileName; }else {
		 * request.setAttribute("error", "图片格式不正确"); return "useradd"; } }
		 */
	       if (flag) {
	    	   // 需要有创建者id,其实就是去获得保存在session中的用户对象的id值
		        int createdBy = ((User)session.getAttribute("loginuser")).getId();
		        // 需要创建时间,获得系统的当前时间
		        user.setCreatedBy(createdBy);
		        user.setCreationDate(new Date());
		        user.setIdpicpath(idpicpath);
		        user.setWorkpicpath(workpicpath);
		        // 调用增加用户的方法
		        boolean isOk = userService.add(user);
		        if (isOk) {
		            // 成功后页面上的数据也需要刷新，使用这里我们访问
		            return "redirect:userlist.html";
		        }else {
		            return "useradd";
		        }
		} else {
			 return "useradd";
		}
	 }
	
	//通过spring实现增加的方法
	@RequestMapping(value="/add.html",method=RequestMethod.GET)
	public String add(@ModelAttribute("user") User user) {
		return "user/useradd";
	}
	@RequestMapping(value="/add.html",method=RequestMethod.POST)
	//为了使用jsr303的数据校验框架,需要在实体类前面加上@valid注解
    //在参数后面紧跟一个参数BindingResult,改参数的作用就是去获取验证的结果
	public String addsave(@Valid User user,BindingResult result,HttpSession session) {
		//在执行之前,先判断是否有异常,有异常为true
		if(result.hasErrors()) {
			//直接跳转增加页面
			return "user/useradd";
		}
		int createdBy=((User)session.getAttribute("loginuser")).getId();
		//为创建者赋值
		user.setCreatedBy(createdBy);
		user.setCreationDate(new Date());
		//调用增加方法
		boolean isOk=userService.add(user);
		if(isOk) {
			//增加成功 跳转展示页面
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
		int modifyid=((User)session.getAttribute("loginuser")).getId();
		//为修改建者和修改时间赋值
		user.setModifyBy(modifyid);
		user.setModifyDate(new Date());
		//调用修改的方法
		boolean isOk=userService.modify(user);
		//修改成功
		if(isOk) {
			//实现页面跳转
			return "redirect:userlist.html";
		}else {
			return "redirect:updateuser.html?userid="+user.getId();
		}
	}
	
	//删除用户的方法
	@RequestMapping(value = "/deluser.html",method = RequestMethod.GET)
	public String delUser(@RequestParam("userid") String userid) {
		//删除成功
		if(userService.deleteUserById(Integer.parseInt(userid))) {
			return "redirect:userlist.html";
		}else {
			//删除失败
			return "redirect:userlist.html";
		}		
	}
	
	//修改密码页面跳转
	@RequestMapping(value = "/pwdmodify.html",method = RequestMethod.GET)
	public String updatepwd() {
		return "pwdmodify";
	}
	//修改当前账号密码
	@RequestMapping(value = "/updatpwd.html",method = RequestMethod.POST)
	public String pwdModify(HttpSession session,@RequestParam("oldpassword") String oldpassword,@RequestParam("newpassword") String newpassword,@RequestParam("rnewpassword") String rnewpassword) {
		/* User user=((User)session.getAttribute("loginuser")); */
		return "";
	}
	
	//验证用户编码是否存在
	@RequestMapping(value = "/userExists",method =RequestMethod.GET )
	@ResponseBody  //只要方法放回json对象 就必须添加这个注解
	public Object userCodeExist(@RequestParam("userCode") String userCode) {
		//创建一个map集合保存响应的数据
		Map<String,String> map=new HashMap<String,String>();
		//判断是否为空
		if(StringUtils.isNullOrEmpty(userCode)) {
			map.put("userCode","exist");
		}else {
		   //调用业务层的方法
		   User	user=userService.selectUserCodeExist(userCode);
		   //判断user是否为空
		   if(user ==null) {
			   map.put("userCode","noexist");
		   }else {
			   map.put("userCode","exist");
		   }
		}
		return JSONArray.toJSONString(map);
	}
	
	//通过id查询用户信息的方法
	@RequestMapping(value = "/showInfo.html",method = RequestMethod.GET)
	@ResponseBody
	public Object showInfo(@RequestParam("userid") String userid) {
		 //通过id查询数据的方法
		 User user=userService.getUserById(userid);
		 return JSON.toJSONString(user);
	}
}
