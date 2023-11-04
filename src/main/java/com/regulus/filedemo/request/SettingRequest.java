package com.regulus.filedemo.request;

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
public class SettingRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -5049756437671541193L;

    private Integer exif;

    private Integer expireTime;


}
