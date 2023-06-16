
* 本文github: https://github.com/Gin757306533/springboot-mybatis/tree/main
* 本文博客地址：https://chengxuyuancd.com/springboot-peizhishiyongmybatis.html

# 1. 创建项目，引入依赖

* 项目`pom.xml`文件详细如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.12</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.example</groupId>
    <artifactId>springboot-mybatis</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>springboot-mybatis</name>
    <description>springboot-mybatis</description>
    <properties>
        <java.version>11</java.version>
    </properties>
    <dependencies>
        <!-- https://mvnrepository.com/artifact/org.projectlombok/lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.28</version>
            <scope>provided</scope>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.3.1</version>
        </dependency>

        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>

```

* 引入的依赖有
    * springboot
    * spring web
    * mybatis
    * lombok
    * thymeleaf
    * mysql连接驱动

# 2. 设置`Controller`, `service`等

* `src/main/java/com/example/springbootmybatis/controller/AccountController.java`

```java
package com.example.springbootmybatis.controller;  
  
import com.example.springbootmybatis.service.AccountService;  
import org.springframework.web.bind.annotation.GetMapping;  
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RestController;  
  
@RestController  
@RequestMapping("/accounts")  
public class AccountController {  
    private final AccountService accountService;  
  
    public AccountController(AccountService accountService) {  
        this.accountService = accountService;  
    }  
  
    @GetMapping  
    public Object getAccountList() {  
        return accountService.findAll();  
    }  
  
}
```

* `src/main/java/com/example/springbootmybatis/service/AccountService.java`

```java
package com.example.springbootmybatis.service;  
  
import com.example.springbootmybatis.mapper.Account;  
import com.example.springbootmybatis.mapper.AccountMapper;  
import org.springframework.stereotype.Service;  
  
import java.util.List;  
  
@Service  
public class AccountService {  
    private final AccountMapper accountMapper;  
  
    public AccountService(AccountMapper accountMapper) {  
        this.accountMapper = accountMapper;  
    }  
  
    public List<Account> findAll() {  
        return accountMapper.findAll();  
    }  
}
```


* `src/main/java/com/example/springbootmybatis/mapper/Account.java`

```java
package com.example.springbootmybatis.mapper;  
  
import lombok.Data;  
  
@Data  
public class Account {  
    private int id;  
    private String username;  
    private String password;  
    private String nickName;  
    private int age;  
    private String location;  
}
```

# 3. 数据准备，建立mysql数据库
* 创建数据库`mybatis_test`
* 创建表

```mysql

use mybatis_test;
CREATE TABLE `account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `nick_name` varchar(200) DEFAULT NULL COMMENT '昵称',
  `age` int DEFAULT NULL COMMENT '备注信息',
  `location` varchar(500) DEFAULT NULL COMMENT '地址',
  PRIMARY KEY (`id`),
  UNIQUE KEY `un_name` (`username`) USING BTREE COMMENT '用户名唯一'
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8 COMMENT='后台用户表';


```

* 自己随便添加几条数据

# 4. MyBatis配置

## 4.1 Mapper建立

* `src/main/java/com/example/springbootmybatis/mapper/AccountMapper.java`
    * 注意这里的注解`@Mapper`

```java
package com.example.springbootmybatis.mapper;  
  
import org.apache.ibatis.annotations.Mapper;  
  
import java.util.List;  
  
@Mapper  
public interface AccountMapper {  
    List<Account> findAll();  
}

```

## 4.2 MyBatis配置文件

* `src/main/resources/mybatis/mapper/AccountMapper.xml`
```xml
<?xml version="1.0" encoding="UTF-8" ?>  
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"  
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">  
  
<mapper namespace="com.example.springbootmybatis.mapper.AccountMapper">  
    <resultMap id="BaseResultMap" type="Account">  
        <result column="username" property="username"></result>  
        <result column="password" property="password"></result>  
        <result column="nick_name" property="nickName"></result>  
        <result column="age" property="age"></result>  
        <result column="location" property="location"></result>  
        <result column="id" property="id"></result>  
    </resultMap>  
    <select id="findAll" resultMap="BaseResultMap">  
        select * from account  
    </select>  
</mapper>

```


# 5. 项目配置文件
* `src/main/resources/application.yml`

```yaml
server:  
  port: 9999  
  
spring:  
  datasource:  
    username: root  
    password: root  
    url: jdbc:mysql://localhost:13306/mybatis_test?useSSL=false&useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai  
    driver-class-name: com.mysql.cj.jdbc.Driver  

mybatis:  
  type-aliases-package: com.example.springbootmybatis.mapper  
  mapper-locations: classpath:mybatis/mapper/*.xml
```


# 6. 启动项目进行测试

```
➜ curl http://localhost:9999/accounts
[{"id":1,"username":"baozige","password":"123456","nickName":"包子哥","age":23,"location":"北京"}]
```

