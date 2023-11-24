package indi.sword.util.validator.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/*
    ConstraintValidator定义了两个泛型参数,
     第一个是这个校验器所服务的 Annotation 类型(在我们的例子中即CheckCase),
     第二个这个校验器所支持到被校验元素的类型 (即String).

     如果一个约束 Annotation 支持多种类型到被校验元素的话, 那么需要为每个所支持的类型定义一个ConstraintValidator,并且注册到约束 Annotation 中.
 */
public class CheckCaseValidator implements ConstraintValidator<CheckCase, String> {

    private CaseMode caseMode;

    // initialize() 方法传进来一个所要验证的标注类型的实例, 在本例中, 我们通过此实例来获取其value属性的值,并将其保存为CaseMode类型的成员变量供下一步使用.
    @Override
    public void initialize(CheckCase constraintAnnotation) {
        this.caseMode = constraintAnnotation.value();
    }

    // isValid()是实现真正的校验逻辑的地方, 判断一个给定的String对于@CheckCase这个约束条件来说是否是合法的,
    // 同时这还要取决于在initialize()中获得的大小写模式. 根据Bean Validation中所推荐的做法,
    // 我们认为null是合法的值. 如果null对于这个元素来说是不合法的话,那么它应该使用@NotNull来标注.
    @Override
    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
        if (object == null)
            return true;

        boolean isValid;
        if (caseMode == CaseMode.UPPER) {
            isValid = object.equals(object.toUpperCase());
        }
        else {
            isValid = object.equals(object.toLowerCase());
        }

        if(!isValid) {
            // 通过使用传入的ConstraintValidatorContext对象, 我们还可以给约束条件中定义的错误信息模板来添加额外的信息或者完全创建一个新的错误信息模板.
            constraintContext.disableDefaultConstraintViolation();
            constraintContext.buildConstraintViolationWithTemplate( "{com.mycompany.constraints.CheckCase.message}"  )
                    // 在创建新的constraint violation的时候一定要记得调用addConstraintViolation, 只有这样, 这个新的constraint violation才会被真正的创建.
                    .addConstraintViolation();
        }
        return isValid;
    }

}
