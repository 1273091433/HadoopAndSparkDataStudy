# Sqoop2的安装


## 1. 解压并安装

```
hadoop@Master:~$ sudo tar xvfz sqoop-1.99.6-bin-hadoop200.tar.gz 
hadoop@Master:~$ sudo mv sqoop-1.99.6-bin-hadoop200 /usr/local/sqoop
hadoop@Master:~$ sudo chmod -R 775 /usr/local/sqoop
hadoop@Master:~$ sudo chown -R hadoop:hadoop /usr/local/sqoop

```

## 2. 修改环境变量
```
hadoop@Master:~$ sudo nano /etc/profile

#sqoop
export SQOOP_HOME=/usr/local/sqoop
export PATH=$SQOOP_HOME/bin:$PATH
export CATALINA_BASE=$SQOOP_HOME/server
export LOGDIR=$SQOOP_HOME/logs

hadoop@Master:~$ source /etc/profile

```

## 3. 修改sqoop的环境变量

```
hadoop@Master:/$ sudo nano /usr/local/sqoop/conf/sqoop.properties 

#修改指向我的hadoop安装目录  
org.apache.sqoop.submission.engine.mapreduce.configuration.directory=/usr/local/hadoop/etc/hadoop  


#catalina.properties 此文件不存在,需要自已建立
hadoop@Master:/$ sudo nano /usr/local/sqoop/conf/catalina.properties 

common.loader=/usr/local/hadoop/share/hadoop/common/*.jar,/usr/local/hadoop/share/hadoop/common/lib/*.jar,/usr/local/hadoop/share/hadoop/hdfs/*.jar,/usr/local/hadoop/share/hadoop/hdfs/lib/*.jar,/usr/local/hadoop/share/hadoop/mapreduce/*.jar,/usr/local/hadoop/share/hadoop/mapreduce/lib/*.jar,/usr/local/hadoop/share/hadoop/tools/*.jar,/usr/local/hadoop/share/hadoop/tools/lib/*.jar,/usr/local/hadoop/share/hadoop/yarn/*.jar,/usr/local/hadoop/share/hadoop/yarn/lib/*.jar,/usr/local/hadoop/share/hadoop/httpfs/tomcat/lib/*.jar,
```

下载mysql驱动包，mysql-connector-java-5.1.27.jar

把jar包丢到到$SQOOP_HOME/server/lib下面

```
sudo cp mysql-connector-java-5.1.27.jar $SQOOP_HOME/server/lib
```

有时，启动sqoop时可能会遇到找不到JAVA_HOME的情况，为了保险起见我们直接在配置文件中写入JAVA_HOME

**在/usr/local/sqoop/bin/sqoop.sh中，添加**

```
  export JAVA_HOME=/usr/lib/jvm/
  HADOOP_COMMON_HOME=/usr/local/hadoop/share/hadoop/common
  HADOOP_HDFS_HOME=/usr/local/hadoop/share/hadoop/hdfs
  HADOOP_MAPRED_HOME=/usr/local/hadoop/share/hadoop/mapreduce
  HADOOP_YARN_HOME=/usr/local/hadoop/share/hadoop/yarn
```


## 4. 启动sqoop
```
hadoop@Master:~/mysql-connector-java-5.0.8$ sqoop.sh server start
Sqoop home directory: /usr/local/sqoop
Setting SQOOP_HTTP_PORT:     12000
Setting SQOOP_ADMIN_PORT:     12001
Using   CATALINA_OPTS:       
Adding to CATALINA_OPTS:    -Dsqoop.http.port=12000 -Dsqoop.admin.port=12001
Using CATALINA_BASE:   /usr/local/sqoop/server
Using CATALINA_HOME:   /usr/local/sqoop/server
Using CATALINA_TMPDIR: /usr/local/sqoop/server/temp
Using JRE_HOME:        /usr/lib/jvm//jre
Using CLASSPATH:       /usr/local/sqoop/server/bin/bootstrap.jar

```

##5. 验证启动成功

```
./sqoop.sh server start    启动 
./sqoop.sh server stop     停止
./sqoop.sh client          进入客户端
set server --host hadoopMaster --port 12000 --webapp sqoop 设置服务器，注意hadoopMaster为hdfs主机名
show connector --all    查看连接类型
create link --cid 1    创建连接，cid为连接类型id
show link 查看连接
update link -l 1 修改id为1的连接
delete link -l 1 删除id为1的连接
create job -f 1 -t 2 创建从连接1到连接2的job
show job 查看job
update job -jid 1    修改job
delete job -jid 1    删除job
status job -jid 1    看看job状态
stop job -jid    1   停止job

```

## 6.Hadoop的配置修改
需要在Hadoop的yarn-site.xml 这个配置文件中增加以下属性
```
<property>  
  <name>yarn.log-aggregation-enable</name>  
  <value>true</value>  
</property> 
``` 

## 7. 其他参考文档

[一篇写的比我好的文档,我就不搬砖了,大家自已看:](http://blog.csdn.net/u014729236/article/details/46876651)
