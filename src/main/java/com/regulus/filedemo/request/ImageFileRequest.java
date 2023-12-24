package com.regulus.filedemo.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class ImageFileRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -5548049959272694576L;

    private List<Long> fids;

}
