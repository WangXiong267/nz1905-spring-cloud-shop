package com.qf.entityvo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Author Wang
 * @Date 2020/3/10
 */
@Data
@Getter
@Setter
public class TproductVO{
    //以下是产品信息
    private Long pid;

    private String pname;

    private Long price;

    private Long salePrice;

    private Long typeId;

    private Byte status;

    private String pimage;

    private Byte flag;

    private Date createTime;

    private Date updateTime;

    private Long createUser;

    private Long updateUser;

    //以下是产品描述信息
    private Long id;

    private String pdesc;

}
