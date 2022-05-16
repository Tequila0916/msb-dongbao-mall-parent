package com.msb.dongbao.pms.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 规格项
 * </p>
 *
 * @author 马士兵 · 项目架构部
 * @version V1.0
 * @contact
 * @date 2020-06-08
 * @company 马士兵（北京）教育科技有限公司 (http://www.mashibing.com/)
 * @copyright 马士兵（北京）教育科技有限公司 · 项目架构部
 */
@Data
@EqualsAndHashCode

public class SpecificationOption implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 规格项主键id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 规格项名称
     */
    private String name;

    /**
     * 规格id
     */
    private Long relSpecId;

    /**
     * 创建时间
     */
    private Long gmtCreate;

    /**
     * 更新时间
     */
    private Long gmtModified;

    /**
     * 逻辑删除 0-未删除，1-删除
     */
    private Boolean enabled;

    /**
     * 更新人uid
     */
    private String modifiedUid;

    /**
     * 创建人uid
     */
    private String createUid;

    /**
     * 更新人昵称
     */
    private String modifiedUname;

    /**
     * 创建人昵称
     */
    private String createUname;


}
