# 一 SQOOP2的实验

##数据库基本操作命令

###1 选择数据库命令

* Mysql:

现在我用一个例子讲解sqoop2的具体使用方法,数据准备,有一个mysql的表叫worker，里面有三条数据，我们要将其导入hadoop,这是建表语句

```
登录方式:
#直接本地登录 root:123456
#mysql -u root -p 

mysql> show databases;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| hive               |
| mysql              |
| performance_schema |
+--------------------+
4 rows in set (0.02 sec)

mysql> create database chu888chu888;
Query OK, 1 row affected (0.00 sec)

mysql> use chu888chu888;
Database changed
mysql> CREATE TABLE `workers` (
    ->   `id` int(11) NOT NULL AUTO_INCREMENT,
    ->   `name` varchar(20) NOT NULL,
    ->   PRIMARY KEY (`id`)
    -> ) ENGINE=MyISAM  DEFAULT CHARSET=utf8;
Query OK, 0 rows affected (0.00 sec)

mysql> insert into workers (name) values ('jack');
Query OK, 1 row affected (0.00 sec)

mysql> insert into workers (name) values ('vicky');
Query OK, 1 row affected (0.00 sec)

mysql> insert into workers (name) values ('martin');
Query OK, 1 row affected (0.00 sec)

```

### 2. 导入数据

```
$sqoop2-shell
sqoop:000> show connector
16/08/09 13:35:05 WARN util.NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable
+------------------------+---------+------------------------------------------------------------+----------------------+
|          Name          | Version |                           Class                            | Supported Directions |
+------------------------+---------+------------------------------------------------------------+----------------------+
| oracle-jdbc-connector  | 1.99.7  | org.apache.sqoop.connector.jdbc.oracle.OracleJdbcConnector | FROM/TO              |
| sftp-connector         | 1.99.7  | org.apache.sqoop.connector.sftp.SftpConnector              | TO                   |
| kafka-connector        | 1.99.7  | org.apache.sqoop.connector.kafka.KafkaConnector            | TO                   |
| kite-connector         | 1.99.7  | org.apache.sqoop.connector.kite.KiteConnector              | FROM/TO              |
| ftp-connector          | 1.99.7  | org.apache.sqoop.connector.ftp.FtpConnector                | TO                   |
| hdfs-connector         | 1.99.7  | org.apache.sqoop.connector.hdfs.HdfsConnector              | FROM/TO              |
| generic-jdbc-connector | 1.99.7  | org.apache.sqoop.connector.jdbc.GenericJdbcConnector       | FROM/TO              |
+------------------------+---------+------------------------------------------------------------+----------------------+



```

