package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.MessageConst;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.OrderSettingDao;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {


    @Autowired
    OrderSettingDao orderSettingDao;

    @Autowired
    MemberDao memberDao;

    @Autowired
    OrderDao orderDao;

    /**
     * 业务分析
     *  1. 判断是否能预约（是否进行了预约设置）
     *      根据日期查询预约设置对象， 如果能查到，说明能预约，没有查到，不能预约
     *  2. 判断是否预约已满
     *      如果已预约大于等于可预约，不能预约，否则，可以预约
     *  3. 判断是否是会员
     *      根据手机号查询会员对象，
     *      如果能查到，说明是会员
     *          判断是否重复预约
     *              重复预约：同一人，同一天， 同一套餐
     *      如果查不到，说明不是会员
     *          自动注册成为会员
     *  4. 添加预约信息
     *      访问dao，存储预约信息到数据库
     *  5. 更新预约设置人数
     *      预约设置人数 + 1
     *
     * @param map
     * @return
     */
    @Override
    public Result addOrder(Map<String, String> map) {
        //获取参数中的日期
        String orderDateStr = map.get("orderDate");
        Date orderDate = DateUtils.parseString2Date(orderDateStr);
        //获取手机号码
        String telephone = map.get("telephone");
        //获取姓名
        String name = map.get("name");
        //获取性别
        String sex = map.get("sex");
        //获取身份证号
        String idCard = map.get("idCard");
        //获取套餐id
        String setmealIdStr = map.get("setmealId");
        //获取 预约类型
        String orderType = map.get("orderType");
        //1. 判断是否能预约
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(orderDate);
        if(orderSetting == null){
            return new Result(false, MessageConst.SELECTED_DATE_CANNOT_ORDER);
        }
        //2. 判断是否预约已满
        if (orderSetting.getReservations() >= orderSetting.getNumber()){
            return new Result(false,MessageConst.ORDER_FULL);
        }
        //3. 判断是否是会员
        Member member = memberDao.findByTelephone(telephone);
        //不是会员
        if(member == null){
            //自动注册成为会员(name, sex, idCard, phoneNumber, regTime )
            member = new Member();
            member.setName(name);
            member.setSex(sex);
            member.setIdCard(idCard);
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            //注册到数据库(主键回显)
            memberDao.add(member);
        }
        //是会员
        else{
            //是否重复预约（同一人预约了同一天同一个套餐）
            Order condition = new Order();
            condition.setSetmealId(Integer.parseInt(setmealIdStr));
            condition.setOrderDate(orderDate);
            condition.setMemberId(member.getId());
            //根据条件查询预约对象
            long count = orderDao.findByCondition(condition);
            if(count > 0){
                return new Result(false, MessageConst.HAS_ORDERED);
            }
        }

        //4. 添加预约信息
        Order order = new Order();
        order.setMemberId(member.getId());
        order.setOrderDate(orderDate);
        order.setSetmealId(Integer.parseInt(setmealIdStr));
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        order.setOrderType(orderType);

        orderDao.addOrder(order);

        //5. 更新预约设置人数
        orderSetting.setReservations(orderSetting.getReservations() + 1);
        orderSettingDao.editReservationsByOrderDate(orderSetting);

        return new Result(true, MessageConst.ORDER_SUCCESS, order );
    }

    @Override
    public Map<String, Object> findById(Integer id) {
        Map<String,Object> map = orderDao.findById(id);
        //先获取预约日期
        Date orderDate = (Date) map.get("orderDate");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String orderDateStr = sdf.format(orderDate);
        //把原来的日期类型覆盖为 字符串类型
        map.put("orderDate", orderDateStr);
        return map;
    }
}
