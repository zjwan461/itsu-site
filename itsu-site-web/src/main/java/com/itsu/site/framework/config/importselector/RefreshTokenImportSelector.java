package com.itsu.site.framework.config.importselector;

import com.itsu.core.component.dytoken.LocalTokenBlackList;
import com.itsu.core.exception.CodeAbleException;
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
        if (rt == RefreshTokenType.MEMORY) {
            // 选择本地缓存需要额外加载本地缓存到IOC中
            return new String[]{ra.getName(), LocalTokenBlackList.class.getName()};
        } else if (rt == RefreshTokenType.REDIS) {
            // 选择Redis缓存直接将refreshtoken切面注入IOC中
            return new String[]{ra.getName()};
        } else
            throw new CodeAbleException(10001, "not valid RefreshTokenType for " + EnableRefreshToken.class.getName());
    }
}
