package com.example.harry.appmsg.DesignPattern;

/**
 * Created by Harry on 2017/10/10.
 */

/**
 * Builder => final屬性 物件不可變 , 定義完整連續狀態
 * */
public class MyBuilder {
    private final String Name;
    private final boolean Gender;
    private final int Ages;
    private final String PhoneNumber;
    /**
     * 以上 類別屬性 定義好之後 command+N  選擇 Builder 自動建立以下code ...
     * */
    private MyBuilder(Builder builder) {
        /*私有建構子 限制呼叫者不能直接 產生 此物件*/
        Name = builder.Name;
        Gender = builder.Gender;
        Ages = builder.Ages;
        PhoneNumber = builder.PhoneNumber;
    }

    public static final class Builder {
        private String Name;
        private boolean Gender;
        private int Ages;
        private String PhoneNumber;

        public Builder() {
        }

        public Builder Name(String val) {
            Name = val;
            return this;
        }

        public Builder Gender(boolean val) {
            Gender = val;
            return this;
        }

        public Builder Ages(int val) {
            Ages = val;
            return this;
        }

        public Builder PhoneNumber(String val) {
            PhoneNumber = val;
            return this;
        }

        public MyBuilder build() {
            return new MyBuilder(this);
        }
    }
}
