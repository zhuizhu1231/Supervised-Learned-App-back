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

import java.sql.Timestamp;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("study_room")
public class Study_room {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userCreateId;
    private String name;
    private String detail;
    private Integer userCount;
    @JsonDeserialize(using = SpringDataConverter.class)
    private Timestamp createTime;
    @JsonDeserialize(using = SpringDataConverter.class)
    private Timestamp leastWorkTime;
    @JsonDeserialize(using = SpringDataConverter.class)
    private Timestamp timeStamp;
}
