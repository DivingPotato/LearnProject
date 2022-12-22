package com.potato.kun.learn.desgin.mode.creator.single;

/**
 * @author DivingPotato
 * @description 懒汉单例模式
 */
public class LazySingleInstanceMode {

    //保证 instance 在所有线程中同步
    private static volatile Object lazySingleObj;

    //构造方法私有，防止在外部被实例化
    private LazySingleInstanceMode(){}

    //getInstance 方法前加同步,防止并发请求
    public static synchronized Object getInstance()
    {
        if(lazySingleObj == null)
        {
            lazySingleObj = new Object();
        }
        return lazySingleObj;
    }
}
