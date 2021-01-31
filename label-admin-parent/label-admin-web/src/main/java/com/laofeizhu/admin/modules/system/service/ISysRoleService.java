package com.laofeizhu.admin.modules.system.service;

import com.laofeizhu.admin.modules.system.entity.SysRole;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @Author scott
 * @since 2018-12-19
 */
public interface ISysRoleService extends IService<SysRole> {
	
    boolean checkHaveJGRole(String userName);
}
