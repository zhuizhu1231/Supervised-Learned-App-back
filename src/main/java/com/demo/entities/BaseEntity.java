package com.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Component
@ConfigurationProperties(prefix = "school.student")
//多值匹配  在yml中
public class BaseEntity {
    public String name;

    public Integer age;
    public List likes;
    public Map address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", likes=" + likes +
                ", address=" + address +
                '}';
    }

    public List getLikes() {
        return likes;
    }

    public void setLikes(List likes) {
        this.likes = likes;
    }

    public Map getAddress() {
        return address;
    }

    public void setAddress(Map address) {
        this.address = address;
    }
}
