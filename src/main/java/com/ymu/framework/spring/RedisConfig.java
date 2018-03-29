package com.ymu.framework.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;


@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisUrl;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.password}")
    private String pwd;

	/**
	 * 采用默认连接池
	 * @return
	 */
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setUsePool(true);
        jedisConnectionFactory.setHostName(redisUrl);
        jedisConnectionFactory.setPort(port);
        jedisConnectionFactory.setPassword(pwd);
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<String, Serializable> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new JdkSerializationRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new JdkSerializationRedisSerializer());
        return template;
    }

    public RedisTemplate strRedisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new JdkSerializationRedisSerializer());
        return template;
    }
}
