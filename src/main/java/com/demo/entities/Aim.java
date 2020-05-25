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
@TableName("aim")//table实际名
public class Aim {
    @TableId(type = IdType.AUTO)//Id自动增长
    private Integer id;
    private Integer userId;
    private Integer labelId;
    private String title;
    private String remark;
    @JsonDeserialize(using = SpringDataConverter.class)
    private Timestamp createTime;
    @JsonDeserialize(using = SpringDataConverter.class)
    private Timestamp restTime;
    @JsonDeserialize(using = SpringDataConverter.class)
    private Timestamp timeStamp;
}
