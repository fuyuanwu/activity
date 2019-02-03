package com.desuo.activity.common.annotation.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Fuyuanwu
 * @date 2019/1/31 16:41
 */
@Component
@Aspect
public class ParamsLogAspect {
    private final ObjectMapper mapper;

    @Autowired
    public ParamsLogAspect(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Pointcut("@annotation(com.desuo.activity.common.annotation.ParamsLog)")
    private void cut() {
    }

    /**
     * 定制一个环绕通知
     *
     * @param joinPoint
     */
    @Around("cut()")
    public Object advice(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger logger = LoggerFactory.getLogger(joinPoint.getThis().getClass());

        StringBuilder param = new StringBuilder();

        for (Object object : joinPoint.getArgs()) {
            if (object instanceof MultipartFile || object instanceof HttpServletRequest || object instanceof HttpServletResponse) {
                continue;
            }

            param.append(mapper.writeValueAsString(object)).append(",");
        }

        logger.info(joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + ":入参: " + param.toString());
        final Object proceed = joinPoint.proceed();
        logger.info("出参" + proceed);
        return proceed;
    }
}
