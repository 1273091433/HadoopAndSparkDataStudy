# 第十七章 HUE安装与配置

##Hue安装

####环境说明

操作系统：Ubuntu 14.04

集群节点：

* Master
* slave1
* slave2

hadoop用户为：root

这里我们将hue安装在Slave2节点上


####安装编译hue需要的相关依赖

```
sudo apt-get install ant gcc g++ libkrb5-dev libffi-dev libmysqlclient-dev libssl-dev libsasl2-dev libsasl2-modules-gssapi-mit libsqlite3-dev libtidy-0.99-0 libxml2-dev libxslt-dev make libldap2-dev maven python-dev python-setuptools libgmp3-dev

```

####下载解压并移动
到官网[下载](：http://gethue.com/hue-3-10-with-its-new-sql-editor-is-out/)对应tar包

```
root@slave2:~$ sudo tar zxvf hue-3.10.0.tgz
root@slave2:~$ sudo cp -R hue-3.10.0 /usr/local/hue

```

####编译
```
root@slave2:~$ cd /usr/local/hue
root@slave2:/usr/local/hue# sudo make apps

```

####添加hue用户并赋权

```
root@slave2:/usr/local/hue# sudo adduser hue
root@slave2:sudo chmod -R 775 /usr/local/hue
root@slave2:sudo chown -R hue:hue /usr/local/hue

```
####启动hue

```
root@slave2:/usr/local/hue# ./build/env/bin/supervisor

```
打开slave2:8888查看到hue界面，代表hue安装成功。

下一步就是配置hue，使它能够管理hdfs、hive、hbase，并能使用Oozie、Pig等，将在下面的文章中给大家介绍。

##Hue配置
####配置集群的访问权限
由于hue的启动用户是hue，所以需要为hue添加集群的访问权限，在各节点的/usr/local/hadoop/etc/hadoop/core-site.xml，添加如下参数：

```
 <property>
      <name>hadoop.proxyuser.hue.hosts</name>
      <value>*</value>
 </property>
 <property>
      <name>hadoop.proxyuser.hue.groups</name>
      <value>*</value>
 </property>
 
 ```
 
 配置完，记得重启hadoop集群
 
####配置hdfs
配置/usr/local/hue/desktop/conf/hue.ini

1）配置hdfs的超级用户

```
  # This should be the hadoop cluster admin
  default_hdfs_superuser=root

```

2）hdfs相关配置

这里主要配置三项：fs_defaultfs、webhdfs_url、hadoop_conf_dir；

其中，webhdfs_url默认本身就是开启的，不需要在hadoop中特别开启。

```
  [[hdfs_clusters]]
    # HA support by using HttpFs
    [[[default]]]
    
      # Enter the filesystem uri
      fs_defaultfs=hdfs://Master:8020

      # NameNode logical name.
      ## logical_name=

      # Use WebHdfs/HttpFs as the communication mechanism.
      # Domain should be the NameNode or HttpFs host.
      # Default port is 14000 for HttpFs.
      webhdfs_url=http://Master:50070/webhdfs/v1

      # Change this if your HDFS cluster is Kerberos-secured
      ## security_enabled=false

      # In secure mode (HTTPS), if SSL certificates from YARN Rest APIs
      # have to be verified against certificate authority
      ## ssl_cert_ca_verify=True

      # Directory of the Hadoop configuration
      hadoop_conf_dir=/usr/local/hadoop/etc/hadoop


```

####配置yarn 
配置/usr/local/hue/desktop/conf/hue.ini；

主要配置四个地方：resourcemanager_host、resourcemanager_api_url、proxy_api_url、history_server_api_url。

```
[[yarn_clusters]]

    [[[default]]]
      # Enter the host on which you are running the ResourceManager
      resourcemanager_host=Master

      # The port where the ResourceManager IPC listens on
      ## resourcemanager_port=8032

      # Whether to submit jobs to this cluster
      submit_to=True

      # Resource Manager logical name (required for HA)
      ## logical_name=

      # Change this if your YARN cluster is Kerberos-secured
      ## security_enabled=false

      # URL of the ResourceManager API
      resourcemanager_api_url=http://Master:8088

      # URL of the ProxyServer API
      proxy_api_url=http://Master:8088

      # URL of the HistoryServer API
      history_server_api_url=http://Master:19888

      # URL of the Spark History Server
      ## spark_history_server_url=http://localhost:18088

      # In secure mode (HTTPS), if SSL certificates from YARN Rest APIs
      # have to be verified against certificate authority
      ## ssl_cert_ca_verify=True

```

####配置hive
1）首先配置hue.ini

主要配置两个地方：hive_server_host、hive_conf_dir。

```
[beeswax]

  # Host where HiveServer2 is running.
  # If Kerberos security is enabled, use fully-qualified domain name (FQDN).
  hive_server_host=Master

  # Port where HiveServer2 Thrift server runs on.
  ## hive_server_port=10000

  # Hive configuration directory, where hive-site.xml is located
  hive_conf_dir=/usr/local/hive/conf

  # Timeout in seconds for thrift calls to Hive service
  ## server_conn_timeout=120


```
2）启动hive2

```
root@Master:/usr/local/hive/bin# hive --service hiveserver2 &

```

####配置hbase
1）首先配置hue.ini

主要配置两个地方：hbase_clusters、hbase_conf_dir。

```
[hbase]
  # Comma-separated list of HBase Thrift servers for clusters in the format of '(name|host:port)'.
  # Use full hostname with security.
  # If using Kerberos we assume GSSAPI SASL, not PLAIN.
  hbase_clusters=(Cluster|Master:9090)

  # HBase configuration directory, where hbase-site.xml is located.
  hbase_conf_dir=/usr/local/hbase/conf

  # Hard limit of rows or columns per row fetched before truncating.
  ## truncate_limit = 500

  # 'buffered' is the default of the HBase Thrift Server and supports security.
  # 'framed' can be used to chunk up responses,
  # which is useful when used in conjunction with the nonblocking server in Thrift.
  ## thrift_transport=buffered

```
2）启动thrift

```
root@Master:/usr/local/hbase/bin# hbase-daemon.sh start thrift

```

特别注意：这里的thrift必须是1，而不是thrift2

####启动hue

```
root@slave2:/usr/local/hue# ./build/env/bin/supervisor

```
打开slave2:8888/about/查看到hue界面，如果页面中没有报hdfs、yarn、hbase、hive相关的警告则代表配置成功，之后就能在hue中使用相关的功能。

但是，我们可能会看到如下警告：

```
SQLITE_NOT_FOR_PRODUCTION_USE	SQLite is only recommended for small development environments with a few users.
Impala	                        No available Impalad to send queries to.
Oozie Editor/Dashboard	        The app won't work without a running Oozie server
Pig Editor	                    The app won't work without a running Oozie server
Spark	                        The app won't work without a running Livy Spark Server

```

那是由于我们没有安装和配置相应功能，该块内容，将在后续文章中补充。








