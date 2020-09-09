package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.SetmealDao;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@Service
public class ReportServiceImpl implements ReportService {


    @Autowired
    MemberDao memberDao;

    @Autowired
    OrderDao orderDao;

    @Autowired
    SetmealDao setmealDao;

    /**
     * reportDate:null,    统计日期： 今天
     * todayNewMember :0,  今日新增会员数: 注册时间等于今天
     * totalMember :0,     总会员数：会员表有多少条数据
     * thisWeekNewMember :0,   本周新增会员数：注册时间大于等于周一
     * thisMonthNewMember :0,  本月新增会员数：注册时间大于等于1号
     * todayOrderNumber :0,    今日预约数：（有多少人预约今天来体检） 预计今天要来多少人
     * todayVisitsNumber :0,   今日到诊数: 今天实际来了多少人
     * thisWeekOrderNumber :0,  本周预约数: 预约时间 在周一到周日之间
     * thisWeekVisitsNumber :0,  本周的到诊数： 周一以后的到诊数
     * thisMonthOrderNumber :0, 本月预约数:预约时间 在1号到最后一天之间
     * thisMonthVisitsNumber :0, 本月到诊数: 1号以后的到诊数
     * hotSetmeal :[  热门套餐：预约人数最多的两个套餐
     *     {name:'阳光爸妈升级肿瘤12项筛查（男女单人）体检套餐',setmeal_count:200,proportion:0.222},
     *     {name:'阳光爸妈升级肿瘤12项筛查体检套餐',setmeal_count:200,proportion:0.222}
     * ]
     * @return
     */
    @Override
    public Map<String, Object> getBusinessReportData() {
        //获取今天的日期
        String today = DateUtils.parseDate2String(new Date());
        //获取本周一的日期
        String thisWeekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        //获取本周日的日期
        String thisWeekSunday = DateUtils.parseDate2String(DateUtils.getThisWeekSunday());
        //获取本月1号的日期
        String thisMonthFirstDay = DateUtils.parseDate2String(DateUtils.getThisMonthFirstDay());
        //获取本月最后一天的日期
        String thisMonthLastDay = DateUtils.parseDate2String(DateUtils.getThisMonthLastDay());
        //统计日期
        String reportDate = today;
        //今日新增会员数
        long todayNewMember =  memberDao.findTodayNewMember(today);
        //totalMember 总会员数
        long totalMember = memberDao.findTotalMember();
        //thisWeekNewMember 本周新增会员数: 注册时间大于等于周一
        long thisWeekNewMember = memberDao.findCountAfterByDate(thisWeekMonday);
        //thisMonthNewMember 本月新增会员数：注册时间大于等于1号
        long thisMonthNewMember = memberDao.findCountAfterByDate(thisMonthFirstDay);



        //todayOrderNumber 今日预约数
        long todayOrderNumber =  orderDao.findTodayOrderNumber(today);
        //todayVisitsNumber 今日到诊数
        long todayVisitsNumber = orderDao.findTodayVisitsNumber(today);

        //thisWeekOrderNumber 本周预约数
        long thisWeekOrderNumber = orderDao.findOrderCountByBetweenDate(thisWeekMonday, thisWeekSunday);
        //thisMonthOrderNumber 本月预约数
        long thisMonthOrderNumber = orderDao.findOrderCountByBetweenDate(thisMonthFirstDay, thisMonthLastDay);

        //thisWeekVisitsNumber  本周到诊数
        long thisWeekVisitsNumber = orderDao.findVisitsCountByAfterDate(thisWeekMonday);
        //thisMonthVisitsNumber 本月到诊数
        long thisMonthVisitsNumber = orderDao.findVisitsCountByAfterDate(thisMonthFirstDay);

        //hotSetmeal 获取热门套餐
        List<Map<String,Object>> hotSetmeal = setmealDao.findHotSetmeal();

        //创建返回值map
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("reportDate", reportDate);
        resultMap.put("todayNewMember", todayNewMember);
        resultMap.put("totalMember", totalMember);
        resultMap.put("thisWeekNewMember", thisWeekNewMember);
        resultMap.put("thisMonthNewMember", thisMonthNewMember);
        resultMap.put("todayOrderNumber", todayOrderNumber);
        resultMap.put("todayVisitsNumber", todayVisitsNumber);
        resultMap.put("thisWeekOrderNumber", thisWeekOrderNumber);
        resultMap.put("thisMonthOrderNumber", thisMonthOrderNumber);
        resultMap.put("thisWeekVisitsNumber", thisWeekVisitsNumber);
        resultMap.put("thisMonthVisitsNumber", thisMonthVisitsNumber);
        resultMap.put("hotSetmeal", hotSetmeal);


        return resultMap;
    }
}
