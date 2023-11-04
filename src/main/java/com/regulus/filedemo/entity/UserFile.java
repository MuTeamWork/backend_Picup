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
 * @since 2023-10-31
 */
@Data
@TableName("user_file")
public class UserFile implements Serializable {

    @Serial
    private static final long serialVersionUID = 7649431698529446542L;

    @TableField(value = "fid")
    private long fid;

    @TableField(value = "uid")
    private long uid;


}
