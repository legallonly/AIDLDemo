AIDL（Android Interface Definition Language），是Android接口定义语言，这种语言定义了一个客户端和服务器通讯接口的一个标准、规范。AIDL作用是让两个不同的应用间通过Service进行通信（进程通讯IPC），并且远程的Service可以处理多线程。简单来讲就是，两个应用，一个应用对外提供一个远程Service，其他的应用可以并发地访问这个Service，即：C/S模式。
Google官方AIDL的说明：

`Using AIDL is necessary only if you allow clients from different applications to access your service for IPC and want to handle multithreading in your service. If you do not need to perform concurrent IPC across different applications, you should create your interface by implementing a Binder or, if you want to perform IPC, but do not need to handle multithreading, implement your interface using a Messenger. Regardless, be sure that you understand Bound Services before implementing an AIDL.`

本项目就是AIDL跨进程通讯的简单示例。

详细解析请参考的的博客文章：http://blog.csdn.net/u012956630/article/details/78289381