# 7. 注意

## 7.1 `AccountMapper`类上面的`@Mapper`不可省略
* 去掉之后会出现：

```
Parameter 0 of constructor in com.example.springbootmybatis.service.AccountService required a bean of type 'com.example.springbootmybatis.mapper.AccountMapper' that could not be found.
```
* 这是因为我们的`AccountMapper`是一个接口，如果什么都没有，没法实例化注入容器中，当需要用到此类型的类时，发现找不到，所以报错
* 加上`@Mapper`之后就可以让MyBatis去使用动态代理技术在容器中注入一个此接口的实现类

### 7.1.1 替代方式
* 如果不想在每个`Mapper`上都加上`@Mapper`注解，可以使用如下方法
* 在启动类上加上注解`@MapperScan("com.example.springbootmybatis.mapper")` 这里的路径可以设置为你自己的
* 全部启动类代码如下：

```java
package com.example.springbootmybatis;  
  
import org.mybatis.spring.annotation.MapperScan;  
import org.springframework.boot.SpringApplication;  
import org.springframework.boot.autoconfigure.SpringBootApplication;  
  
@SpringBootApplication  
@MapperScan("com.example.springbootmybatis.mapper")  
public class SpringbootMybatisApplication {  
  
    public static void main(String[] args) {  
        SpringApplication.run(SpringbootMybatisApplication.class, args);  
    }  
  
}
```


## 7.2 MyBatis配置文件里面的`select`写在其他地方

* 在配置文件里面，我们有:

```xml
<select id="findAll" resultMap="BaseResultMap">  
    select * from account  
</select>
```

* 可以注释下上面的代码，用更简单的方式放在`AccountMapper`里面
```java
package com.example.springbootmybatis.mapper;  
  
import org.apache.ibatis.annotations.Mapper;  
import org.apache.ibatis.annotations.Select;  
  
import java.util.List;  
  
//@Mapper  
public interface AccountMapper {  
    @Select("select * from account;")  
    List<Account> findAll();  
}
```

* 注释后的`src/main/resources/mybatis/mapper/AccountMapper.xml`

```xml
<?xml version="1.0" encoding="UTF-8" ?>  
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"  
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">  
  
<mapper namespace="com.example.springbootmybatis.mapper.AccountMapper">  
    <resultMap id="BaseResultMap" type="Account">  
        <result column="username" property="username"></result>  
        <result column="password" property="password"></result>  
        <result column="nick_name" property="nickName"></result>  
        <result column="age" property="age"></result>  
        <result column="location" property="location"></result>  
        <result column="id" property="id"></result>  
    </resultMap>  

</mapper>

```




## 7.3 如何看到程序打印sql日志

* 在`application.yml`中添加：

```yml
logging:  
  level:  
    com:  
      example:  
        springbootmybatis:  
          mapper: debug
```

* 此时重新启动程序，调用API发现打印了我们期望的日志 "select * from account;*"

```
2023-06-15 17:21:33.745  INFO 33092 --- [nio-9999-exec-1] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
2023-06-15 17:21:33.752 DEBUG 33092 --- [nio-9999-exec-1] c.e.s.mapper.AccountMapper.findAll       : ==>  Preparing: select * from account;
2023-06-15 17:21:33.772 DEBUG 33092 --- [nio-9999-exec-1] c.e.s.mapper.AccountMapper.findAll       : ==> Parameters: 
2023-06-15 17:21:33.796 DEBUG 33092 --- [nio-9999-exec-1] c.e.s.mapper.AccountMapper.findAll       : <==      Total: 1
```



# 8. MyBatis插入数据

* 首先建立 `AccountDTO`类用来接受从前端过来的数据
    * `src/main/java/com/example/springbootmybatis/controller/AccountDTO.java`

```java
package com.example.springbootmybatis.controller;  
  
import lombok.AllArgsConstructor;  
import lombok.Data;  
import lombok.NoArgsConstructor;  
  
@Data  
@NoArgsConstructor  
@AllArgsConstructor  
public class AccountDTO {  
    private int id;  
    private String username;  
    private String password;  
    private String nickName;  
    private int age;  
    private String location;  
}
```

* 接着为`AccountController`添加方法:

```java
@PostMapping  
public void addAccount(@RequestBody AccountDTO accountDTO) {  
    accountService.addAccount(accountDTO);  
}
```

* 同样的，`AccountService`也需要添加方法

```java
  
public void addAccount(AccountDTO accountDTO) {  
    Account account = new Account();  
    account.setAge(accountDTO.getAge());  
    account.setLocation(accountDTO.getLocation());  
    account.setUsername(accountDTO.getUsername());  
    account.setPassword(accountDTO.getPassword());  
    account.setNickName(accountDTO.getNickName());  
    accountMapper.add(account);  
}
```

* `AccountMapper`添加方法

```java
void add(Account account);
```

