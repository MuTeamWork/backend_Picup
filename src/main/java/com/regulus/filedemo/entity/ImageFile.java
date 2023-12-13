package com.regulus.filedemo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

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
@TableName("tb_file")
public class ImageFile implements Serializable {

    @Serial
    private static final long serialVersionUID = -5433842390424282445L;

    @TableId(value = "fid")
    private Long fid;

    @TableField("file_name")
    private String fileName;

    @TableField("file")
    private String file;

    @TableField("thumbnail")
    private String thumbnail;

    @TableField("expire_time")
    private String expireTime;

}
