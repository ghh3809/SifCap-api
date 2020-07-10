package com.kotoumi.sifcapapi.dao.mapper;

import com.alibaba.fastjson.JSON;
import com.kotoumi.App;
import com.kotoumi.sifcapapi.dao.mapper.LlProxyMapper;
import com.kotoumi.sifcapapi.model.vo.service.User;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@EnableAutoConfiguration
@Slf4j
public class TestMapper {

    @Resource
    private LlProxyMapper llProxyMapper;

    @Test
    public void testMapper() {
        User user = llProxyMapper.findUser(6669728);
        TestCase.assertNotNull(user);
        log.info(JSON.toJSONString(user));
        List<User> userList = llProxyMapper.searchUser("697", 3);
        TestCase.assertFalse(userList.isEmpty());
        log.info(JSON.toJSONString(userList.get(0)));
    }

}
