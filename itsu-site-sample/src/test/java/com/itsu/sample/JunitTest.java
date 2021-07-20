package com.itsu.sample;

import cn.hutool.json.JSONUtil;
import com.itsu.core.entity.Account;
import com.itsu.core.util.SystemUtil;
import com.itsu.core.vo.JsonResult;
import com.itsu.core.vo.io.req.LoginReqVo;
import com.itsu.core.vo.io.resp.LoginRespVo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        Md5Hash md5Hash = new Md5Hash("password", "ONhwKZ9oUDyzgUoaKvZW2xaqzPqiNq1b", 1);
        System.out.println(md5Hash.toString());
    }

    @Test
    public void test3() throws NoSuchFieldException {
        Field field = JsonResult.class.getDeclaredField("code");
        System.out.println(Integer.class.isAssignableFrom(field.getType()));
        System.out.println(SystemUtil.isNotSimpleField(field));
        JsonResult<Object> ok = JsonResult.ok();
        ok.setRefreshTokens(Arrays.asList("1", "2", "3"));
        System.out.println(List.class.isAssignableFrom(ArrayList.class));
        System.out.println(JSONUtil.toJsonStr("adflsjklfsjldsfjs"));
    }

}
