package com.example;

import java.io.IOException;

/**
 * @Auther: viagra
 * @Date: 2020/3/4 16:54
 * @Description:
 */
public class hibernateValidatorTestMain {

    public static void main(String[] args) {
        try {
            org.hibernate.validator.internal.util.Version.touch();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            org.hibernate.validator.internal.util.Version.touch();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
