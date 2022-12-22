package com.potato.kun.learn.stream;

import com.potato.kun.learn.stream.dto.UserGradeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 * @author DivingPotato
 * @description
 */
public class AbortOperationTest{

    private List<UserGradeDTO> gradeList = new ArrayList<>();

    @BeforeEach
    public void before(){
        UserGradeDTO userGrade1 = new UserGradeDTO()
                .setUserName("张三")
                .setSex("男")
                .setSubjects("数学")
                .setGrade(88);
        gradeList.add(userGrade1);
        UserGradeDTO userGrade2 = new UserGradeDTO()
                .setUserName("张三")
                .setSex("男")
                .setSubjects("语文")
                .setGrade(72);
        gradeList.add(userGrade2);
        UserGradeDTO userGrade3 = new UserGradeDTO()
                .setUserName("张三")
                .setSex("男")
                .setSubjects("英文")
                .setGrade(58);
        gradeList.add(userGrade3);
        UserGradeDTO userGrade4 = new UserGradeDTO()
                .setUserName("张三")
                .setSex("男")
                .setSubjects("生物")
                .setGrade(94);
        gradeList.add(userGrade4);
        UserGradeDTO userGrade5 = new UserGradeDTO()
                .setUserName("张三")
                .setSex("男")
                .setSubjects("化学")
                .setGrade(82);
        gradeList.add(userGrade5);
        UserGradeDTO userGrade6 = new UserGradeDTO()
                .setUserName("张三")
                .setSex("男")
                .setSubjects("物理")
                .setGrade(86);
        gradeList.add(userGrade6);
        UserGradeDTO userGrade7 = new UserGradeDTO()
                .setUserName("张三")
                .setSex("男")
                .setSubjects("历史")
                .setGrade(89);
        gradeList.add(userGrade7);
        UserGradeDTO userGrade8 = new UserGradeDTO()
                .setUserName("张三")
                .setSex("男")
                .setSubjects("政治")
                .setGrade(73);
        gradeList.add(userGrade8);
        UserGradeDTO userGrade9 = new UserGradeDTO()
                .setUserName("张三")
                .setSex("男")
                .setSubjects("地理")
                .setGrade(78);
        gradeList.add(userGrade9);
    }

    @Test
    public void anyMatchGradeGt90(){
        System.out.println(gradeList.stream().anyMatch(w -> w.getGrade() > 90));
    }

    @Test
    public void getMaxGradeRecord(){
        System.out.println(gradeList.stream().max((Comparator.comparingInt(UserGradeDTO::getGrade))));
    }

    @Test
    public void reduceSumGrade(){
        //规约操作,计算总分
        System.out.println(gradeList.stream()
                .map(UserGradeDTO::getGrade)
                .reduce(Integer::sum)
                .get());
        System.out.println(gradeList.stream().collect(Collectors.summingInt(UserGradeDTO::getGrade)));
        System.out.println((Integer) gradeList.stream().mapToInt(UserGradeDTO::getGrade).sum());
    }

    @Test
    public void getGradeGt80Records(){
        System.out.println(gradeList.stream().filter(w -> w.getGrade() > 80)
                .collect(Collectors.toList()));
    }

    @Test
    public void getNameMapRecords(){
        //注:key不能相同，否则报错
        Map<String, UserGradeDTO> map = gradeList.stream().collect(Collectors.toMap(w->w.getUserName()+"_"+w.getSubjects(), w -> w));
        System.out.println(map);
    }

    @Test
    public void getSubjectsStr(){
        String collect = gradeList.stream().map(UserGradeDTO::getSubjects).collect(Collectors.joining("','", "('", "')"));
        System.out.println(collect);
    }

    @Test
    public void getGradeGt85Count(){
        System.out.println((Long) gradeList.stream().filter(w -> w.getGrade() > 85)
                .count());
    }

    @Test
    public void groupByNameAndSubject(){
        //先按人进行分组，再按照科目进行分组
        Map<String, Map<String, List<UserGradeDTO>>> collect = gradeList.stream().collect(Collectors.groupingBy(UserGradeDTO::getUserName, Collectors.groupingBy(UserGradeDTO::getSubjects)));
        System.out.println(collect);
    }

    @Test
    public void partitioningBy(){
        //分区，超过85岁的为true,小于等于85岁的为false
        Map<Boolean, List<UserGradeDTO>> collect = gradeList.stream().collect(Collectors.partitioningBy(w -> w.getGrade() > 85));
        System.out.println(collect);
    }

}
