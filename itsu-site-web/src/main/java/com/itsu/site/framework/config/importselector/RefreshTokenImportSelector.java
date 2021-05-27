package com.itsu.site.framework.config.importselector;

import com.itsu.core.component.dytoken.LocalTokenBlackList;
import com.itsu.core.vo.sys.RefreshTokenType;
import com.itsu.site.framework.component.RefreshTokenAspectAdaptor;
import com.itsu.site.framework.config.annotation.EnableRefreshToken;
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
        if (type instanceof RefreshTokenType) {
            RefreshTokenType rt = (RefreshTokenType) type;
            if (rt == RefreshTokenType.MEMORY) {
                return new String[]{RefreshTokenAspectAdaptor.class.getName(), LocalTokenBlackList.class.getName()};
            }
        }
        return new String[]{RefreshTokenAspectAdaptor.class.getName()};
    }
}
