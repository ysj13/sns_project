package com.cos.photogramstart.web.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomResponseDto<T> {

    private int code;       // 1(성공), -1(실패)
    private String message;
    private T data;
}
