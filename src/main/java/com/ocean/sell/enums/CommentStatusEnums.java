package com.ocean.sell.enums;

import lombok.Getter;

@Getter
public enum CommentStatusEnums {
    NOTCOMMENT(0,"未评论"),
    COMMENTED(1,"已评论"),
    ;

    private Integer code;
    private String message;

    CommentStatusEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
