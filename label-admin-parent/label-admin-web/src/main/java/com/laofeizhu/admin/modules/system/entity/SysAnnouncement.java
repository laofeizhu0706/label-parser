package com.laofeizhu.admin.modules.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.laofeizhu.admin.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: 系统通告表
 * @Author: ls
 * @Date:  2019-01-02
 * @Version: V1.0
 */
@Data
@TableName("sys_announcement")
public class SysAnnouncement implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.UUID)
    private String id;
    /**
     * 标题
     */
    @Excel(name = "标题", width = 15)
    private String titile;
    /**
     * 内容
     */
    @Excel(name = "内容", width = 30)
    private String msgContent;
    /**
     * 开始时间
     */
    @Excel(name = "开始时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date startTime;
    /**
     * 结束时间
     */
    @Excel(name = "结束时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date endTime;
    /**
     * 发布人
     */
    @Excel(name = "发布人", width = 15)
    private String sender;
    /**
     * 优先级（L低，M中，H高）
     */
    @Excel(name = "优先级", width = 15, dicCode = "priority")
    @Dict(dicCode = "priority")
    private String priority;
    
    /**
     * 消息类型1:通知公告2:系统消息
     */
    @Excel(name = "消息类型", width = 15, dicCode = "msg_category")
    @Dict(dicCode = "msg_category")
    private String msgCategory;
    /**
     * 通告对象类型（USER:指定用户，ALL:全体用户）
     */
    @Excel(name = "通告对象类型", width = 15, dicCode = "msg_type")
    @Dict(dicCode = "msg_type")
    private String msgType;
    /**
     * 发布状态（0未发布，1已发布，2已撤销）
     */
    @Excel(name = "发布状态", width = 15, dicCode = "send_status")
    @Dict(dicCode = "send_status")
    private String sendStatus;
    /**
     * 发布时间
     */
    @Excel(name = "发布时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date sendTime;
    /**
     * 撤销时间
     */
    @Excel(name = "撤销时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date cancelTime;
    /**
     * 删除状态（0，正常，1已删除）
     */
    private String delFlag;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date createTime;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date updateTime;
    /**
     * 指定用户
     **/
    private String userIds;
}
