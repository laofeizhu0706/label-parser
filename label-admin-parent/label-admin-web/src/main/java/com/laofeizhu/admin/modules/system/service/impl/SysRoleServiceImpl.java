package com.laofeizhu.admin.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.laofeizhu.admin.modules.system.entity.SysRole;
import com.laofeizhu.admin.modules.system.entity.SysUser;
import com.laofeizhu.admin.modules.system.entity.SysUserRole;
import com.laofeizhu.admin.modules.system.mapper.SysRoleMapper;
import com.laofeizhu.admin.modules.system.service.ISysRoleService;
import com.laofeizhu.admin.modules.system.service.ISysUserRoleService;
import com.laofeizhu.admin.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @Author scott
 * @since 2018-12-19
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private ISysUserService userService;

    @Lazy
    @Autowired
    private ISysUserRoleService userRoleService;

    @Override
    public boolean checkHaveJGRole(String userName) {
        SysUser user = userService.getUserByName(userName);
        if (Objects.nonNull(user)) {
            SysRole role = this.getOne(new QueryWrapper<SysRole>().lambda().eq(SysRole::getRoleCode, "jg"));
            if (Objects.nonNull(role)) {
                SysUserRole userRole = userRoleService.getOne(new QueryWrapper<SysUserRole>().lambda()
                        .eq(SysUserRole::getUserId, user.getId())
                        .eq(SysUserRole::getRoleId, role.getId()));
                return userRole != null;
            }
        }
        return false;
    }
}
