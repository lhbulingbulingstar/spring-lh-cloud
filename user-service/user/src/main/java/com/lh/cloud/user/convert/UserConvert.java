package com.lh.cloud.user.convert;

import com.lh.cloud.user.domain.param.response.UserVO;
import com.lh.cloud.user.domain.User;
import org.mapstruct.Mapper;

/**
 * @author: lh
 * @date: 2022/9/22 18:57
 */
@Mapper(componentModel = "spring")
public interface UserConvert {
   /**
    * 用户entity转VO
    * @param user
    * @return
    */
   public UserVO entity2vo (User user);

   /**
    * 用户VO转entity
    * @param userVO
    * @return User
    */
   public User vo2entity (UserVO userVO);
}
