package org.mumu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {

    private int code; //响应状态码
    private String message; //后台向前台响应业务操作的提示信息
    private T data; //后台向前台响应的业务数据
}
