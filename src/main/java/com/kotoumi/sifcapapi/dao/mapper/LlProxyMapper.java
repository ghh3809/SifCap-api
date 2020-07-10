package com.kotoumi.sifcapapi.dao.mapper;

import com.kotoumi.sifcapapi.model.vo.service.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author guohaohao
 */
public interface LlProxyMapper {

    /**
     * 根据关键字寻找用户
     * @param keyword UID/ID/昵称
     * @param limit limit数
     * @return 用户信息
     */
    List<User> searchUser(@Param("keyword") String keyword,
                          @Param("limit") int limit);

    /**
     * 根据uid寻找用户
     * @param userId 用户uid
     * @return 用户信息
     */
    User findUser(@Param("userId") int userId);

}
