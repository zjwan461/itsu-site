/*
 * @Author: Jerry Su
 * @Date: 2020-12-23 15:06:56
 * @Last Modified by: Jerry Su
 * @Last Modified time: 2020-12-28 10:39:10
 */
package com.itsu.site.framework.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@ConditionalOnClass({RedisTemplate.class, JedisPoolConfig.class, JedisPool.class})
public class RedisConfiguration {

    /**
     * 自定义redistemplate的key&value序列化方案
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        redisTemplate.setValueSerializer(jdkSerializationRedisSerializer);
        return redisTemplate;
    }

    /**
     * 自定义json形式的key-value序列化方式
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean(name = "jsonRedisTemplate")
    public RedisTemplate<String, Object> jsonRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

    /**
     * jedis连接池配置放入ioc中
     *
     * @param redisProperties
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(JedisPoolConfig.class)
    public JedisPoolConfig jedisPoolConfig(RedisProperties redisProperties) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(redisProperties.getJedis().getPool().getMaxIdle());
        jedisPoolConfig.setMinIdle(redisProperties.getJedis().getPool().getMinIdle());
        jedisPoolConfig.setMaxWaitMillis(redisProperties.getJedis().getPool().getMaxWait().toMillis());
        return jedisPoolConfig;
    }

    /**
     * jedis连接池放入ioc中
     *
     * @param jedisPoolConfig
     * @param redisProperties
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(JedisPool.class)
    public JedisPool jedisPool(JedisPoolConfig jedisPoolConfig, RedisProperties redisProperties) {
        return new JedisPool(jedisPoolConfig, redisProperties.getHost(), redisProperties.getPort(),
                (int) redisProperties.getTimeout().toMillis(), redisProperties.getPassword());
    }
}
