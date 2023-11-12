package org.mumu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data // 省略写get set方法
@NoArgsConstructor //提供无参数的构造函数
@AllArgsConstructor //提供带所有参数的构造函数
public class Payment implements Serializable {

    private long id;
    private String serial;
}
