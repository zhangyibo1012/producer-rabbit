package cn.zyblogs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableScheduling
// 扫描所需要的包基本包和工具包 包含一些工具类所在的路径
@ComponentScan(basePackages = {"org.n3r.idworker", "cn.zyblogs"})
@MapperScan(basePackages = "cn.zyblogs.mapper")
public class ProducerRabbitApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerRabbitApplication.class, args);
    }
}
