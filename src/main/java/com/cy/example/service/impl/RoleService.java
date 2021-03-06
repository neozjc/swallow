package com.cy.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cy.example.carrier.PageCa;
import com.cy.example.entity.system.SysRoleEntity;
import com.cy.example.mapper.system.RoleMapper;
import com.cy.example.service.IRoleService;

@Service
public class RoleService extends ServiceImpl<RoleMapper, SysRoleEntity>
	implements IRoleService{
	
	@Autowired
	private RoleMapper roleMapper;
	

	public List<SysRoleEntity> searchAll(SysRoleEntity role,
			PageCa page) {
		// TODO Auto-generated method stub
		return roleMapper.searchAll(role, page);
	}

	public int searchAllCount(SysRoleEntity role) {
		// TODO Auto-generated method stub
		return roleMapper.searchAllCount(role);
	}
}
