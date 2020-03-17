package com.viagra.springMVC.beanAnno;

/**
 * @Auther: viagra
 * @Date: 2020/2/26 10:14
 * @Description:
 */
public class HelloWorld {

    private String message;

    public void getMessage() {
        System.out.println("Your message is:" + message);
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
