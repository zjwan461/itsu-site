package com.itsu.core.component;

import com.baomidou.mybatisplus.annotation.DbType;
import com.itsu.core.vo.sys.RefreshTokenType;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author Jerry Su
 * @Date 2020年12月22日 下午12:12:33
 */
@ConfigurationProperties(prefix = "itsu.site")
public class ItsuSiteConfigProperties {

    private boolean enable = true;

    private ApiExceptionHandler apiExceptionHandler = new ApiExceptionHandler();

    private MapperCache mapperCache = new MapperCache();

    private String name = "Itsu-Site-Application";

    private String domain = "localhost";

    private String env = "prd";

    private String aesKey;

    private Mask mask = new Mask();

    private AccessToken accessToken = new AccessToken();

    private boolean loginAesEncrypt;

    private SecurityConfig securityConfig = new SecurityConfig();

    private GlobalParamCheck globalParamCheck = new GlobalParamCheck();

    private AntiCrawler antiCrawler = new AntiCrawler();

    private GenerateHtml generateHtml = new GenerateHtml();

    private ScriptProcess scriptProcess = new ScriptProcess();

    private String customErrorProperties;

    private AutoCreateDbTable autoCreateDbTable = new AutoCreateDbTable();

    private Pagination pagination = new Pagination();

    private CrossOrigin crossOrigin = new CrossOrigin();

    public CrossOrigin getCrossOrigin() {
        return crossOrigin;
    }

