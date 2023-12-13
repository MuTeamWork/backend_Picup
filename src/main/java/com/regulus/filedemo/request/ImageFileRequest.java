package com.regulus.filedemo.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

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
public class ImageFileRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -5548049959272694576L;

    private List<Long> fids;

}
