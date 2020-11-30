package com.alibaba.csp.sentinel.dashboard.rule.nacos.degrde;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConfigConstant;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConfigProperties;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * FlowRuleNacosPublisher
 *
 * @author cxd 2020-11-26
 * @version 1.0
 */
@Component("degradeRuleNacosPublisher")
public class DegradeRuleNacosPublisher implements DynamicRulePublisher<List<DegradeRuleEntity>>{

    @Autowired
    private NacosConfigProperties nacosConfigProperties;

    @Autowired
    private ConfigService                           configService;
    @Autowired
    private Converter<List<DegradeRuleEntity>, String> converter;

    @Override
    public void publish(String app, List<DegradeRuleEntity> rules) throws Exception{
        AssertUtil.notEmpty(app, "app name cannot be empty");
        if(rules == null){
            return;
        }
        configService.publishConfig(app + NacosConfigConstant.DEGRADE_DATA_ID_POSTFIX, nacosConfigProperties.getGroupId(), converter
                .convert(rules));
    }
}