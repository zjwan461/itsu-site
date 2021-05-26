/*
 * @Author: Jerry Su
 * @Date: 2021-02-02 17:38:43
 * @Last Modified by: Jerry Su
 * @Last Modified time: 2021-02-05 08:47:18
 */
package com.itsu.site.framework.component;

import cn.hutool.core.io.IoUtil;
import com.itsu.core.exception.CodeAbleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;

@Async
public class ScriptProcess {

    private static final Logger log = LoggerFactory.getLogger(ScriptProcess.class);

    /**
     * 调用脚本，默认写日志
     *
     * @param commands
     * @throws CodeAbleException
     */
    public void runScript(String... commands) throws CodeAbleException {
        runScript(true, commands);
    }

    /**
     * 调用脚本
     *
     * @param writeLog
     * @param commands
     * @throws CodeAbleException
     */
    public void runScript(boolean writeLog, String... commands) throws CodeAbleException {
        BufferedReader infoBr = null;
        BufferedReader errorBr = null;
        try {
            Process process = Runtime.getRuntime().exec(commands);
            if (writeLog) {
                infoBr = IoUtil.getUtf8Reader(process.getInputStream());
                errorBr = IoUtil.getUtf8Reader(process.getErrorStream());
                StringBuffer logBuffer = new StringBuffer();
                String line = "";
                while ((line = infoBr.readLine()) != null) {
                    logBuffer.append(line + "\n");
                }
                if (StringUtils.hasText(logBuffer.toString()))
                    log.info(logBuffer.toString());
                StringBuffer errorBuffer = new StringBuffer();
                while ((line = errorBr.readLine()) != null) {
                    errorBuffer.append(line + "\n");
                }
                if (StringUtils.hasText(errorBuffer.toString()))
                    log.error(errorBuffer.toString());
            }
            int result = process.waitFor();
            if (result != 0) {
                log.error(" process script fail ");
                throw new CodeAbleException(10001, " process script fail ");
            }
        } catch (Exception e) {
            throw new CodeAbleException(10001, e.getMessage());
        } finally {
            if (infoBr != null) {
                try {
                    infoBr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (errorBr != null) {
                try {
                    errorBr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
