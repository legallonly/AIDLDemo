// IRemoteService.aidl
package com.lonly.example.aidlservicer;
//用到的自定义类型数据类一定要手动导入
import com.lonly.example.aidlservicer.Person;

// Declare any non-default types here with import statements

interface IRemoteService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
     /**
      * Request the process ID of this service, to do evil things with it
      */
     int getPid();
     /**
      * get name by id
      */
     String getName(int id);
     /**
     * get Person
     */
     Person getPerson(int id);

     /**
     * 添加人员 in:定向tag。
     * 其中，
      * in 表示数据只能由客户端流向服务端，
     * out 表示数据只能由服务端流向客户端，
     * inout 则表示数据可在服务端与客户端之间双向流通。
     */
     void addPerson(in Person person);

     List<Person> getPersons();
}