* 最后`src/main/resources/mybatis/mapper/AccountMapper.xml`配置文件添加:

```xml
<insert id="add" parameterType="Account">  
    insert into account(username, password, age, location, nick_name)  
        values            (             #{username}, #{password}, #{age}, #{location}, #{nickName}            )  
</insert>
```

## 8.1 全部完成后，进行测试

* 执行下面的语句用来进行插入，我用的是curl命令，如果你没有，可以选择用postman
```shell
curl -XPOST http://localhost:9999/accounts -H "Content-Type: application/json" -d "{\"username\": \"jie\", \"password\": \"123456\"}"
```

* 上面的意思是发送post请求到`http://localhost:9999/accounts`, `-d`后面是参数，可以改变，如果第一次成功，第二次一定要改变username哦，因为username是唯一索引

* 接着执行get请求或者直接去数据库中查看,发现插入成功

```shell
➜ curl -XGET http://localhost:9999/accounts
[{"id":1,"username":"baozige","password":"123456","nickName":null,"age":23,"location":"北京"},{"id":61,"username":"baozige2","password":"123","nickName":null,"age":0,"location":null},{"id":63,"username":"jie","password":"123456","nickName":null,"age":0,"location":null},{"id":64,"username":"jie2","password":"123456","nickName":null,"age":0,"location":null}]%
(base)
```


# 9. 分页查询

* 引入依赖

```xml
  
<!-- https://mvnrepository.com/artifact/com.github.pagehelper/pagehelper-spring-boot-starter -->  
<dependency>  
    <groupId>com.github.pagehelper</groupId>  
    <artifactId>pagehelper-spring-boot-starter</artifactId>  
    <version>1.4.7</version>  
</dependency>
```

* 增加配置`src/main/resources/application.yml`:

```yml
# 分页配置参数  
pagehelper:  
  page-size-zero: true  
  helper-dialect: mysql  
  reasonable: true  
  support-methods-arguments: true  
  params: count=countSql

```

* 在controller增加入口

```java
  
@GetMapping("/findByPages")  
public Object findByPages(  
        @RequestParam(required = false) Integer pageNum,  
        @RequestParam(required = false) Integer pageSize) {  
    return accountService.findByPages(pageNum, pageSize);  
}

```

* 增加service方法

```java
  
public Object findByPages(Integer pageNum, Integer pageSize) {  
  
    PageHelper.startPage(pageNum, pageSize);  
    List<Account> accounts = accountMapper.findAll();  
    PageInfo<Account> pageInfo = new PageInfo<>(accounts);  
    return pageInfo;  
}
```

* 注意，mapper里面之前有个地方需要稍微修改下
    * `@Select("select * from account;")` 改为 `@Select("select * from account")`
    * 把分号去了，否则会报错，说语法不对，因为PageHelper的原理是AOP，在查询语句后方拼接上分页字段：`select * from account LIMIT ?`

* 注意：`PageHelper.startPage(pageNum, pageSize); `需要放在`accountMapper.findAll();  ` 前面

* 此时启动工程，测试分页查询
    * `curl -XGET http://localhost:9999/accounts/findByPages\?pageNum\=1\&pageSize\=1`

* 得到结果,发现list里面只有一条记录，和我们预期一样

```json
{  
    "total":4,  
    "list":[  
        {  
            "id":1,  
            "username":"baozige",  
            "password":"123456",  
            "nickName":null,  
            "age":23,  
            "location":"北京"  
        }  
    ],  
    "pageNum":1,  
    "pageSize":1,  
    "size":1,  
    "startRow":1,  
    "endRow":1,  
    "pages":4,  
    "prePage":0,  
    "nextPage":2,  
    "isFirstPage":true,  
    "isLastPage":false,  
    "hasPreviousPage":false,  
    "hasNextPage":true,  
    "navigatePages":8,  
    "navigatepageNums":[  
        1,  
        2,  
        3,  
        4  
    ],  
    "navigateFirstPage":1,  
    "navigateLastPage":4  
}
```


# 最后
* 自我介绍下，我是包子哥，专注于互联网程序开发，有近十年大小厂工作经验，
* 熟悉java，python，算法等开发，带过很多学生，希望能和屏幕前的您有所交流
* 公众号，关注后可领取求职以及成长大礼包
  ![程序员充电站](http://chengxuyuancd.oss-cn-beijing.aliyuncs.com/gongchonghao%2F%E6%89%AB%E7%A0%81_%E6%90%9C%E7%B4%A2%E8%81%94%E5%90%88%E4%BC%A0%E6%92%AD%E6%A0%B7%E5%BC%8F-%E6%A0%87%E5%87%86%E8%89%B2%E7%89%88.jpg)


* 个人微信
  ![软件开发包子哥微信](http://chengxuyuancd.oss-cn-beijing.aliyuncs.com/gongzhonghao%2Fbaozige.png)


