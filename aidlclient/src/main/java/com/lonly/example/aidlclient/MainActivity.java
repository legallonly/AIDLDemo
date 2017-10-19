package com.lonly.example.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lonly.example.aidlservicer.IRemoteService;
import com.lonly.example.aidlservicer.Person;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private IRemoteService mRemoteService;
    private TextView mTv_result;
    private TextView mAll_result;
    private EditText mEditText;
    private EditText mEditText1;
    private EditText mEditText2;
    private boolean isConnSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = (EditText) findViewById(R.id.editText);
        mEditText1 = (EditText) findViewById(R.id.editText1);
        mEditText2 = (EditText) findViewById(R.id.editText2);

        mTv_result = (TextView) findViewById(R.id.tv_result);
        mAll_result = (TextView) findViewById(R.id.all_result);

        // 连接绑定远程服务
        Intent intent = new Intent();
        intent.setAction("com.lonly.example.aidl");
        intent.setPackage("com.lonly.example.aidlservicer");
        isConnSuccess = bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    public void search(View view) {
        if (isConnSuccess) {
            // 连接成功
            String keyStr = mEditText.getText().toString();
            if (!TextUtils.isEmpty(keyStr)) {
                int id = Integer.valueOf(keyStr);
                try {
                    String name = mRemoteService.getName(id);
                    mTv_result.setText(name);
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            } else {
                Toast.makeText(this, "请输入查询", Toast.LENGTH_SHORT).show();
            }


        } else {
            System.out.println("连接失败!");
        }
    }

    public void searchPerson(View view) {
        if (isConnSuccess) {
            // 连接成功
            String keyStr = mEditText.getText().toString();
            if (!TextUtils.isEmpty(keyStr)) {
                int id = Integer.valueOf(keyStr);
                try {
                    Person person = mRemoteService.getPerson(id);
                    mTv_result.setText(new StringBuffer("姓名：").append(person.getName()).append("\t年龄：").append(String.valueOf(person.getAge())));
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            } else {
                Toast.makeText(this, "请输入查询", Toast.LENGTH_SHORT).show();
            }
        } else {
            System.out.println("连接失败!");
        }
    }
    public void add(View view) {
        if (isConnSuccess) {
            // 连接成功
            String nameStr = mEditText1.getText().toString();
            String ageStr = mEditText2.getText().toString();
            if (!TextUtils.isEmpty(nameStr) && !TextUtils.isEmpty(ageStr) ) {
                int age = Integer.valueOf(ageStr);
                try {
                    //判断是否已有此人
                    List<Person> list = mRemoteService.getPersons();
                    for (Person per:list) {
                        if(TextUtils.equals(nameStr,per.getName())){
                            Toast.makeText(this, "名册已有此人", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    //添加
                    Person person = new Person(nameStr,age);
                    mRemoteService.addPerson(person);

                    //重新获取
                    List<Person> persons = mRemoteService.getPersons();

                    StringBuffer  sb = new StringBuffer ("");
                    for (Person per:persons) {
                        sb.append("姓名：")
                                .append(per.getName())
                                .append("\t年龄：")
                                .append(per.getAge())
                                .append("\n");
                    }
                    mAll_result.setText(sb.toString());
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            } else {
                Toast.makeText(this, "请输入姓名和年龄", Toast.LENGTH_SHORT).show();
            }

        } else {
            System.out.println("连接失败!");
        }
    }
    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 这里的IBinder对象service是代理对象，所以必须调用下面的方法转换成AIDL接口对象
            mRemoteService = IRemoteService.Stub.asInterface(service);
            int pid = 0;
            try {
                pid = mRemoteService.getPid();
                int currentPid = android.os.Process.myPid();
                System.out.println("currentPID: " + currentPid + "  remotePID: " + pid);
                mRemoteService.basicTypes(12, 1223, true, 12.2f, 12.3, "有梦就要去追，加油！");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            System.out.println("bind success! " + mRemoteService.toString());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mRemoteService = null;
            System.out.println(mRemoteService.toString() + " disconnected! ");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
