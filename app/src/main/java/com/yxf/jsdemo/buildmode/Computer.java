package com.yxf.jsdemo.buildmode;

import com.orhanobut.logger.Logger;

/**
 * @Description:
 * @Author: yxf
 * @CreateDate: 2021/6/4 15:13
 * @UpdateUser: yxf
 * @UpdateDate: 2021/6/4 15:13
 */
public class Computer {

    private int intProperty;
    private String strProperty;

    public Computer(Builder builder) {
        this.intProperty=builder.intProperty;
        this.strProperty=builder.strProperty;
    }

    public int getIntProperty() {
        return intProperty;
    }

    public String getStrProperty() {
        return strProperty;
    }

    public static class Builder {
        private int intProperty;
        private String strProperty;

        public Builder setIntProperty(int intProperty) {
            this.intProperty = intProperty;
            return this;
        }

        public Builder setStrProperty(String strProperty) {
            this.strProperty = strProperty;
            return this;
        }

        public Computer build() {
            return new Computer(this);
        }

    }
}
