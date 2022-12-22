package com.potato.kun.learn.desgin.mode.creator.single;

/**
 * @author DivingPotato
 * @description 饿汉单例模式
 */
public class HungrySingleInstanceMode {

    private static final Object hungrySingleObj = new Object();

    //构造方法私有，防止在外部被实例化
    private HungrySingleInstanceMode(){}

    public static Object getInstance()
    {
        return hungrySingleObj;
    }
}
