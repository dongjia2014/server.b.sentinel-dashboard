package com.alibaba.csp.sentinel.dashboard.rule.nacos.degrde;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConfigConstant;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConfigProperties;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.nacos.api.config.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * FlowRuleNacosProvider
 *
 * @author cxd 2020-11-26
 * @version 1.0
 */
@Component("degradeRuleNacosProvider")
public class DegradeRuleNacosProvider implements DynamicRuleProvider<List<DegradeRuleEntity>>{

    private static final Logger logger = LoggerFactory.getLogger(DegradeRuleNacosProvider.class);

    @Autowired
    private NacosConfigProperties nacosConfigProperties;

    @Autowired
    private ConfigService configService;

    @Autowired
    private Converter<String, List<DegradeRuleEntity>> converter;

    @Override
    public List<DegradeRuleEntity> getRules(String appName) throws Exception{
        String rules = configService.getConfig(appName + NacosConfigConstant.DEGRADE_DATA_ID_POSTFIX, nacosConfigProperties
                .getGroupId(), 3000);
        logger.info("从Nacos中拉取到降级规则信息:{}", rules);
        if(StringUtil.isEmpty(rules)){
            return new ArrayList<>();
        }
        return converter.convert(rules);
    }
}