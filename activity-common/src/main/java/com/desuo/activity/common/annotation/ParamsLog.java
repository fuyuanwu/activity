package com.desuo.activity.common.annotation;

import java.lang.annotation.*;

/**
 * @author Fuyuanwu
 * @date 2019/1/31 16:40
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Inherited
@Documented
public @interface ParamsLog {
    String logStr() default "";
}