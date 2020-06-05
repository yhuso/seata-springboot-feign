##### 1.springboot整合seata
- RPC使用springcloud(feign)
- 注册中心使用nacos
- 注意最外层 gradle的seata相关依赖的配置，版本必须一致，坑真多


##### 2.启动步骤如下：
- 0.初始化sql 
    -seata.sql是事务协调者(TC)高可用全局保存事务信息的表，对应下边的-m命令先用的db，
    order,account,storage模拟三个数据源，每个库中都需要一张undo_log表，
    undo_log记录事务回滚时反向补偿的数据
    
- 1.下载seata server,https://github.com/seata/seata/releases,下载的1.2.0版本，替换掉配置文件夹seata/conf/，
（因为file.conf,registry.conf 已经重新编辑），启动seata server,命令：./seata-server.sh -p 8091 -m db
- 2.依次启动order-server,accountserver,storageserver
- 3.storageserver作为事务发起方
    - curl http://127.0.0.1:10002/test?id=2   此时不会回滚
    - curl http://127.0.0.1:10002/test?id=3   抛异常，回滚，见storageserver的service实现
    
