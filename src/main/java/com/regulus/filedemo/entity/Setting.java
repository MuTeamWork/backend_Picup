package com.regulus.filedemo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * TODO
 * <p>
 *
 * @author zsy
 * @version TODO
 * @since 2023-11-01
 */
@Data
@TableName("tb_setting")
public class Setting implements Serializable {

    @Serial
    private static final long serialVersionUID = -5049756437671541193L;

    @TableId(value = "uid")
    private Long uid;

    @TableField("exif")
    private Integer exif;

    @TableField("expire_time")
    private Integer expireTime;


}
