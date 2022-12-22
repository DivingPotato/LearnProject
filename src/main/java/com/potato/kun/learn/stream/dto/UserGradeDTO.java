package com.potato.kun.learn.stream.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * @author DivingPotato
 * @description 用户成绩DTO
 */
@Data
@Accessors(chain = true)
public class UserGradeDTO {

    private String userName;
    /**
     * @description 男/女
    **/
    private String sex;
    /**
     * @description 语文，数学，英文，化学，生物，物理，历史，政治，地理
     **/
    private String subjects;
    /**
     * @description 成绩
     **/
    private Integer grade;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserGradeDTO that = (UserGradeDTO) o;
        return Objects.equals(userName, that.userName) && Objects.equals(subjects, that.subjects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, subjects);
    }
}
