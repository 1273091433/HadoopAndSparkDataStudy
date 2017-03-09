# 概述

开源实时日志分析ELK平台(ElasticSearch, Logstash, Kibana组成)，能很方便的帮我们收集日志，进行集中化的管理，并且能很方便的进行日志的统计和检索，下面基于ELK的最新版本5.2进行一次整合测试。

ElasticSearch是一个高可扩展的开源的全文搜索分析引擎。它允许你快速的存储、搜索和分析大量数据。ElasticSearch通常作为后端程序，为需要复杂查询的应用提供服务。

Elasticsearch是一个基于Lucene的开源分布式搜索引擎，具有分布式多用户能力。Elasticsearch是用java开发，提供Restful接口，能够达到实时搜索、高性能计算；同时Elasticsearch的横向扩展能力非常强，不需要重启服务，基本上达到了零配置。

##环境要求:
+ JDK1.8

##下载地址

[https://www.elastic.co/downloads/elasticsearch](https://www.elastic.co/downloads/elasticsearch)


##安装过程


+ 1.解压ElasticSearch并进入目录：

```
chu888chu888@hadoopmaster:~$ tar xvfz elasticsearch-5.2.1.tar.gz
```


+ 2.启动ElasticSearch
```
chu888chu888@hadoopmaster:~/elasticsearch-5.2.1$ ls
bin  config  lib  LICENSE.txt  modules  NOTICE.txt  plugins  README.textile
chu888chu888@hadoopmaster:~/elasticsearch-5.2.1$ cd bin
chu888chu888@hadoopmaster:~/elasticsearch-5.2.1/bin$ ls
elasticsearch         elasticsearch-plugin.bat       elasticsearch-systemd-pre-exec
elasticsearch.bat     elasticsearch-service.bat      elasticsearch-translog
elasticsearch.in.bat  elasticsearch-service-mgr.exe  elasticsearch-translog.bat
elasticsearch.in.sh   elasticsearch-service-x64.exe
elasticsearch-plugin  elasticsearch-service-x86.exe
chu888chu888@hadoopmaster:~/elasticsearch-5.2.1/bin$ ./elasticsearch
[2017-03-09T10:41:26,016][INFO ][o.e.n.Node               ] [] initializing ...
[2017-03-09T10:41:26,102][INFO ][o.e.e.NodeEnvironment    ] [ivu1waD] using [1] data paths, mounts [[/ (/dev/mapper/hadoopmaster--vg-root)]], net usable_space [45.9gb], net total_space [50.8gb], spins? [possibly], types [ext4]
[2017-03-09T10:41:26,102][INFO ][o.e.e.NodeEnvironment    ] [ivu1waD] heap size [1.9gb], compressed ordinary object pointers [true]
[2017-03-09T10:41:26,103][INFO ][o.e.n.Node               ] node name [ivu1waD] derived from node ID [ivu1waDpRYy4hoV5zsj-0A]; set [node.name] to override
[2017-03-09T10:41:26,104][INFO ][o.e.n.Node               ] version[5.2.1], pid[15989], build[db0d481/2017-02-09T22:05:32.386Z], OS[Linux/4.2.0-27-generic/amd64], JVM[Oracle Corporation/Java HotSpot(TM) 64-Bit Server VM/1.8.0_121/25.121-b13]
[2017-03-09T10:41:27,053][INFO ][o.e.p.PluginsService     ] [ivu1waD] loaded module [aggs-matrix-stats]
[2017-03-09T10:41:27,054][INFO ][o.e.p.PluginsService     ] [ivu1waD] loaded module [ingest-common]
[2017-03-09T10:41:27,054][INFO ][o.e.p.PluginsService     ] [ivu1waD] loaded module [lang-expression]
[2017-03-09T10:41:27,054][INFO ][o.e.p.PluginsService     ] [ivu1waD] loaded module [lang-groovy]
[2017-03-09T10:41:27,054][INFO ][o.e.p.PluginsService     ] [ivu1waD] loaded module [lang-mustache]
[2017-03-09T10:41:27,058][INFO ][o.e.p.PluginsService     ] [ivu1waD] loaded module [lang-painless]
[2017-03-09T10:41:27,058][INFO ][o.e.p.PluginsService     ] [ivu1waD] loaded module [percolator]
[2017-03-09T10:41:27,058][INFO ][o.e.p.PluginsService     ] [ivu1waD] loaded module [reindex]
[2017-03-09T10:41:27,059][INFO ][o.e.p.PluginsService     ] [ivu1waD] loaded module [transport-netty3]
[2017-03-09T10:41:27,059][INFO ][o.e.p.PluginsService     ] [ivu1waD] loaded module [transport-netty4]
[2017-03-09T10:41:27,059][INFO ][o.e.p.PluginsService     ] [ivu1waD] no plugins loaded
[2017-03-09T10:41:29,311][INFO ][o.e.n.Node               ] initialized
[2017-03-09T10:41:29,311][INFO ][o.e.n.Node               ] [ivu1waD] starting ...
[2017-03-09T10:41:29,508][INFO ][o.e.t.TransportService   ] [ivu1waD] publish_address {127.0.0.1:9300}, bound_addresses {[::1]:9300}, {127.0.0.1:9300}
[2017-03-09T10:41:29,515][WARN ][o.e.b.BootstrapChecks    ] [ivu1waD] max file descriptors [4096] for elasticsearch process is too low, increase to at least [65536]
[2017-03-09T10:41:29,515][WARN ][o.e.b.BootstrapChecks    ] [ivu1waD] max virtual memory areas vm.max_map_count [65530] is too low, increase to at least [262144]
[2017-03-09T10:41:32,575][INFO ][o.e.c.s.ClusterService   ] [ivu1waD] new_master {ivu1waD}{ivu1waDpRYy4hoV5zsj-0A}{Xhkkg5XLQ-iEUWWYBO2jeA}{127.0.0.1}{127.0.0.1:9300}, reason: zen-disco-elected-as-master ([0] nodes joined)
[2017-03-09T10:41:32,606][INFO ][o.e.g.GatewayService     ] [ivu1waD] recovered [0] indices into cluster_state
[2017-03-09T10:41:32,611][INFO ][o.e.h.HttpServer         ] [ivu1waD] publish_address {127.0.0.1:9200}, bound_addresses {[::1]:9200}, {127.0.0.1:9200}
[2017-03-09T10:41:32,612][INFO ][o.e.n.Node               ] [ivu1waD] started
```
日志中启动了两个端口分别是：9300和9200,9300用于跟其他的节点的传输，9200用于接受HTTP请求，ctrl+c可以结束进程

如果想要后台运行，可以用以下方法
```
./bin/elasticsearch -d
```

+ 3. 测试ElasticSearch
```
chu888chu888@hadoopmaster:~$ curl 127.0.0.1:9200
{
  "name" : "ivu1waD",
  "cluster_name" : "elasticsearch",
  "cluster_uuid" : "J0GWcsnES6Grl2RJapiHyA",
  "version" : {
    "number" : "5.2.1",
    "build_hash" : "db0d481",
    "build_date" : "2017-02-09T22:05:32.386Z",
    "build_snapshot" : false,
    "lucene_version" : "6.4.1"
  },
  "tagline" : "You Know, for Search"
}
chu888chu888@hadoopmaster:~$ 
```

+ 4. 让外网可以访问到我们
     因为elasticsearch安装在虚拟机里面，我希望我的主机也可以访问，需要config/elasticsearch.yml进行配置：
```
network.host: 192.168.1.159
```
重新启动后会出现错误

```
chu888chu888@hadoopmaster:~/elasticsearch-5.2.1/bin$ ./elasticsearch
[2017-03-09T10:52:46,758][INFO ][o.e.n.Node               ] [] initializing ...
[2017-03-09T10:52:46,845][INFO ][o.e.e.NodeEnvironment    ] [ivu1waD] using [1] data paths, mounts [[/ (/dev/mapper/hadoopmaster--vg-root)]], net usable_space [45.9gb], net total_space [50.8gb], spins? [possibly], types [ext4]
[2017-03-09T10:52:46,845][INFO ][o.e.e.NodeEnvironment    ] [ivu1waD] heap size [1.9gb], compressed ordinary object pointers [true]
[2017-03-09T10:52:46,847][INFO ][o.e.n.Node               ] node name [ivu1waD] derived from node ID [ivu1waDpRYy4hoV5zsj-0A]; set [node.name] to override
[2017-03-09T10:52:46,849][INFO ][o.e.n.Node               ] version[5.2.1], pid[16128], build[db0d481/2017-02-09T22:05:32.386Z], OS[Linux/4.2.0-27-generic/amd64], JVM[Oracle Corporation/Java HotSpot(TM) 64-Bit Server VM/1.8.0_121/25.121-b13]
[2017-03-09T10:52:47,796][INFO ][o.e.p.PluginsService     ] [ivu1waD] loaded module [aggs-matrix-stats]
[2017-03-09T10:52:47,796][INFO ][o.e.p.PluginsService     ] [ivu1waD] loaded module [ingest-common]
[2017-03-09T10:52:47,796][INFO ][o.e.p.PluginsService     ] [ivu1waD] loaded module [lang-expression]
[2017-03-09T10:52:47,796][INFO ][o.e.p.PluginsService     ] [ivu1waD] loaded module [lang-groovy]
[2017-03-09T10:52:47,797][INFO ][o.e.p.PluginsService     ] [ivu1waD] loaded module [lang-mustache]
[2017-03-09T10:52:47,797][INFO ][o.e.p.PluginsService     ] [ivu1waD] loaded module [lang-painless]
[2017-03-09T10:52:47,797][INFO ][o.e.p.PluginsService     ] [ivu1waD] loaded module [percolator]
[2017-03-09T10:52:47,797][INFO ][o.e.p.PluginsService     ] [ivu1waD] loaded module [reindex]
[2017-03-09T10:52:47,797][INFO ][o.e.p.PluginsService     ] [ivu1waD] loaded module [transport-netty3]
[2017-03-09T10:52:47,798][INFO ][o.e.p.PluginsService     ] [ivu1waD] loaded module [transport-netty4]
[2017-03-09T10:52:47,798][INFO ][o.e.p.PluginsService     ] [ivu1waD] no plugins loaded
[2017-03-09T10:52:49,997][INFO ][o.e.n.Node               ] initialized
[2017-03-09T10:52:49,998][INFO ][o.e.n.Node               ] [ivu1waD] starting ...
[2017-03-09T10:52:50,202][INFO ][o.e.t.TransportService   ] [ivu1waD] publish_address {192.168.1.159:9300}, bound_addresses {192.168.1.159:9300}
[2017-03-09T10:52:50,208][INFO ][o.e.b.BootstrapChecks    ] [ivu1waD] bound or publishing to a non-loopback or non-link-local address, enforcing bootstrap checks
ERROR: bootstrap checks failed
max file descriptors [4096] for elasticsearch process is too low, increase to at least [65536]
max virtual memory areas vm.max_map_count [65530] is too low, increase to at least [262144]
[2017-03-09T10:52:50,223][INFO ][o.e.n.Node               ] [ivu1waD] stopping ...
[2017-03-09T10:52:50,245][INFO ][o.e.n.Node               ] [ivu1waD] stopped
[2017-03-09T10:52:50,245][INFO ][o.e.n.Node               ] [ivu1waD] closing ...
[2017-03-09T10:52:50,253][INFO ][o.e.n.Node               ] [ivu1waD] closed

```

解决办法：
切换到root用户，修改配置limits.conf
```
chu888chu888@hadoopmaster:/etc/security$ sudo nano limits.conf

* soft nofile 65536
* hard nofile 131072
* soft nproc 2048
* hard nproc 4096
```
修改配置sysctl.conf
```
chu888chu888@hadoopmaster:/etc/security$ sudo nano /etc/sysctl.conf 

vm.max_map_count=655360
```

重新再启动后，成功
```
chu888chu888@hadoopmaster:~/elasticsearch-5.2.1/bin$ ./elasticsearch
[2017-03-09T11:02:51,271][INFO ][o.e.n.Node               ] [] initializing ...
[2017-03-09T11:02:51,358][INFO ][o.e.e.NodeEnvironment    ] [ivu1waD] using [1] data paths, mounts [[/ (/dev/mapper/hadoopmaster--vg-root)]], net usable_space [45.9gb], net total_space [50.8gb], spins? [possibly], types [ext4]
[2017-03-09T11:02:51,358][INFO ][o.e.e.NodeEnvironment    ] [ivu1waD] heap size [1.9gb], compressed ordinary object pointers [true]
[2017-03-09T11:02:51,360][INFO ][o.e.n.Node               ] node name [ivu1waD] derived from node ID [ivu1waDpRYy4hoV5zsj-0A]; set [node.name] to override
[2017-03-09T11:02:51,362][INFO ][o.e.n.Node               ] version[5.2.1], pid[1205], build[db0d481/2017-02-09T22:05:32.386Z], OS[Linux/4.2.0-27-generic/amd64], JVM[Oracle Corporation/Java HotSpot(TM) 64-Bit Server VM/1.8.0_121/25.121-b13]
[2017-03-09T11:02:52,289][INFO ][o.e.p.PluginsService     ] [ivu1waD] loaded module [aggs-matrix-stats]
[2017-03-09T11:02:52,289][INFO ][o.e.p.PluginsService     ] [ivu1waD] loaded module [ingest-common]
[2017-03-09T11:02:52,289][INFO ][o.e.p.PluginsService     ] [ivu1waD] loaded module [lang-expression]
[2017-03-09T11:02:52,289][INFO ][o.e.p.PluginsService     ] [ivu1waD] loaded module [lang-groovy]
[2017-03-09T11:02:52,289][INFO ][o.e.p.PluginsService     ] [ivu1waD] loaded module [lang-mustache]
[2017-03-09T11:02:52,290][INFO ][o.e.p.PluginsService     ] [ivu1waD] loaded module [lang-painless]
[2017-03-09T11:02:52,290][INFO ][o.e.p.PluginsService     ] [ivu1waD] loaded module [percolator]
[2017-03-09T11:02:52,290][INFO ][o.e.p.PluginsService     ] [ivu1waD] loaded module [reindex]
[2017-03-09T11:02:52,290][INFO ][o.e.p.PluginsService     ] [ivu1waD] loaded module [transport-netty3]
[2017-03-09T11:02:52,290][INFO ][o.e.p.PluginsService     ] [ivu1waD] loaded module [transport-netty4]
[2017-03-09T11:02:52,291][INFO ][o.e.p.PluginsService     ] [ivu1waD] no plugins loaded
[2017-03-09T11:02:54,576][INFO ][o.e.n.Node               ] initialized
[2017-03-09T11:02:54,576][INFO ][o.e.n.Node               ] [ivu1waD] starting ...
[2017-03-09T11:02:54,734][INFO ][o.e.t.TransportService   ] [ivu1waD] publish_address {192.168.1.159:9300}, bound_addresses {192.168.1.159:9300}
[2017-03-09T11:02:54,737][INFO ][o.e.b.BootstrapChecks    ] [ivu1waD] bound or publishing to a non-loopback or non-link-local address, enforcing bootstrap checks
[2017-03-09T11:02:57,822][INFO ][o.e.c.s.ClusterService   ] [ivu1waD] new_master {ivu1waD}{ivu1waDpRYy4hoV5zsj-0A}{GXSNbn4YSzixbO2--wwjOg}{192.168.1.159}{192.168.1.159:9300}, reason: zen-disco-elected-as-master ([0] nodes joined)
[2017-03-09T11:02:57,851][INFO ][o.e.g.GatewayService     ] [ivu1waD] recovered [0] indices into cluster_state
[2017-03-09T11:02:57,851][INFO ][o.e.h.HttpServer         ] [ivu1waD] publish_address {192.168.1.159:9200}, bound_addresses {192.168.1.159:9200}
[2017-03-09T11:02:57,852][INFO ][o.e.n.Node               ] [ivu1waD] started
```

![](../../images/21/2017030901.png)

##ElasticSearch安装错误FAQ

Elasticsearch5.0 安装问题集锦

elasticsearch 5.0 安装过程中遇到了一些问题，通过查找资料几乎都解决掉了，这里简单记录一下 ，供以后查阅参考，也希望可以帮助遇到同样问题的你。

+ 1.问题一：警告提示

```
[2016-11-06T16:27:21,712][WARN ][o.e.b.JNANatives ] unable to install syscall filter: 

java.lang.UnsupportedOperationException: seccomp unavailable: requires kernel 3.5+ with CONFIG_SECCOMP and CONFIG_SECCOMP_FILTER compiled in
at org.elasticsearch.bootstrap.Seccomp.linuxImpl(Seccomp.java:349) ~[elasticsearch-5.0.0.jar:5.0.0]
at org.elasticsearch.bootstrap.Seccomp.init(Seccomp.java:630) ~[elasticsearch-5.0.0.jar:5.0.0]

报了一大串错误，其实只是一个警告。

解决：使用比较新的linux版本，就不会出现此类问题了。
```


+ 2.问题二：ERROR: bootstrap checks failed
```

max file descriptors [4096] for elasticsearch process likely too low, increase to at least [65536]
max number of threads [1024] for user [lishang] likely too low, increase to at least [2048]

解决：切换到root用户，编辑limits.conf 添加类似如下内容

vi /etc/security/limits.conf 

添加如下内容:

* soft nofile 65536

* hard nofile 131072

* soft nproc 2048

* hard nproc 4096
```


+ 3.问题三：max number of threads [1024] for user [lish] likely too low, increase to at least [2048]
```

解决：切换到root用户，进入limits.d目录下修改配置文件。

vi /etc/security/limits.d/90-nproc.conf 

修改如下内容：

* soft nproc 2048
```


+ 4.问题四：max virtual memory areas vm.max_map_count [65530] likely too low, increase to at least [262144]
```

解决：切换到root用户修改配置sysctl.conf

vi /etc/sysctl.conf 

添加下面配置：

vm.max_map_count=655360

并执行命令：

sysctl -p

然后，重新启动elasticsearch，即可启动成功。
```




##LogStash
Logstash是一个完全开源的工具，可以对你的日志进行收集、过滤，并将其存储供以后使用，参考官网的介绍图：

![](../../images/21/175856_Lzw9_159239.png)

###安装

+ 1.解压进入目录
```
chu888chu888@hadoopmaster:~$ unzip logstash-5.2.2.zip
```

+ 2.添加配置文件
```
chu888chu888@hadoopmaster:~/logstash-5.2.2/config$ nano first-pipline.conf

```
+ 3.添加如下内容

```
input {
  log4j {
    host => "192.168.1.159"
    port => 8801
  }
}
output {
    elasticsearch {
        hosts => [ "192.168.1.159:9200" ]
    }
}
```

+ 4.启动服务

```
./bin/logstash -f config/first-pipeline.conf
```

+ 5.启动成功后的日志

```
chu888chu888@hadoopmaster:~/logstash-5.2.2$ ./bin/logstash -f ./config/first-pipline.conf 
Sending Logstash's logs to /home/chu888chu888/logstash-5.2.2/logs which is now configured via log4j2.properties
[2017-03-09T14:28:25,094][INFO ][logstash.outputs.elasticsearch] Elasticsearch pool URLs updated {:changes=>{:removed=>[], :added=>[http://192.168.1.159:9200/]}}
[2017-03-09T14:28:25,100][INFO ][logstash.outputs.elasticsearch] Running health check to see if an Elasticsearch connection is working {:healthcheck_url=>http://192.168.1.159:9200/, :path=>"/"}
log4j:WARN No appenders could be found for logger (org.apache.http.client.protocol.RequestAuthCache).
log4j:WARN Please initialize the log4j system properly.
log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
[2017-03-09T14:28:25,249][WARN ][logstash.outputs.elasticsearch] Restored connection to ES instance {:url=>#<URI::HTTP:0x734a1760 URL:http://192.168.1.159:9200/>}
[2017-03-09T14:28:25,255][INFO ][logstash.outputs.elasticsearch] Using mapping template from {:path=>nil}
[2017-03-09T14:28:25,304][INFO ][logstash.outputs.elasticsearch] Attempting to install template {:manage_template=>{"template"=>"logstash-*", "version"=>50001, "settings"=>{"index.refresh_interval"=>"5s"}, "mappings"=>{"_default_"=>{"_all"=>{"enabled"=>true, "norms"=>false}, "dynamic_templates"=>[{"message_field"=>{"path_match"=>"message", "match_mapping_type"=>"string", "mapping"=>{"type"=>"text", "norms"=>false}}}, {"string_fields"=>{"match"=>"*", "match_mapping_type"=>"string", "mapping"=>{"type"=>"text", "norms"=>false, "fields"=>{"keyword"=>{"type"=>"keyword"}}}}}], "properties"=>{"@timestamp"=>{"type"=>"date", "include_in_all"=>false}, "@version"=>{"type"=>"keyword", "include_in_all"=>false}, "geoip"=>{"dynamic"=>true, "properties"=>{"ip"=>{"type"=>"ip"}, "location"=>{"type"=>"geo_point"}, "latitude"=>{"type"=>"half_float"}, "longitude"=>{"type"=>"half_float"}}}}}}}}
[2017-03-09T14:28:25,319][INFO ][logstash.outputs.elasticsearch] Installing elasticsearch template to _template/logstash
[2017-03-09T14:28:25,586][INFO ][logstash.outputs.elasticsearch] New Elasticsearch output {:class=>"LogStash::Outputs::ElasticSearch", :hosts=>[#<URI::Generic:0x17c6fa83 URL://192.168.1.159:9200>]}
[2017-03-09T14:28:25,586][INFO ][logstash.pipeline        ] Starting pipeline {"id"=>"main", "pipeline.workers"=>1, "pipeline.batch.size"=>125, "pipeline.batch.delay"=>5, "pipeline.max_inflight"=>125}
[2017-03-09T14:28:25,597][INFO ][logstash.inputs.log4j    ] Starting Log4j input listener {:address=>"192.168.1.159:8801"}
[2017-03-09T14:28:25,611][INFO ][logstash.inputs.log4j    ] Log4j input
[2017-03-09T14:28:25,617][INFO ][logstash.pipeline        ] Pipeline main started
[2017-03-09T14:28:25,713][INFO ][logstash.agent           ] Successfully started Logstash API endpoint {:port=>9600}

```

New Elasticsearch output {:class=>”LogStash::Outputs::ElasticSearch”, :hosts=>[“192.168.111.131:9200”]}表示已经成功连接了指定的Elasticsearch。


##Kibana
Kibana可以为Logstash和ElasticSearch提供的日志分析友好的Web界面，可以帮助您汇总、分析和搜索重要数据日志。

1.解压进入目录
```
chu888chu888@hadoopmaster:~$ tar xvfz kibana-5.2.2-linux-x86_64.tar.gz
```
2.修改配置文件
```
nano config/kibana.yml
```
3.添加如下配置项
```
server.port: 5601
server.host: "192.168.1.159"
elasticsearch.url: "http://192.168.1.159:9200"
kibana.index: ".kibana"
```
4.启动服务
```
./bin/kibana
```
5.启动成功日志如下
```
chu888chu888@hadoopmaster:~/kibana-5.2.2-linux-x86_64/bin$ ./kibana
  log   [06:38:50.066] [info][status][plugin:kibana@5.2.2] Status changed from uninitialized to green - Ready
  log   [06:38:50.115] [info][status][plugin:elasticsearch@5.2.2] Status changed from uninitialized to yellow - Waiting for Elasticsearch
  log   [06:38:50.146] [info][status][plugin:console@5.2.2] Status changed from uninitialized to green - Ready
  log   [06:38:50.150] [warning] You're running Kibana 5.2.2 with some different versions of Elasticsearch. Update Kibana or Elasticsearch to the same version to prevent compatibility issues: v5.2.1 @ 192.168.1.159:9200 (192.168.1.159)
  log   [06:38:50.258] [info][status][plugin:timelion@5.2.2] Status changed from uninitialized to green - Ready
  log   [06:38:50.261] [info][listening] Server running at http://192.168.1.159:5601
  log   [06:38:50.262] [info][status][ui settings] Status changed from uninitialized to yellow - Elasticsearch plugin is yellow
  log   [06:38:55.173] [info][status][plugin:elasticsearch@5.2.2] Status changed from yellow to yellow - No existing Kibana index found
  log   [06:38:55.553] [info][status][plugin:elasticsearch@5.2.2] Status changed from yellow to green - Kibana index ready
  log   [06:38:55.553] [info][status][ui settings] Status changed from yellow to green - Ready

```
6.浏览器访问
默认第一次需要Configure an index pattern，默认的Index name是logstash-*，直接create就行了。

```
http://192.168.1.159:5601/app/kibana#/management/kibana/index/?_g=()
```
Unable to fetch mapping .Do you have indices matching the pattern?
我在这里卡住了。。我再研究一下。

![](../../images/21/kibna.png)
