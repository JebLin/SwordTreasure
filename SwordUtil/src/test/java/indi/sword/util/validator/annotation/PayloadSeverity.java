package indi.sword.util.validator.annotation;

import javax.validation.Payload;

/**
 *
 * 通过payload属性来指定默认错误严重级别的示例
 *
 * @author jeb_lin
 * 1:52 PM 2022/8/9
 */

public class PayloadSeverity {
    public static class Info implements Payload {};
    public static class Error implements Payload {};
}
