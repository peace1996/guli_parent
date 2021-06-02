package com.atguigu.servicebase.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor  //生成有参数的构造方法
@NoArgsConstructor   //生成无参数的构造方法
public class PeaceException extends RuntimeException{
    private Integer code;
    private String msg;

    @Override
    public String toString() {
        return "PeaceException{" +
        "message=" + this.getMsg() +
        ", code=" + code +
        '}';
    }
}


