## 文档
### 项目文档
https://i2igfgkrnh.feishu.cn/docx/Niw7dVjbkoCVHrxHdIJcvfuMnlg

### 接口文档
https://documenter.getpostman.com/view/17613275/2s9Y5YR2Pa

## 概览
### 业务模型
![业务模型图](/assets/images/业务模型图.png)

### 存储模型
![存储模型图](/assets/images/存储模型图.jpg)

### 技术架构
![技术架构图](/assets/images/技术架构图.png)

### 项目结构
![项目结构图](/assets/images/项目结构图.jpg)

## 功能实现
### CAS单点登录
1. 授权服务器使用私钥签发令牌。
2. 网关过滤请求，添加授权请求头。
3. 资源服务器使用公钥解析并验证授权信息。

### 商品上架与索引同步
1. 数据同步服务通过canal监听商品spu数据库上架状态变化并发送mq消息。
2. 商品服务接收消息，导入或删除关联的商品sku的索引信息。

### 分布式事务处理插入订单和增加用户积分
1. 【本地事务】订单服务插入订单记录，并将增加积分封装成任务数据插入数据库。
2. 订单服务开启定时任务，扫描增加积分任务表，将扫描到的任务转换为toAdd消息发送到mq。
3. 用户服务接收toAdd消息:
   1. （一级）查看redis缓存中是否有当前任务，如果有表示正在处理，直接返回。
   2. （二级）查看mysql中是否有积分日志，如果有表示已经处理完成，直接返回。
   3. 将当前任务存到redis中。
   4. 【本地事务】修改用户积分并记录积分日志。
   5. 删除redis中的当前任务数据。
4. 用户服务发送finishAdd消息。
5. 订单服务接收finishAdd消息，【本地事务】删除任务数据并插入到历史任务表中。

### 超时订单关闭
1. 订单服务完成下单的最后一步，将订单数据转换为消息发送到orderCreate队列：
   - 此队列设置了死信交换机和消息ttl。
   - 消息超过ttl时间后由死信交换机转发到绑定的orderTimeout队列（订单超时消息）。
2. 订单服务接收订单超时消息：
   1. （一级）查看mysql中订单状态，若已完成，直接返回。
   2. （二级）通过支付服务接口查询订单状态：
      1. 若已完成支付，修改mysql中订单状态为已完成（数据补偿），并插入订单日志。
      2. 若未完成支付，修改mysql中订单状态为已关闭，并插入订单日志，接着调用商品服务接口恢复库存，调用支付服务接口手动关闭订单。

### 秒杀商品下单
1. 前置工作：秒杀服务通过定时任务将秒杀商品完整信息和库存数量加载到redis缓存，需要满足以下条件：
   - 库存大于0
   - 活动档期属于当前秒杀时间段
   - 当前缓存中没有该商品数据（没有选择覆盖是因为mysql中的库存可能存在延迟）
2. 下单时，从redis获取商品信息和库存，使用redis原子操作库存减1（限制同一用户同一商品只能秒杀一件）。如果扣减后库存为0，则删除缓存数据。
3. 将秒杀订单转换为消息发送到mq，实现异步下单，这里的消息生产者开启confirm机制保证消息不丢失：
4. 生产者发送消息时：
   1. 生成消息唯一标识作为key，消息内容作为value，缓存到redis中（消息标识数据）。
   2. 消息唯一标识作为key，发送时的<交换机,路由key/队列,消息内容>作为value，缓存到redis中（消息路由数据）。
5. 生产者发出消息后：
   1. confirm回调时ack为true表示发送成功，删除缓存的消息标识数据和消息路由数据。
   2. confirm回调时ack为false表示发送失败，通过读取路由信息重新发送消息。
6. 消费者接收到异步下单消息，将秒杀订单落库（减库存、插入订单记录），采用手动应答的方式消费：
   1. 落库成功，应答ack，完成秒杀商品下单。
   2. 落库失败，应答nack，并将消息重新入队。
   