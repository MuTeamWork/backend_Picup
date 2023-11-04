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
 * @since 2023-11-02
 */
@Data
public class OptionRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -5548049959272694576L;

    private String username;

    private String mail;

    private Boolean isExifDataKept;

    private Integer expireTime;

}