    public void setCrossOrigin(CrossOrigin crossOrigin) {
        this.crossOrigin = crossOrigin;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public AutoCreateDbTable getAutoCreateDbTable() {
        return autoCreateDbTable;
    }

    public void setAutoCreateDbTable(AutoCreateDbTable autoCreateDbTable) {
        this.autoCreateDbTable = autoCreateDbTable;
    }

    public ApiExceptionHandler getApiExceptionHandler() {
        return apiExceptionHandler;
    }

    public void setApiExceptionHandler(ApiExceptionHandler apiExceptionHandler) {
        this.apiExceptionHandler = apiExceptionHandler;
    }

    public MapperCache getMapperCache() {
        return mapperCache;
    }

    public void setMapperCache(MapperCache mapperCache) {
        this.mapperCache = mapperCache;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getCustomErrorProperties() {
        return customErrorProperties;
    }

    public void setCustomErrorProperties(String customErrorProperties) {
        this.customErrorProperties = customErrorProperties;
    }

    public GenerateHtml getGenerateHtml() {
        return generateHtml;
    }

    public void setGenerateHtml(GenerateHtml generateHtml) {
        this.generateHtml = generateHtml;
    }

    public ScriptProcess getScriptProcess() {
        return scriptProcess;
    }

    public void setScriptProcess(ScriptProcess scriptProcess) {
        this.scriptProcess = scriptProcess;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the aesKey
     */
    public String getAesKey() {
        return aesKey;
    }

    /**
     * @param aesKey the aesKey to set
     */
    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    /**
     * @return the mask
     */
    public Mask getMask() {
        return mask;
    }

    /**
     * @param mask the mask to set
     */
    public void setMask(Mask mask) {
        this.mask = mask;
    }

    /**
     * @return the accessToken
     */
    public AccessToken getAccessToken() {
        return accessToken;
    }

    /**
     * @param accessToken the accessToken to set
     */
    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * @return the loginAesEncrypt
     */
    public boolean isLoginAesEncrypt() {
        return loginAesEncrypt;
    }

    /**
     * @param loginAesEncrypt the loginAesEncrypt to set
     */
    public void setLoginAesEncrypt(boolean loginAesEncrypt) {
        this.loginAesEncrypt = loginAesEncrypt;
    }

    /**
     * @return the securityConfig
     */
    public SecurityConfig getSecurityConfig() {
        return securityConfig;
    }

    /**
     * @param securityConfig the securityConfig to set
     */
    public void setSecurityConfig(SecurityConfig securityConfig) {
        this.securityConfig = securityConfig;
    }

    /**
     * @return the globalParamCheck
     */
    public GlobalParamCheck getGlobalParamCheck() {
        return globalParamCheck;
    }

    /**
     * @param globalParamCheck the globalParamCheck to set
     */
    public void setGlobalParamCheck(GlobalParamCheck globalParamCheck) {
        this.globalParamCheck = globalParamCheck;
    }

    /**
     * @return the env
     */
    public String getEnv() {
        return env;
    }

    /**
     * @param env the env to set
     */
    public void setEnv(String env) {
        this.env = env;
    }

    /**
     * @return the antiCrawler
     */
    public AntiCrawler getAntiCrawler() {
        return antiCrawler;
    }

    /**
     * @param antiCrawler the antiCrawler to set
     */
    public void setAntiCrawler(AntiCrawler antiCrawler) {
        this.antiCrawler = antiCrawler;
    }

    /**
     * @return the domain
     */
    public String getDomain() {
        return domain;
    }

    /**
     * @param domain the domain to set
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }


    public static class Mask {
        private boolean log;
        private boolean resp;

        /**
         * @return the log
         */
        public boolean isLog() {
            return log;
        }

        /**
         * @param log the log to set
         */
        public void setLog(boolean log) {
            this.log = log;
        }

        /**
         * @return the resp
         */
        public boolean isResp() {
            return resp;
        }

        /**
         * @param resp the resp to set
         */
        public void setResp(boolean resp) {
            this.resp = resp;
        }

    }

    public static class AccessToken {

        private boolean dynamic = false;

        private String keyPrefix = "accesstoken:blacklist:";

        private RefreshTokenType type = RefreshTokenType.REDIS;

        private String expire = "24h";

        /**
         * @return the dynamic
         */
        public boolean isDynamic() {
            return dynamic;
        }

        /**
         * @param dynamic the dynamic to set
         */
        public void setDynamic(boolean dynamic) {
            this.dynamic = dynamic;
        }

        /**
         * @return the keyPrefix
         */
        public String getKeyPrefix() {
            return keyPrefix;
        }

        /**
         * @param keyPrefix the keyPrefix to set
         */
        public void setKeyPrefix(String keyPrefix) {
            this.keyPrefix = keyPrefix;
        }

        /**
         * @return the type
         */
        public RefreshTokenType getType() {
            return type;
        }

        /**
         * @param type the type to set
         */
        public void setType(RefreshTokenType type) {
            this.type = type;
        }

        /**
         * @return the expire
         */
        public String getExpire() {
            return expire;
        }

        /**
         * @param expire the expire to set
         */
        public void setExpire(String expire) {
            this.expire = expire;
        }
    }

    public static class SecurityConfig {
        private String loginUrl = "/login";

        private String logoutUrl = "/";

        private String authenticationCacheName = "AUTHEN:CACHE";

        private String authorizationCacheName = "AUTHORI:CACHE";

        private String hashAlgorithmName = "MD5";

        private int hashIterations = 1;

        private boolean cacheEnable = true;

        // 单位是秒s
        private int cacheExpire = 60 * 60;

        private CacheType cacheType = CacheType.MEMORY;

        public boolean isCacheEnable() {
            return cacheEnable;
        }

        public void setCacheEnable(boolean cacheEnable) {
            this.cacheEnable = cacheEnable;
        }

        public CacheType getCacheType() {
            return cacheType;
        }

        public void setCacheType(CacheType cacheType) {
            this.cacheType = cacheType;
        }

        /**
         * @return the loginUrl
         */
        public String getLoginUrl() {
            return loginUrl;
        }

        /**
         * @param loginUrl the loginUrl to set
         */
        public void setLoginUrl(String loginUrl) {
            this.loginUrl = loginUrl;
        }

        /**
         * @return the logoutUrl
         */
        public String getLogoutUrl() {
            return logoutUrl;
        }

        /**
         * @param logoutUrl the logoutUrl to set
         */
        public void setLogoutUrl(String logoutUrl) {
            this.logoutUrl = logoutUrl;
        }

        /**
         * @return the authenticationCacheName
         */
        public String getAuthenticationCacheName() {
            return authenticationCacheName;
        }

        /**
         * @param authenticationCacheName the authenticationCacheName to set
         */
        public void setAuthenticationCacheName(String authenticationCacheName) {
            this.authenticationCacheName = authenticationCacheName;
        }

        /**
         * @return the authorizationCacheName
         */
        public String getAuthorizationCacheName() {
            return authorizationCacheName;
        }

        /**
         * @param authorizationCacheName the authorizationCacheName to set
         */
        public void setAuthorizationCacheName(String authorizationCacheName) {
            this.authorizationCacheName = authorizationCacheName;
        }

        /**
         * @return the hashAlgorithmName
         */
        public String getHashAlgorithmName() {
            return hashAlgorithmName;
        }

        /**
         * @param hashAlgorithmName the hashAlgorithmName to set
         */
        public void setHashAlgorithmName(String hashAlgorithmName) {
            this.hashAlgorithmName = hashAlgorithmName;
        }

        /**
         * @return the hashIterations
         */
        public int getHashIterations() {
            return hashIterations;
        }

        /**
         * @param hashIterations the hashIterations to set
         */
        public void setHashIterations(int hashIterations) {
            this.hashIterations = hashIterations;
        }

        /**
         * @return the cacheExpire
         */
        public int getCacheExpire() {
            return cacheExpire;
        }

        /**
         * @param cacheExpire the cacheExpire to set
         */
        public void setCacheExpire(int cacheExpire) {
            this.cacheExpire = cacheExpire;
        }

        public enum CacheType {
            MEMORY, REDIS;
        }

    }

    public static class GlobalParamCheck {

        private boolean enable = false;

        private String[] regExs = {".*[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\\\\\\\]+.*"};

        /**
         * @return the enable
         */
        public boolean isEnable() {
            return enable;
        }

        /**
         * @param enable the enable to set
         */
        public void setEnable(boolean enable) {
            this.enable = enable;
        }

        /**
         * @return the regExs
         */
        public String[] getRegExs() {
            return regExs;
        }

        /**
         * @param regExs the regExs to set
         */
        public void setRegExs(String[] regExs) {
            this.regExs = regExs;
        }

    }

    public static class AntiCrawler {
        private boolean enable = false;

        private List<String> illegalUserAgents;

        private String referer;

        private Integer tsOffset;

        /**
         * @return the enable
         */
        public boolean isEnable() {
            return enable;
        }

        /**
         * @param enable the enable to set
         */
        public void setEnable(boolean enable) {
            this.enable = enable;
        }

        /**
         * @return the illegalUserAgents
         */
        public List<String> getIllegalUserAgents() {
            return illegalUserAgents;
        }

        /**
         * @param illegalUserAgents the illegalUserAgents to set
         */
        public void setIllegalUserAgents(List<String> illegalUserAgents) {
            this.illegalUserAgents = illegalUserAgents;
        }

        /**
         * @return the referer
         */
        public String getReferer() {
            return referer;
        }

        /**
         * @param referer the referer to set
         */
        public void setReferer(String referer) {
            this.referer = referer;
        }

        /**
         * @return the tsOffset
         */
        public Integer getTsOffset() {
            return tsOffset;
        }

        /**
         * @param tsOffset the tsOffset to set
         */
        public void setTsOffset(Integer tsOffset) {
            this.tsOffset = tsOffset;
        }
    }

    public static class SendgridConfig {

        private boolean enable;

        private String apiKey;

        private String sendEmail;

        private String sendEmailName;

        private String registerTemplateHk;

        private String registerTemplateTh;

        private String resetpwdTemplateHk;

        private String resetpwdTemplateTh;

        private String enquiryTemplateHk;

        private String shortlistTemplateHk;

        /**
         * @return the enable
         */
        public boolean isEnable() {
            return enable;
        }

        /**
         * @param enable the enable to set
         */
        public void setEnable(boolean enable) {
            this.enable = enable;
        }

        /**
         * @return the apiKey
         */
        public String getApiKey() {
            return apiKey;
        }

        /**
         * @param apiKey the apiKey to set
         */
        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }

        /**
         * @return the sendEmail
         */
        public String getSendEmail() {
            return sendEmail;
        }

        /**
         * @param sendEmail the sendEmail to set
         */
        public void setSendEmail(String sendEmail) {
            this.sendEmail = sendEmail;
        }

        /**
         * @return the sendEmailName
         */
        public String getSendEmailName() {
            return sendEmailName;
        }

        /**
         * @param sendEmailName the sendEmailName to set
         */
        public void setSendEmailName(String sendEmailName) {
            this.sendEmailName = sendEmailName;
        }

        /**
         * @return the registerTemplateHk
         */
        public String getRegisterTemplateHk() {
            return registerTemplateHk;
        }

        /**
         * @param registerTemplateHk the registerTemplateHk to set
         */
        public void setRegisterTemplateHk(String registerTemplateHk) {
            this.registerTemplateHk = registerTemplateHk;
        }

        /**
         * @return the registerTemplateTh
         */
        public String getRegisterTemplateTh() {
            return registerTemplateTh;
        }

        /**
         * @param registerTemplateTh the registerTemplateTh to set
         */
        public void setRegisterTemplateTh(String registerTemplateTh) {
            this.registerTemplateTh = registerTemplateTh;
        }

        /**
         * @return the resetpwdTemplateHk
         */
        public String getResetpwdTemplateHk() {
            return resetpwdTemplateHk;
        }

        /**
         * @param resetpwdTemplateHk the resetpwdTemplateHk to set
         */
        public void setResetpwdTemplateHk(String resetpwdTemplateHk) {
            this.resetpwdTemplateHk = resetpwdTemplateHk;
        }

        /**
         * @return the resetpwdTemplateTh
         */
        public String getResetpwdTemplateTh() {
            return resetpwdTemplateTh;
        }

        /**
         * @param resetpwdTemplateTh the resetpwdTemplateTh to set
         */
        public void setResetpwdTemplateTh(String resetpwdTemplateTh) {
            this.resetpwdTemplateTh = resetpwdTemplateTh;
        }

        /**
         * @return the enquiryTemplateHk
         */
        public String getEnquiryTemplateHk() {
            return enquiryTemplateHk;
        }

        /**
         * @param enquiryTemplateHk the enquiryTemplateHk to set
         */
        public void setEnquiryTemplateHk(String enquiryTemplateHk) {
            this.enquiryTemplateHk = enquiryTemplateHk;
        }

        /**
         * @return the shortlistTemplateHk
         */
        public String getShortlistTemplateHk() {
            return shortlistTemplateHk;
        }

        /**
         * @param shortlistTemplateHk the shortlistTemplateHk to set
         */
        public void setShortlistTemplateHk(String shortlistTemplateHk) {
            this.shortlistTemplateHk = shortlistTemplateHk;
        }
    }

    public static class GenerateHtml {

        private static final String DEFAULT_GENERATE_HTML_PATH = "/usr/local/itsu-site";
        private boolean enable = false;
        private String generateHtmlPath = DEFAULT_GENERATE_HTML_PATH;

        public String getGenerateHtmlPath() {
            return generateHtmlPath;
        }

        public void setGenerateHtmlPath(String generateHtmlPath) {
            this.generateHtmlPath = generateHtmlPath;
        }

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }
    }

