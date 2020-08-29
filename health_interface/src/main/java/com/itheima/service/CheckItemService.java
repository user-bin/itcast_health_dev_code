package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem; /**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
public interface CheckItemService {

    void add(CheckItem checkItem);

    PageResult findPage(QueryPageBean queryPageBean);

    void delById(Integer id);
}
