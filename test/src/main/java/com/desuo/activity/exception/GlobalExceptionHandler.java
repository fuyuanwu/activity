package com.desuo.activity.exception;

import com.desuo.common.exception.CustomException;
import org.activiti.engine.ActivitiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import javax.validation.ValidationException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 异常处理
 */
@RestController
@ControllerAdvice
class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    ResponseEntity exception(Exception e) {
        logger.error("系统错误", e);
        return ResponseEntity.status(500).body("系统错误");
    }

    @ExceptionHandler(value = CustomException.class)
    @ResponseBody
    ResponseEntity exception(CustomException e) {
        logger.error("自定义错误", e);
        return ResponseEntity.status(401).body(NestedExceptionUtils.getMostSpecificCause(e).getLocalizedMessage());
    }

    @ExceptionHandler(value = ActivitiException.class)
    @ResponseBody
    ResponseEntity exception(ActivitiException e) {
        logger.error("流程引擎错误", e);
        return ResponseEntity.status(400).body(NestedExceptionUtils.getMostSpecificCause(e).getLocalizedMessage());
    }

    @ExceptionHandler(value = ResponseStatusException.class)
    @ResponseBody
    ResponseEntity exception(ResponseStatusException e) {
        logger.error("与特定HTTP响应状态代码关联的异常", e);
        return ResponseEntity.status(e.getStatus()).body(NestedExceptionUtils.getMostSpecificCause(e).getLocalizedMessage());
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseBody
    ResponseEntity exception(IllegalArgumentException e) {
        logger.error("请求参数错误", e);
        return ResponseEntity.status(400).body(NestedExceptionUtils.getMostSpecificCause(e).getLocalizedMessage());
    }

    @ExceptionHandler(value = InvalidDataAccessApiUsageException.class)
    @ResponseBody
    ResponseEntity exception(InvalidDataAccessApiUsageException e) {
        logger.error("请求参数错误", e);
        return ResponseEntity.status(400).body("请求参数错误");
    }

    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseBody
    ResponseEntity noHandlerFoundException(NoHandlerFoundException e) {
        logger.error("找不到请求路径", e);
        return ResponseEntity.status(404).body(NestedExceptionUtils.getMostSpecificCause(e).getLocalizedMessage());
    }

    @ExceptionHandler(value = ServletException.class)
    @ResponseBody
    ResponseEntity httpRequestMethodNotSupportedException(ServletException e) {
        logger.error("请求method不支持", e);
        return ResponseEntity.status(400).body(NestedExceptionUtils.getMostSpecificCause(e).getLocalizedMessage());
    }

    @ExceptionHandler(value = ValidationException.class)
    @ResponseBody
    ResponseEntity validationException(ValidationException e) {
        logger.error("参数验证失败", e);
        return ResponseEntity.status(400).body(NestedExceptionUtils.getMostSpecificCause(e).getLocalizedMessage());
    }


    @ExceptionHandler(value = MissingPathVariableException.class)
    @ResponseBody
    ResponseEntity missingPathVariableException(MissingPathVariableException e) {
        logger.error("参数验证失败", e);
        return ResponseEntity.status(400).body(NestedExceptionUtils.getMostSpecificCause(e).getLocalizedMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    ResponseEntity methodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error("参数验证失败", e);

        List<String> errMessage = new CopyOnWriteArrayList<>(Collections.singletonList("参数验证错误："));

        e.getBindingResult().getFieldErrors().forEach(fieldError -> errMessage.add(fieldError.getDefaultMessage()));

        return ResponseEntity.status(400).body(String.join(";", errMessage));
    }

    @ExceptionHandler(value = UnsatisfiedServletRequestParameterException.class)
    @ResponseBody
    ResponseEntity unsatisfiedServletRequestParameterException(UnsatisfiedServletRequestParameterException e) {
        logger.error("参数验证失败", e);
        return ResponseEntity.status(400).body(NestedExceptionUtils.getMostSpecificCause(e).getLocalizedMessage());
    }

    @ExceptionHandler(value = ServletRequestBindingException.class)
    @ResponseBody
    ResponseEntity servletRequestBindingException(ServletRequestBindingException e) {
        logger.error("参数验证失败", e);
        return ResponseEntity.status(400).body(NestedExceptionUtils.getMostSpecificCause(e).getLocalizedMessage());
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseBody
    ResponseEntity missingServletRequestParameterException(MissingServletRequestParameterException e) {
        logger.error("参数验证失败", e);
        return ResponseEntity.status(400).body(NestedExceptionUtils.getMostSpecificCause(e).getLocalizedMessage());
    }
}