    public static class ScriptProcess {

        private boolean enable;

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }
    }

    public static class MapperCache {

        private CacheType cacheType = CacheType.MEMORY;

        private int cacheTime = 30;

        private String cachePrefix = "mybatis:cache";

        public String getCachePrefix() {
            return cachePrefix;
        }

        public void setCachePrefix(String cachePrefix) {
            this.cachePrefix = cachePrefix;
        }

        public int getCacheTime() {
            return cacheTime;
        }

        public void setCacheTime(int cacheTime) {
            this.cacheTime = cacheTime;
        }

        public CacheType getCacheType() {
            return cacheType;
        }

        public void setCacheType(CacheType cacheType) {
            this.cacheType = cacheType;
        }

        public enum CacheType {
            REDIS, MEMORY
        }
    }

    public static class ApiExceptionHandler {
        private boolean enable = false;

        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }
    }

    public static class AutoCreateDbTable {
        private boolean enable = false;

        private Type type = Type.CREATE;//create未有表时创建；update未有表时创建，有表时删除原来的表和数据重新创建

        private boolean initData = true;


        /**
         * get field
         *
         * @return initData
         */
        public boolean isInitData() {
            return this.initData;
        }

        /**
         * set field
         *
         * @param initData
         */
        public void setInitData(boolean initData) {
            this.initData = initData;
        }

        /**
         * get field
         *
         * @return type
         */
        public Type getType() {
            return this.type;
        }

        /**
         * set field
         *
         * @param type
         */
        public void setType(Type type) {
            this.type = type;
        }


        public enum Type {
            CREATE, UPDATE
        }

        /**
         * get field
         *
         * @return enable
         */
        public boolean isEnable() {
            return this.enable;
        }

        /**
         * set field
         *
         * @param enable
         */
        public void setEnable(boolean enable) {
            this.enable = enable;
        }

    }

    public class Pagination {
        private boolean overflow = false;
        private Long maxLimit = 500L;
        private DbType dbType = DbType.MYSQL;

        public DbType getDbType() {
            return dbType;
        }

        public void setDbType(DbType dbType) {
            this.dbType = dbType;
        }

        public boolean isOverflow() {
            return overflow;
        }

        public void setOverflow(boolean overflow) {
            this.overflow = overflow;
        }

        public Long getMaxLimit() {
            return maxLimit;
        }

        public void setMaxLimit(Long maxLimit) {
            this.maxLimit = maxLimit;
        }
    }

    public static class CrossOrigin {
        private String[] allowOrigins = {"all"};

        private String[] allowMethods = {"GET", "PUT", "POST", "DELETE"};

        private Integer maxAge = 3600;

        private String[] allowHeaders = {"x-requested-with", "accesstoken"};

        public Integer getMaxAge() {
            return maxAge;
        }

        public void setMaxAge(Integer maxAge) {
            this.maxAge = maxAge;
        }

        public String[] getAllowHeaders() {
            return allowHeaders;
        }

        public void setAllowHeaders(String[] allowHeaders) {
            this.allowHeaders = allowHeaders;
        }

        public String[] getAllowMethods() {
            return allowMethods;
        }

        public void setAllowMethods(String[] allowMethods) {
            this.allowMethods = allowMethods;
        }

        /**
         * @return the allowOrigins
         */
        public String[] getAllowOrigins() {
            return allowOrigins;
        }

        /**
         * @param allowOrigins the allowOrigins to set
         */
        public void setAllowOrigins(String[] allowOrigins) {
            if (allowOrigins != null && allowOrigins.length == 1 && allowOrigins[0].equals("all"))
                this.allowOrigins = new String[]{"*"};
            else
                this.allowOrigins = allowOrigins;
        }
    }
}
