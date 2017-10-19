package com.lonly.example.aidlservicer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lonly on 2017/10/19.
 */

public class RemoteService extends Service{

    private List<Person> persons = new ArrayList<>();
    /**
     * 返回一个RemoteService代理对象IBinder给客户端使用
     * @param intent
     * @return
     */

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mRemoteService;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化persons
        persons.add(new Person("吕布",20));
        persons.add(new Person("关羽",23));
        persons.add(new Person("张飞",21));
        persons.add(new Person("赵子龙",19));
    }

    /**
     * 实现接口
     */
    private IRemoteService.Stub mRemoteService = new IRemoteService.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            System.out.println("Thread: " + Thread.currentThread().getName());
            System.out.println("basicTypes aDouble: " + aDouble +" anInt: " + anInt+" aBoolean " + aBoolean+" aString " + aString);
        }

        @Override
        public int getPid() throws RemoteException {
            System.out.println("Thread: " + Thread.currentThread().getName());
            System.out.println("RemoteService getPid ");
            return android.os.Process.myPid();
        }

        @Override
        public String getName(int id) throws RemoteException {
            return persons.get(id).getName();
        }

        @Override
        public Person getPerson(int id) throws RemoteException {
            return persons.get(id);
        }

        @Override
        public void addPerson(Person person) throws RemoteException {
            persons.add(person);
        }

        @Override
        public List<Person> getPersons() throws RemoteException {
            return persons;
        }

    };
}
