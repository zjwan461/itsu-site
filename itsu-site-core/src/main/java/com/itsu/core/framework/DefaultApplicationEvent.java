package com.itsu.core.framework;

/**
 * @author Jerry.Su
 * @Date 2021/7/19 11:38
 */
public enum DefaultApplicationEvent implements ApplicationEvent {

//    private static final Logger logger = LoggerFactory.getLogger(DefaultApplicationEvent.class);

    SET {
        @Override
        public Object publish(String key, Object value) {
            return null;
        }
    },
    GET {
        @Override
        public Object publish(String key, Object value) {
            return null;
        }
    },
    REMOVE {
        @Override
        public Object publish(String key, Object value) {
            return null;
        }
    },
    CLEAN {
        @Override
        public Object publish(String key, Object value) {
            return null;
        }
    }

}
