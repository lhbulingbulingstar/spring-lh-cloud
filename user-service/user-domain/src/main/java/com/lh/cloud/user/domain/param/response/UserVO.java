package com.lh.cloud.user.domain.param.response;

import com.lh.cloud.common.param.response.BaseVO;
import lombok.Data;

/**
 * @author: lh
 * @date: 2022/9/22 18:58
 */
@Data
public class UserVO extends BaseVO {
    private String id;

    private String name;

    private String account;
}
