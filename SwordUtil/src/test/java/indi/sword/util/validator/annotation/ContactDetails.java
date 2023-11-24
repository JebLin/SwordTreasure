package indi.sword.util.validator.annotation;

import javax.validation.constraints.NotNull;

/**
 * 这样, 在校验完一个ContactDetails 的示例之后,
 * 你就可以通过调用ConstraintViolation.getConstraintDescriptor().getPayload()来得到之前指定到错误级别了,
 * 并且可以根据这个信息来决定接下来到行为.
 *
 * @author jeb_lin
 * 1:53 PM 2022/8/9
 */
public class ContactDetails {
    @NotNull(message="Name is mandatory", payload=PayloadSeverity.Error.class)
    private String name;

    @NotNull(message="Phone number not specified, but not mandatory", payload=PayloadSeverity.Info.class)
    private String phoneNumber;
}
