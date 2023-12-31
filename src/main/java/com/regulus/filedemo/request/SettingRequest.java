package com.regulus.filedemo.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class SettingRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -5049756437671541193L;

    private Integer exif;

    private Integer expireTime;


}
