package com.itsu.site.framework.config.condition;

import cn.hutool.core.util.ArrayUtil;
import com.itsu.core.exception.InitialException;
import com.itsu.core.util.LogUtil;
import com.itsu.site.framework.config.annotation.condition.ConditionalOnProperties;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author Jerry.Su
 * @Date 2021/7/27 8:55
 */
public class OnPropertiesCondition extends SpringBootCondition {


    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Map<String, Object> attributes = metadata.getAnnotationAttributes(ConditionalOnProperties.class.getName());
        assert attributes != null;
        check(attributes);
        String[] keys = (String[]) attributes.get("keys");
        String[] values = (String[]) attributes.get("values");
        String relation = (String) attributes.get("relation");
        boolean matchIfMissing = (boolean) attributes.get("matchIfMissing");
        Environment env = context.getEnvironment();
        boolean match = true;
        if ("and".equalsIgnoreCase(relation)) {
            for (int i = 0; i < keys.length; i++) {
                String property = env.getProperty(keys[i]);
                if (matchIfMissing && !StringUtils.hasText(property)) {
                    continue;
                }
                if (!values[i].equalsIgnoreCase(property)) {
                    match = false;
                    break;
                }
            }
        } else if ("or".equalsIgnoreCase(relation)) {
            match = false;
            for (int i = 0; i < keys.length; i++) {
                String property = env.getProperty(keys[i]);
                if (matchIfMissing && !StringUtils.hasText(property)) {
                    match = true;
                    break;
                }
                if (values[i].equalsIgnoreCase(property)) {
                    match = true;
                    break;
                }
            }
        } else
            throw new InitialException("unsupported relation [" + relation + "] for ConditionalOnProperties");

        return match ? ConditionOutcome.match() : ConditionOutcome.noMatch("Conditional on " + ArrayUtil.toString(keys) + " is not match " + ArrayUtil.toString(values));
    }


    private void check(Map<String, Object> attributes) {
        try {
            String[] keys = (String[]) attributes.get("keys");
            String[] values = (String[]) attributes.get("values");
            String relation = (String) attributes.get("relation");
            if (keys.length != values.length) {
                LogUtil.error(OnPropertiesCondition.class, "keys length not match values length");
                throw new InitialException("keys length not match values length");
            }
            if (!"and".equalsIgnoreCase(relation) && !"or".equalsIgnoreCase(relation)) {
                LogUtil.error(OnPropertiesCondition.class, "relation must be and / or");
                throw new InitialException("keys length not match values length");
            }
        } catch (Exception e) {
            throw new InitialException(e);
        }
    }
}
