package com.alibaba.csp.sentinel.dashboard.rule.nacos.flow;

import java.util.List;

import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConfigConstant;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.nacos.api.config.ConfigService;

/**
 * FlowRuleNacosPublisher
 *
 * @author cxd 2020-05-26
 * @version 1.0
 */
@Component("flowRuleNacosPublisher")
public class FlowRuleNacosPublisher implements DynamicRulePublisher<List<FlowRuleEntity>>{

    @Autowired
    private NacosConfigProperties nacosConfigProperties;

    @Autowired
    private ConfigService                           configService;
    @Autowired
    private Converter<List<FlowRuleEntity>, String> converter;

    @Override
    public void publish(String app, List<FlowRuleEntity> rules) throws Exception{
        AssertUtil.notEmpty(app, "app name cannot be empty");
        if(rules == null){
            return;
        }
        configService.publishConfig(app + NacosConfigConstant.FLOW_DATA_ID_POSTFIX, nacosConfigProperties.getGroupId(), converter
                .convert(rules));
    }
}