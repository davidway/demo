package com.mymiaosha.demo.exception;

import com.mymiaosha.demo.result.CodeMsg;
import com.mymiaosha.demo.result.Result;
import jdk.nashorn.internal.objects.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(value=Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e){
         if ( e instanceof BindException){
             BindException ex = (BindException)e;
             List<ObjectError> errors = ex.getAllErrors();
             ObjectError error = errors.get(0);
             String msg = error.getDefaultMessage();
             logger.error("绑定错误",e);
             return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
         }else if ( e instanceof  GlobalException){
            GlobalException ex = (GlobalException)e;
             logger.error("全局异常",e);
            return Result.error(ex.getCm());
         } else{
             logger.error("未知异常",e);
             return Result.error(CodeMsg.SERVER_ERROR);
         }
    }
}
