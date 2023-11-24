package indi.sword.util.validator.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target( { METHOD, FIELD, ANNOTATION_TYPE }) // 表示@CheckCase 可以被用在方法, 字段或者annotation声明上.
@Retention(RUNTIME) // 表示这个标注信息是在运行期通过反射被读取的.
@Constraint(validatedBy = CheckCaseValidator.class) // 指明使用那个校验器(类) 去校验使用了此标注的元素.
@Documented // @CheckCase的类进行javadoc操作到时候, 这个标注会被添加到javadoc当中.
public @interface CheckCase {

    // 这个属性被用来定义默认得消息模版, 当这个约束条件被验证失败的时候,通过此属性来输出错误信息.
    String message() default "{indi.sword.util.validator.annotation.checkcase}";

    // groups 属性, 用于指定这个约束条件属于哪(些)个校验组(请参考第 2.3 节 “校验组”). 这个的默认值必须是Class<?>类型到空到数组.
    Class<?>[] groups() default {};

    // payload 属性, Bean Validation API 的使用者可以通过此属性来给约束条件指定严重级别. 这个属性并不被API自身所使用.
    Class<? extends Payload>[] payload() default {};

    CaseMode value();

}