package com.demo.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.demo.converter.SpringDataConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Timestamp;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("user")//table实际名
public class User implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String password;
    private String sign;
    @JsonDeserialize(using = SpringDataConverter.class)
    private Timestamp registerTime;
    @JsonDeserialize(using = SpringDataConverter.class)
    private Timestamp lastLoginTime;
    private Integer sex;
    private Integer isHide;
    @JsonDeserialize(using = SpringDataConverter.class)
    private Timestamp restTime;
    @JsonDeserialize(using = SpringDataConverter.class)
    private Timestamp workTime;
    @JsonDeserialize(using = SpringDataConverter.class)
    private Timestamp timeStamp;


}

