package com.itheima.handlerException;

import com.itheima.constant.MessageConst;
import com.itheima.entity.Result;
import com.itheima.exception.BusinessRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.ServletException;

/**
 * @RestController  =  @Controller  + @ResponseBody
 * @RestControllerAdvice = @ControllerAdvice + @ResponseBody
 *
 * @ControllerAdvice： controller 处理器出现异常后的通知类
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@RestControllerAdvice
@Slf4j
public class MyHandlerException {

    /**
     * @ExceptionHandler: 你要 处理的异常 类
     * @ResponseStatus：  指定 响应的状态码
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({
            BusinessRuntimeException.class
    })
    public Result handlerBusinessRuntimeException(Exception e){
        log.debug("error:" + e);
        return new Result(false, e.getMessage());
    }


    /**
     * 操作指定 异常
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler({
            ServletException.class,              // mediaType不匹配、method不匹配……
            HttpMessageConversionException.class,//http body转换异常，@RequestBody的参数
            MethodArgumentNotValidException.class ,  //http请求缺少查询参数
            MethodArgumentTypeMismatchException.class//http请求参数类型不匹配
    })
    public Result handlerOtherException(Exception e) throws Exception {
        log.debug("出错了");
        throw e;
    }


    /**
     * 操作其他异常
     * @param e
     * @return
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({
            Exception.class
    })
    public Result handlerException(Exception e) throws Exception {
        log.debug("error：" +e );
        return new Result(false, MessageConst.ACTION_FAIL);
    }
}
