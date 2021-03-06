package com.cy.example.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cy.example.config.WebConfig;
import com.cy.example.entity.system.LoginRecordEntity;
import com.cy.example.entity.system.SysDepartmentEntity;
import com.cy.example.entity.system.SysMenuEntity;
import com.cy.example.entity.system.SysNoticeEntity;
import com.cy.example.entity.system.SysPermissionEntity;
import com.cy.example.entity.system.SysRoleEntity;
import com.cy.example.entity.system.SysUserEntity;
import com.cy.example.service.IDepartmentService;
import com.cy.example.service.ILoginRecordService;
import com.cy.example.service.IMenuService;
import com.cy.example.service.INoticeService;
import com.cy.example.service.IPermissionService;
import com.cy.example.service.IRoleService;
import com.cy.example.service.IUserService;

@Controller
public class SystemController {
	
	@Autowired
	private IPermissionService permisService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IMenuService menuService;
	
	@Autowired
	private ILoginRecordService loginRecordService;
	
	@Autowired
	private IDepartmentService departmentService;
	
	@Autowired
	private INoticeService noticeService;
	
	@Value("${swallow.system.name}")
	private String SYS_NAME;

	@RequestMapping("/index")
	public String showIndex(ModelMap map) {
		map.put("SYS_NAME", SYS_NAME);
		return "index";
	}

	@RequestMapping("/main")
	public String showMain(HttpSession session, ModelMap map) {
		SysUserEntity user = (SysUserEntity) session
				.getAttribute(WebConfig.LOGIN_USER);
		List<SysMenuEntity> menuList = menuService.findAll();
		Map<String, List<SysMenuEntity>> data = new HashMap<String, List<SysMenuEntity>>();
		for (int i = 0; i < menuList.size(); i++) {
			if("[root]".equals(menuList.get(i).getC_node())){
				List<SysMenuEntity> tool = new ArrayList<SysMenuEntity>();
				for (int j = 0; j < menuList.size(); j++) {
					if(String.valueOf(menuList.get(i).getId()).equals(menuList.get(j).getC_node())){
						tool.add(menuList.get(j));
					}
				}
				data.put(menuList.get(i).getC_menuName(), tool);
			}
		}
		//在线人数统计
		map.put("user", user);
		map.put("menuList", data);
		map.put("SYS_NAME", SYS_NAME);
		map.put("activeNum", WebConfig.getActiveUserSum());
		return "main/main";
	}

	@RequestMapping("/loginOut")
	public String loginOut(HttpSession session, ModelMap map) {
		map.put("SYS_NAME", SYS_NAME);
		session.removeAttribute(WebConfig.LOGIN_USER);
		return "index";
	}
	
	@RequestMapping("/menu/home")
	public String showHome(ModelMap map) {
		Map<String, Object> loginRecord = loginRecordService.recentLoginCount();
		Page<LoginRecordEntity> recordList = loginRecordService.selectPage(new Page<LoginRecordEntity>(1, 20)
				, new EntityWrapper<LoginRecordEntity>().orderBy("c_createDate",false));
		Page<SysNoticeEntity> list = noticeService.selectPage(new Page<SysNoticeEntity>(1, 10)
				, new EntityWrapper<SysNoticeEntity>().setSqlSelect("c_title,c_content,n_order,c_createDate,c_updateDate,id")
					.orderBy("c_updateDate",false));
		map.put("noticeList", list.getRecords());
		map.put("loginRecord", loginRecord);
		map.put("recordList", recordList.getRecords());
		return "main/home";
	}

	@RequestMapping("/menu/calendarManage")
	public String calendarManage() {
		return "calendarManage";
	}

	@RequestMapping("/menu/userManage")
	public String userManage(ModelMap map) {
		List<SysDepartmentEntity> departList = departmentService.selectList(new EntityWrapper<SysDepartmentEntity>());
		map.put("departList", departList); 
		return "userManage";
	}
	
	@RequestMapping("/menu/menuManage")
	public String menuManage(ModelMap map) {
		List<SysMenuEntity> menuList = menuService.findRoot();
		map.put("menuList", menuList);
		return "menuManage";
	}

	@RequestMapping("/menu/loginRecordManage")
	public String loginRecordManage() {
		return "loginRecordManage";
	}

	@RequestMapping("/menu/uploadFile")
	public String uploadFile() {
		return "test_uploadFile";
	}
	
	@RequestMapping("/menu/roleManage")
	public String roleManage() {
		return "roleManage";
	}
	
	@RequestMapping("/menu/permissionManage")
	public String permissionManage() {
		return "permissionManage";
	}
	
	@RequestMapping("/menu/user_roleManage")
	public String user_roleManage(ModelMap map) {
		List<SysUserEntity> userList = userService.selectList(new EntityWrapper<SysUserEntity>());
		List<SysRoleEntity> roleList = roleService.selectList(new EntityWrapper<SysRoleEntity>());
		map.put("userList", userList);
		map.put("roleList", roleList);
		return "user_roleManage";
	}
	
	@RequestMapping("/menu/role_permisManage")
	public String role_permisManage(ModelMap map) {
		List<SysPermissionEntity> permisList = permisService.selectList(new EntityWrapper<SysPermissionEntity>());
		List<SysRoleEntity> roleList = roleService.selectList(new EntityWrapper<SysRoleEntity>());
		map.put("permisList", permisList);
		map.put("roleList", roleList);
		return "role_permisManage";
	}
	
	@RequestMapping("/menu/workflow/deployManage")
	public String deployManage(ModelMap map) {
		
		return "workflow/deployManage";
	}
	
	@RequestMapping("/menu/workflow/processDefinitionManage")
	public String processDefinitionManage(ModelMap map) {
		return "workflow/processDefinitionManage";
	}
	
	@RequestMapping("/menu/workflow/leaveBillManage")
	public String leaveBillManage(ModelMap map) {
		return "workflow/leaveBillManage";
	}
	
	@RequestMapping("/menu/workflow/taskManage")
	public String taskManage(ModelMap map) {
		return "workflow/taskManage";
	}
	
	@RequestMapping("/menu/departmentManage")
	public String departmentManage(ModelMap map) {
		return "departmentManage";
	}
	
	@RequestMapping("/menu/noticeManage")
	public String noticeManage(ModelMap map) {
		return "noticeManage";
	}
	
}
