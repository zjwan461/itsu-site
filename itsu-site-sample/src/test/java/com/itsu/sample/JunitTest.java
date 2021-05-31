package com.itsu.sample;

import cn.hutool.json.JSONUtil;
import com.itsu.core.entity.Account;
import com.itsu.core.vo.io.req.LoginReqVo;
import com.itsu.core.vo.io.resp.LoginRespVo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;

/**
 * @author Jerry Su
 * @Date 2021/5/31 22:47
 */
public class JunitTest {

    @Test
    public void test1() {
        LoginReqVo reqVo = new LoginReqVo();
        reqVo.setUsername("username");
        reqVo.setPassword("password");
        Account account = reqVo.transform2Entity();
        System.out.println(JSONUtil.toJsonPrettyStr(account));
        LoginRespVo loginRespVo = account.transform2RespObject();
        System.out.println(JSONUtil.toJsonPrettyStr(loginRespVo));
    }

    @Test
    public void test2() {
        Md5Hash md5Hash = new Md5Hash("password", "jerry", 10);
        System.out.println(md5Hash.toString());
    }

}
