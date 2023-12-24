package com.regulus.filedemo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
@TableName("tag_file")
public class TagFile implements Serializable {
    @Serial
    private static final long serialVersionUID = -5049756437671541193L;

    @TableId(value = "fid")
    private long fid;

    @TableField(value = "tid")
    private long tid;

}
