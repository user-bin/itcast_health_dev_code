package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.pojo.Member;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberDao memberDao;

    //根据手机号查询会员
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }




    @Override
    public Map<String, Object> getMemberReport() {
        //获取最近的一年的月份
        List<String> months = DateUtils.getThisYearMonth();
        List<Long> memberCount = new ArrayList<>();
        //循环月份， 根据月查询  每个月底会员总数量
        //month =  2019-09
        for (String month : months) {
            month = month + "-01";
            Date thisMonthLastDay = DateUtils.getThisMonthLastDay(month);
            long count = memberDao.findBeforeByMonthLastDay(thisMonthLastDay);
            memberCount.add(count);
        }

        //创建 map返回值
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("months", months);
        resultMap.put("memberCount", memberCount);

        return resultMap;
    }

    //新增会员
    public void add(Member member) {
         memberDao.add(member);
    }
}
