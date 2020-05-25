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
@TableName("schedule")
public class Schedule {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Timestamp lastModifyTime;
    private String title;
    private Integer isDone;
    @JsonDeserialize(using = SpringDataConverter.class)
    private Timestamp belongTime;
    private Integer category;//import and
    private String remark;
    private Integer property;//assign or
    @JsonDeserialize(using = SpringDataConverter.class)
    private Timestamp timeStamp;

}
