package com.potato.kun.learn.stream;

import com.potato.kun.learn.stream.dto.UserGradeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Stream;

/**
 * @author DivingPotato
 * @description
 */
public class IntermediateOperationTest {

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
    public void filterTest(){
        //测试过滤只打印三个主科的成绩。
        List<String> mainSubjects = Arrays.asList("语文", "数学", "英文");
        gradeList.stream().filter(w->mainSubjects.contains(w.getSubjects()))
                .forEach(w-> System.out.println("科目："+w.getSubjects()+"，成绩为："+w.getGrade()));
    }

    @Test
    public void pageTest(){
        //内存分页实现,每页3个，查看第三页的科目成绩
        int page = 3;
        int size = 3;
        gradeList.stream().skip((page-1)*size).limit(size)
                .forEach(w-> System.out.println("科目："+w.getSubjects()+"，成绩为："+w.getGrade()));
        UserGradeDTO userGradeDTO = new UserGradeDTO();
    }

    @Test
    public void distinctTest(){
        UserGradeDTO userGrade10 = new UserGradeDTO()
                .setUserName("张三")
                .setSex("男")
                .setSubjects("地理")
                .setGrade(80);
        gradeList.add(userGrade10);
        //实现成绩去重，用户名+科目一致则代表为相同数据
        gradeList.stream().distinct()
                .forEach(w-> System.out.println("科目："+w.getSubjects()+"，成绩为："+w.getGrade()));
    }

    @Test
    public void mapTest(){
        //map中函数式方法返回值为对象，也就是原始流中有n个元素，处理之后的流中元素个数也是n个。即使你在返回的时候返回了多个元素如数组，他也会把数组当成一个对象处理。
        gradeList.stream().map(w->{
            if (w.getGrade() >= 85){
                return Arrays.asList(w,w);
            }
            return w;
        }).forEach(w-> System.out.println("科目："+w));
    }

    @Test
    public void flatMapTest(){
        //flatMap中函数式方法返回值为流，flat为扁平的意思，也就是说，我们可以通过flatMap改变原始流中对象的个数，flatMap会将返回的流合并成一个流，而不是当成对象处理。
        gradeList.stream().flatMap(w->{
            if (w.getGrade() >= 85){
                return Stream.of(w,w);
            }
            return Stream.of(w);
        }).forEach(w-> System.out.println("科目："+w));
    }

    @Test
    public void sortTest(){
        UserGradeDTO userGrade10 = new UserGradeDTO()
                .setUserName("张三")
                .setSex("男")
                .setSubjects("天文")
                .setGrade(78);
        gradeList.add(userGrade10);
        //先按成绩降序，成绩相同再按科目升序
        gradeList.stream().sorted((o1, o2) -> {
            if (Objects.equals(o1.getGrade(), o2.getGrade())){
                return o1.getUserName().compareTo(o2.getUserName());
            }
            return o2.getGrade() - o1.getGrade();
        }).forEach(w-> System.out.println("科目："+w.getSubjects()+"，成绩为："+w.getGrade()));
    }

    @Test
    public void peekTest(){
        List<Integer> integers = Arrays.asList(1,2,3,4);
        integers.stream().peek(w->w+=1)
                .forEach(w-> System.out.println("，成绩为："+w));
        System.out.println("再次输出");
        integers.stream().forEach(w-> System.out.println("，成绩为："+w));
    }



}
