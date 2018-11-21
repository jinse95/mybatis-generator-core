# 自己改写的一个mybatis generator core

## 改动
1. 将Mapper后缀名改为Dao(源码改动)
2. 在生成的实体类中去掉了getter,setter方法，加入了lombok的@Getter，@Setter，@ToString，@NoArgsConstructor四个注解；同时在id列加入了@Id注解
3. 在*Dao.java中去掉了基本的方法，加入了tk.mybatis.mapper.common.BaseMapper基础类（实际项目中使用需引入https://github.com/abel533/Mapper）
4. 在*Dao.xml中去掉了基本的方法，只保留了BaseResultMap和Base_Column_List

## 使用

参考本目录的generatorConfig.xml
```
<!-- 必须配置 -->
<plugin type="org.mybatis.generator.plugins.j.NoSetAndGetPlugin"/>
```
