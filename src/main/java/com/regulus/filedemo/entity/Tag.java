package com.regulus.filedemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
@TableName("tb_tag")
public class Tag implements Serializable {
    @Serial
    private static final long serialVersionUID = -5049756437671541193L;

    @TableId(value = "tid")
    private long tid;

    @TableField(value = "tag")
    private String tag;


}
