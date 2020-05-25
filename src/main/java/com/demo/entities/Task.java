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
@TableName("task")
public class Task {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer labelId;
    private Integer aimId;
    private String title;
    private String remark;
    @JsonDeserialize(using = SpringDataConverter.class)
    private Timestamp workTime;
    private Integer workCount;
    @JsonDeserialize(using = SpringDataConverter.class)
    private Timestamp timeStamp;
}
