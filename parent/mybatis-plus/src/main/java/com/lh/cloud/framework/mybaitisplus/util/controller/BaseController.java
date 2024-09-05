package com.lh.cloud.framework.mybaitisplus.util.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lh.cloud.common.param.response.Result;
import com.lh.cloud.common.param.response.ResultCode;
import com.lh.cloud.framework.mybaitisplus.util.page.PageDomain;
import com.lh.cloud.framework.mybaitisplus.util.page.TableSupport;
import com.lh.cloud.framework.mybaitisplus.util.sql.SqlUtil;

import java.util.List;
/**
 * 通用pageHelper
 * @author lh
 * @date 2022/11/23 17:51
 **/
public class BaseController {
    /**
     * 设置请求分页数据
     */
    protected void startPage() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (pageNum!=null && pageSize!=null) {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected Result getDataTable(List<?> list) {
        Result rspData = new Result();
        rspData.setCode(ResultCode.SUCCESS);
        rspData.setMsg("查询成功");
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }
}