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
@TableName("message")
public class Message {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer sendId;
    private Integer receiverId;
    @JsonDeserialize(using = SpringDataConverter.class)
    private Timestamp sendTime;
    private Integer isPrompt;
    private Integer isRead;
    private String content;
    @JsonDeserialize(using = SpringDataConverter.class)
    private Timestamp timeStamp;

}
