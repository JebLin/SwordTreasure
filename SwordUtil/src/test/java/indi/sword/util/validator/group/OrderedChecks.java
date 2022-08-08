package indi.sword.util.validator.group;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

/**
 * 校验组序列中有一个约束条件没有通过验证的话, 那么此约束条件后面的都不会再继续被校验了.
 * && 与的关系
 * @author jeb_lin
 * 5:40 PM 2022/8/8
 */
@GroupSequence({Default.class, CarChecks.class, DriverChecks.class})
public interface OrderedChecks {
}
