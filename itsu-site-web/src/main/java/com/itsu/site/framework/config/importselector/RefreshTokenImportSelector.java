package com.itsu.site.framework.config.importselector;

import com.itsu.core.component.dytoken.LocalTokenBlackList;
import com.itsu.core.exception.CodeAbleException;
import com.itsu.core.context.ApplicationContext;
import com.itsu.core.context.DefaultApplicationContext;
import com.itsu.core.vo.sys.RefreshTokenType;
import com.itsu.site.framework.config.annotation.enable.EnableRefreshToken;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;
import java.util.Objects;

/**
 * @author Jerry Su
 * @Date 2021/5/27 17:33
 */
public class RefreshTokenImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes(EnableRefreshToken.class.getName());
        Object type = Objects.requireNonNull(attributes).get("type");
        Object customRefreshTokenAspect = Objects.requireNonNull(attributes).get("customRefreshTokenAspect");

        RefreshTokenType rt = (RefreshTokenType) type;
        Class ra = (Class) customRefreshTokenAspect;

        boolean dynamic = (boolean) attributes.get("dynamic");
        String keyPrefix = (String) attributes.get("keyPrefix");
        String expire = (String) attributes.get("expire");
        int backUpNum = (int) attributes.get("backUpNum");
        ApplicationContext dac = DefaultApplicationContext.getInstance();
        if (dynamic) {
            dac.set("dynamic", true);
            if (rt == RefreshTokenType.MEMORY) {
                dac.set("refreshTokenType", RefreshTokenType.MEMORY);
            } else if (rt == RefreshTokenType.REDIS) {
                dac.set("refreshTokenType", RefreshTokenType.REDIS);
            } else
                throw new CodeAbleException(10001, "not valid RefreshTokenType for " + EnableRefreshToken.class.getName());
        }
        dac.set("keyPrefix", keyPrefix);
        dac.set("expire", expire);
        dac.set("backUpNum", backUpNum);
        return new String[]{ra.getName(), LocalTokenBlackList.class.getName()};
    }
}
