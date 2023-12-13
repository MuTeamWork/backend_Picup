package com.regulus.filedemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * User实体类
 *
 * @author zsy
 * @version TODO
 * @since 2023-10-30
 */
@Data
@TableName("tb_user")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 5677788635368326963L;

    @TableId(value = "uid")
    private Long uid;

    @TableField("username")
    private String username;

    @TableField("mail")
    private String mail;

    @TableField("password")
    private String password;

    @TableField("avatar")
    private String avatar;

    @TableField("thumbnail")
    private String thumbnail;

    @TableField("role")
    private int role;

    @TableField(exist = false)
    private String address;

}
