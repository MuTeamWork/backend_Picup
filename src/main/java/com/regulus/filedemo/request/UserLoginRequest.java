package com.regulus.filedemo.request;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
public class UserLoginRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -5548049959272694576L;

    private String username;

    private String password;

    private String mail;
}
