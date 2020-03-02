package com.zlsoft.pj.wx.api.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name = "病人信息")
public class BRXX implements Serializable {
    @Id
    @Column(name = "病人ID")
    private Long 病人id;

    @Column(name = "门诊号")
    private Long 门诊号;

    @Column(name = "住院号")
    private Long 住院号;

    @Column(name = "就诊卡号")
    private String 就诊卡号;

    @Column(name = "卡验证码")
    private String 卡验证码;

    @Column(name = "费别")
    private String 费别;

    @Column(name = "医疗付款方式")
    private String 医疗付款方式;

    @Column(name = "姓名")
    private String 姓名;

    @Column(name = "性别")
    private String 性别;

    @Column(name = "年龄")
    private String 年龄;

    @Column(name = "出生日期")
    private Date 出生日期;

    @Column(name = "出生地点")
    private String 出生地点;

    @Column(name = "身份证号")
    private String 身份证号;

    @Column(name = "其他证件")
    private String 其他证件;

    @Column(name = "身份")
    private String 身份;

    @Column(name = "职业")
    private String 职业;

    @Column(name = "民族")
    private String 民族;

    @Column(name = "国籍")
    private String 国籍;

    @Column(name = "籍贯")
    private String 籍贯;

    @Column(name = "区域")
    private String 区域;

    @Column(name = "学历")
    private String 学历;

    @Column(name = "婚姻状况")
    private String 婚姻状况;

    @Column(name = "家庭地址")
    private String 家庭地址;

    @Column(name = "家庭电话")
    private String 家庭电话;

    @Column(name = "家庭地址邮编")
    private String 家庭地址邮编;

    @Column(name = "监护人")
    private String 监护人;

    @Column(name = "联系人姓名")
    private String 联系人姓名;

    @Column(name = "联系人关系")
    private String 联系人关系;

    @Column(name = "联系人地址")
    private String 联系人地址;

    @Column(name = "联系人电话")
    private String 联系人电话;

    @Column(name = "户口地址")
    private String 户口地址;

    @Column(name = "户口地址邮编")
    private String 户口地址邮编;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "QQ")
    private String qq;

    @Column(name = "合同单位ID")
    private Long 合同单位id;

    @Column(name = "工作单位")
    private String 工作单位;

    @Column(name = "单位电话")
    private String 单位电话;

    @Column(name = "单位邮编")
    private String 单位邮编;

    @Column(name = "单位开户行")
    private String 单位开户行;

    @Column(name = "单位帐号")
    private String 单位帐号;

    @Column(name = "担保人")
    private String 担保人;

    @Column(name = "担保额")
    private BigDecimal 担保额;

    @Column(name = "担保性质")
    private Short 担保性质;

    @Column(name = "就诊时间")
    private Date 就诊时间;

    @Column(name = "就诊状态")
    private Short 就诊状态;

    @Column(name = "就诊诊室")
    private String 就诊诊室;

    @Column(name = "住院次数")
    private Short 住院次数;

    @Column(name = "当前科室ID")
    private Long 当前科室id;

    @Column(name = "当前病区ID")
    private Long 当前病区id;

    @Column(name = "当前床号")
    private String 当前床号;

    @Column(name = "入院时间")
    private Date 入院时间;

    @Column(name = "出院时间")
    private Date 出院时间;

    @Column(name = "在院")
    private Short 在院;

    @Column(name = "IC卡号")
    private String ic卡号;

    @Column(name = "健康号")
    private String 健康号;

    @Column(name = "医保号")
    private String 医保号;

    @Column(name = "险类")
    private Short 险类;

    @Column(name = "查询密码")
    private String 查询密码;

    @Column(name = "登记时间")
    private Date 登记时间;

    @Column(name = "停用时间")
    private Date 停用时间;

    @Column(name = "锁定")
    private Short 锁定;

    @Column(name = "联系人身份证号")
    private String 联系人身份证号;

    @Column(name = "病人类型")
    private String 病人类型;

    @Column(name = "主页ID")
    private Integer 主页id;

    @Column(name = "结算模式")
    private Short 结算模式;

    @Column(name = "手机号")
    private String 手机号;

    private static final long serialVersionUID = 1L;
}