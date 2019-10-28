package soap.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * Created by ZhangPY on 2019/10/28
 * Belong Organization OVERUN-9299
 * overun9299@163.com
 * Explain:
 */
@Configuration
public class RedssonConfig {

    @Bean(destroyMethod="shutdown")
    public RedissonClient redisson() throws IOException {
        RedissonClient redisson = Redisson.create(
                Config.fromYAML(new ClassPathResource("redisson-single.yml").getInputStream()));
        return redisson;
    }
}
