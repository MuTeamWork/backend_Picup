package com.regulus.filedemo.request;

import lombok.Data;

import java.io.Serial;
import java.util.List;

@Data
public class TagRequest {
    @Serial
    private static final long serialVersionUID = -5049756437671541193L;

    private Long tid;
    private List<String> tags;
}
