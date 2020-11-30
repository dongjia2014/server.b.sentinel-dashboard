package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.RuleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigFactory;
import com.alibaba.nacos.api.config.ConfigService;

/**
 * NacosConfig
 *
 * @author cxd 2020-05-26
 * @version 1.0
 */
@EnableAutoConfiguration
@Configuration
public class NacosConfig{

    @Autowired
    private NacosConfigProperties nacosConfigProperties;

    /**
     * 非常关键 这里将FlowRuleEntity转换成FlowRule才会对客户端生效
     *
     * @return FlowRule
     */
    @Bean
    public Converter<List<FlowRuleEntity>, String> flowRuleEntityEncoder(){
        return rules -> JSON.toJSONString(rules.stream()
                .map(FlowRuleEntity::toRule)
                .collect(Collectors.toList()), true);
    }
    @Bean
    public Converter<List<DegradeRuleEntity>, String> degradeRuleEntityEncoder(){
        return rules -> JSON.toJSONString(rules.stream()
                .map(DegradeRuleEntity::toRule)
                .collect(Collectors.toList()), true);
    }

    @Bean
    public Converter<String, List<FlowRuleEntity>> flowRuleEntityDecoder(){
        return s -> JSON.parseArray(s, FlowRuleEntity.class);
    }

    @Bean
    public Converter<String, List<DegradeRuleEntity>> degradeRuleEntityDecoder(){
        return s -> JSON.parseArray(s, DegradeRuleEntity.class);
    }

    @Bean
    public ConfigService nacosConfigService() throws Exception{
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, nacosConfigProperties.getServerAddr());
        properties.put(PropertyKeyConst.NAMESPACE, nacosConfigProperties.getNamespace());
        return ConfigFactory.createConfigService(properties);
    }
}