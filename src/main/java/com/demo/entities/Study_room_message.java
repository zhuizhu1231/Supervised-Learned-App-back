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
@TableName("study_room_message")
public class Study_room_message {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer studyRoomId;
    private Integer sendUserId;
    @JsonDeserialize(using = SpringDataConverter.class)
    private Timestamp sendTime;
    private String content;
    @JsonDeserialize(using = SpringDataConverter.class)
    private Timestamp timeStamp;
}
