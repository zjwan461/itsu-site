package com.itsu.core.component;

import com.itsu.core.vo.sys.RefreshTokenType;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author Jerry Su
 * @Date 2020年12月22日 下午12:12:33
 */
@ConfigurationProperties(prefix = "itsu.site")
public class ItsuSiteConfigProperties {

    private String name;

    private String domain;

    private String env = "prd";

    private String aesKey;

    private Mask mask;

    private AccessToken accessToken;

    private boolean loginAesEncrypt;

    private SecurityConfig securityConfig;

    private GlobalParamCheck globalParamCheck;

    private String uploadHtmlScript;

    private AntiCrawler antiCrawler;

    private String disableEmailSuffix;

    private SendgridConfig sendgridConfig;

    private String frontUrlPrefix;

    private String resetpwdEmailTimeout;

    private String resetpwdRedisKeyPrefix;

    private String registerEmailTimeout;

    private String registerRedisKeyPrefix;

    private String[] allowOrigins;

    private GenerateHtml generateHtml;

    private ScriptProcess scriptProcess;

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

    public static class AccessToken {

        private boolean dynamic;

        private String keyPrefix = "accesstoken:blacklist:";

        private RefreshTokenType type = RefreshTokenType.REDIS;

        private String expire;

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

    public static class SecurityConfig {
        private String loginUrl = "/login";

        private String logoutUrl = "/";

        private String authenticationCacheName = "AUTHEN:CACHE";

        private String authorizationCacheName = "AUTHORI:CACHE";

        private String hashAlgorithmName = "MD5";

        private int hashIterations = 1;

        // 单位是秒s
        private int cacheExpire = 1 * 60 * 60;

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

    public static class GlobalParamCheck {

        private boolean enable = true;

        private String[] regExs;

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
     * @return the uploadHtmlScript
     */
    public String getUploadHtmlScript() {
        return uploadHtmlScript;
    }

    /**
     * @param uploadHtmlScript the uploadHtmlScript to set
     */
    public void setUploadHtmlScript(String uploadHtmlScript) {
        this.uploadHtmlScript = uploadHtmlScript;
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

    public static class AntiCrawler {
        private boolean enable = true;

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
     * @return the disableEmailSuffix
     */
    public String getDisableEmailSuffix() {
        return disableEmailSuffix;
    }

    /**
     * @param disableEmailSuffix the disableEmailSuffix to set
     */
    public void setDisableEmailSuffix(String disableEmailSuffix) {
        this.disableEmailSuffix = disableEmailSuffix;
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

    /**
     * @return the sendgridConfig
     */
    public SendgridConfig getSendgridConfig() {
        return sendgridConfig;
    }

    /**
     * @param sendgridConfig the sendgridConfig to set
     */
    public void setSendgridConfig(SendgridConfig sendgridConfig) {
        this.sendgridConfig = sendgridConfig;
    }

    /**
     * @return the frontUrlPrefix
     */
    public String getFrontUrlPrefix() {
        return frontUrlPrefix;
    }

    /**
     * @param frontUrlPrefix the frontUrlPrefix to set
     */
    public void setFrontUrlPrefix(String frontUrlPrefix) {
        this.frontUrlPrefix = frontUrlPrefix;
    }

    /**
     * @return the resetpwdEmailTimeout
     */
    public String getResetpwdEmailTimeout() {
        return resetpwdEmailTimeout;
    }

    /**
     * @param resetpwdEmailTimeout the resetpwdEmailTimeout to set
     */
    public void setResetpwdEmailTimeout(String resetpwdEmailTimeout) {
        this.resetpwdEmailTimeout = resetpwdEmailTimeout;
    }

    /**
     * @return the resetpwdRedisKeyPrefix
     */
    public String getResetpwdRedisKeyPrefix() {
        return resetpwdRedisKeyPrefix;
    }

    /**
     * @param resetpwdRedisKeyPrefix the resetpwdRedisKeyPrefix to set
     */
    public void setResetpwdRedisKeyPrefix(String resetpwdRedisKeyPrefix) {
        this.resetpwdRedisKeyPrefix = resetpwdRedisKeyPrefix;
    }

    /**
     * @return the registerEmailTimeout
     */
    public String getRegisterEmailTimeout() {
        return registerEmailTimeout;
    }

    /**
     * @param registerEmailTimeout the registerEmailTimeout to set
     */
    public void setRegisterEmailTimeout(String registerEmailTimeout) {
        this.registerEmailTimeout = registerEmailTimeout;
    }

    /**
     * @return the registerRedisKeyPrefix
     */
    public String getRegisterRedisKeyPrefix() {
        return registerRedisKeyPrefix;
    }

    /**
     * @param registerRedisKeyPrefix the registerRedisKeyPrefix to set
     */
    public void setRegisterRedisKeyPrefix(String registerRedisKeyPrefix) {
        this.registerRedisKeyPrefix = registerRedisKeyPrefix;
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

    public static class GenerateHtml {

        private boolean enable = true;

        private String generateHtmlPath = DEFAULT_GENERATE_HTML_PATH;

        private static final String DEFAULT_GENERATE_HTML_PATH = "/usr/local/itsu-site";

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
}
