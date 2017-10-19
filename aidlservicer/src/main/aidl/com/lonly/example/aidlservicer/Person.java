package com.lonly.example.aidlservicer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lonly on 2017/10/19.
 */

public class Person implements Parcelable{
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    protected Person(Parcel in) {
        name = in.readString();
        age = in.readInt();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    /**
     * 在想要进行序列号传递的实体类内部一定要声明该常量。常量名只能是CREATOR,类型也必须是
     * Parcelable.Creator<T>  T:就是当前对象类型
     */
    public static final Creator<Person> CREATOR = new Creator<Person>() {
        /***
         * 根据序列化的Parcel对象，反序列化为原本的实体对象
         * 读出顺序要和writeToParcel的写入顺序相同
         */
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in.readString(),in.readInt());
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[0];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }
    /**
     * 将对象写入到Parcel（序列化）
     * @param dest：就是对象即将写入的目的对象
     * @param flags: 有关对象序列号的方式的标识
     * 这里要注意，写入的顺序要和在createFromParcel方法中读出的顺序完全相同。例如这里先写入的为name，
     * 那么在createFromParcel就要先读name
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }
    /**
     * 参数是一个Parcel,用它来存储与传输数据
     * @param dest
     */
    private void readFromParcel(Parcel dest) {
        //注意，此处的读值顺序应当是和writeToParcel()方法中一致的
        this.name = dest.readString();
        this.age = dest.readInt();
    }
}
