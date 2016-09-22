# 一 整合SQL引擎层

1. NOSQL(Not only  SQL 非关系型数据库)的特性之一是不使用SQL作为查询语言,本节简单介绍NOSQL定义,为何NOSQL 上定义SQL引擎,以及现有基于HBASE的SQL引擎的具体实现
2. NOSQL是不同于传统关系型数据库的数据库系统的统称.两者有很多显著的不同点,其中最重要的是NOSQL不使用SQL作为查询语言.其数据存储可以不需要固定的表格模式,也经常会避免使用SQL的JOIN操作,一般具备水平扩展的特征.NOSQL的实现具有两个特征:使用硬盘或者把随机存储器作为存储媒体

## 1 把SQL整合到HBASE的原因
现有的SQL解决方案通常都不是水平可伸缩的,因此当数据量变大时会遇到阻力.我们已经知道NOSQL区别于关系型数据库的一点就是NOSQL不使用SQL作为查询语言,至于为何在NOSQL数据存储HBASE上提供SQL接口,有如下三个原因:

1. 使用诸如SQL这样易于理解的语言,使人们能够更加轻松地使用HBASE
2. 使用诸如SQL这样更高层次的语言来编写,减少了编码的代码量.
3. 执行查询时,在数据访问与运行时执行之间加上SQL这样一层抽象可以进行大量优化.

## 2 基于HBASE的SQL引擎实现

现阶段业内有一些HBASE SQL引擎层的尝试

### Hive 整合HBASE
Hive与HBASE的整合功能从Hive0.6版本开始出现,利用两者对外的API接口互相通信,通信主要依赖hive_hbase-handler.jar工具包(Hive Storage Handlers).由于HBASE有一次比较大的版本变动,所以并不是每个版本的Hive都能和现有的HBASE版本进行整合,因此对版本的范围要求比较严格.


### Phoenix
Phoenix由Saleforce.com开源,是构建在Apache Hbase之上的一个SQL中间层,可以让开发者在HBASE上执行SQL查询.Phoenix完全使用Java开发,并且提供一个客户端可嵌入的JDBC驱动.对于简单的低延迟查询,其量级为毫秒,对于百万级别的行数来说,其量级为秒.Phoenix并不像HBase那样用于map-reduce job,而是通过标准化的语言来访问HBASE数据.根据项目创建者所述,对于10万到100万行的简单查询来说,Phoenix要胜过Hive.对于使用了HBASE API,协同处理器及自定义过滤器的Impala与OpenTSDB来说,进行相似的查询Phoenix的速度也要更快一些.

### Impala
Cloudera发布实时查询开源项目Impala,经多款产品实测表明,比原来基于MapReduce的Hive SQL查询速度提升了3-90倍,Impala是google dremel的模仿,但在SQL功能上青出于蓝.

Impala采用与Hive相同的元数据,SQL语法 ODBC驱动和用户接口,但是对于平台有一定的局限性在CDH上表现良好,在其他平台上兼容性不好.



# 二 Phoenix的安装与使用
##1 概述
Apache Phoenix是构建在HBase之上的关系型数据库层，作为内嵌的客户端JDBC驱动用以对HBase中的数据进行低延迟访问。Apache Phoenix会将用户编写的sql查询编译为一系列的scan操作，最终产生通用的JDBC结果集返回给客户端。数据表的元数据存储在HBase的表中被会标记版本号，所以进行查询的时候会自动选择正确的schema。直接使用HBase的API，结合协处理器（coprocessor）和自定义的过滤器的话，小范围的查询在毫秒级响应，千万数据的话响应速度为秒级。
![](../../images/12/182730bdckgin2jtd38gpg.png)

##(1)下载
访问Phoenix的官方网站进行下载.下载地址:[http://www.apache.org/dyn/closer.lua/phoenix/](http://www.apache.org/dyn/closer.lua/phoenix/)
![](../../images/12/phoenix.png)
##(2)安装
**在Master上面的操作**

```
#sudo tar xvfz phoenix-4.7.0-HBase-1.1-bin.tar.gz 
#sudo chown -R hadoop:hadoop phoenix-4.7.0-HBase-1.1
#sudo chmod -R 775 phoenix-4.7.0-HBase-1.1
#sudo mv phoenix-4.7.0-HBase-1.1 /usr/local/phoenix
#sudo cp /usr/local/phoenix/*.jar /usr/local/hbase/lib/
#sudo cp /usr/local/hbase/conf/hbase-site.xml /usr/local/phoenix/bin/
hadoop@hadoopmaster:/usr/local$ sudo tar cvfz ~/phoenix2016.tar.gz phoenix/
scp /home/hadoop/phoenix2016.tar.gz hadoop@hadoopslave1:/home/hadoop/
scp /home/hadoop/phoenix2016.tar.gz hadoop@hadoopslave2:/home/hadoop/
```

**在Slave上面的操作**

```
hadoop@hadoopslave1:~$ tar xvfz phoenix2016.tar.gz
hadoop@hadoopslave1:~$ sudo mv phoenix /usr/local/phoenix
hadoop@hadoopslave1:~$ sudo chown -R hadoop:hadoop /usr/local/phoenix/
hadoop@hadoopslave1:~$ sudo chmod -R 775 /usr/local/phoenix/
hadoop@hadoopslave1:/usr/local/phoenix$ cp *.jar /usr/local/hbase/lib/
```

完成以上步骤,基本上Phoenix就安装成功了.

##(3)基本使用
###验证操作

```
hadoop@Master:/usr/local/phoenix/bin$ ./sqlline.py localhost
执行SQL语句
CREATE TABLE IF NOT EXISTS us_population2 (
      state CHAR(2) NOT NULL,
      city VARCHAR NOT NULL,
      population BIGINT
      CONSTRAINT my_pk2 PRIMARY KEY (state, city));
      
插入操作

upsert into us_population2(state, city, population) values('NY', 'New York', 8143197);
upsert into us_population2(state, city, population) values('CA', 'Chicago', 2842518);
upsert into us_population2(state, city, population) values('TX', 'Houston', 2016582);
upsert into us_population2(state, city, population) values('TX', 'San Antonio', 1256509);
upsert into us_population2(state, city, population) values('CA', 'San Jose', 912332);

执行操作

SELECT state as "State",count(city) as "City Count",sum(population) as "Population Sum"
FROM us_population2
GROUP BY state
ORDER BY sum(population) DESC;

0: jdbc:phoenix:localhost> SELECT state as "State",count(city) as "City Count",sum(population) as "Population Sum"
. . . . . . . . . . . . .> FROM us_population2
. . . . . . . . . . . . .> GROUP BY state
. . . . . . . . . . . . .> ORDER BY sum(population) DESC;
+--------+-------------+-----------------+
| State  | City Count  | Population Sum  |
+--------+-------------+-----------------+
| NY     | 1           | 8143197         |
| CA     | 2           | 3754850         |
| TX     | 2           | 3273091         |
+--------+-------------+-----------------+
3 rows selected (0.087 seconds)
0: jdbc:phoenix:localhost> 


```

###语法索引
[http://phoenix.apache.org/language/index.html](http://phoenix.apache.org/language/index.html)


