package com.weiwen.atomicreference;

/**
 * @author weiwen
 * @email yusiyiqingyan@163.com
 * Created by weiwen on 2020/7/3
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 练习原子引用类的简单使用 AtomicReference
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
class User{
    private String name;
    private Integer age;
}

public class AtomicReferenceDemo {
    public static void main(String[] args) {
        User weiwen = new User("weiwen", 20);
        User wuzhu = new User("wuzhu", 22);
        AtomicReference<User> userAtomicReference = new AtomicReference<>();
        userAtomicReference.set(weiwen);

        System.out.println(userAtomicReference.compareAndSet(weiwen, wuzhu) + "\t" + userAtomicReference.get().toString());
        System.out.println(userAtomicReference.compareAndSet(weiwen, wuzhu) + "\t" + userAtomicReference.get().toString());


    }





}