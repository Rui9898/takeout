# 外卖订餐系统
1、使用前后端分离开发大大提高了项目的可维护性在代码层面上实现了真正的解耦合，例如在前端对接后端的各个业务层面需要对接多个接口提高了系统维护性的难度，而我们利用服务网关+Nacos只需要对接一个接口，大大提高了系统的扩展性和维护性；又如在业务层面调用Feign实现业务的远程调用实现了代码层面的解耦合。

2、在Nacos的服务列表中给各个端口赋予不同的权重，根据Ribbon权重算法实现负载均衡实现各个端口被调用的流畅性，实时监控一定程度上实现了流量削峰。
