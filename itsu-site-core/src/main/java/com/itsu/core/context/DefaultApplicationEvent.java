package com.itsu.core.context;

import com.itsu.core.util.LogUtil;

/**
 * @author Jerry.Su
 * @Date 2021/7/19 11:38
 */
public enum DefaultApplicationEvent implements ApplicationEvent {

    SET {
        @Override
        public void handle(String key, Object value) {
            LogUtil.debug(DefaultApplicationEvent.class, "key:{}, value:{} is set to ApplicationContext", key, value);
        }
    },
    GET {
        @Override
        public void handle(String key, Object value) {
            LogUtil.debug(DefaultApplicationEvent.class, "get value:{} of key:{} from ApplicationContext", key, value);
        }
    },
    REMOVE {
        @Override
        public void handle(String key, Object value) {
            LogUtil.debug(DefaultApplicationEvent.class, "remove key:{}, value:{} from ApplicationContext", key, value);
        }
    },
    CLEAN {
        @Override
        public void handle(String key, Object value) {
            LogUtil.debug(DefaultApplicationEvent.class, "clean all content from ApplicationContext");
        }
    }

}
