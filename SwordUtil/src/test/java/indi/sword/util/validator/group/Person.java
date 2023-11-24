package indi.sword.util.validator.group;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author jeb_lin
 * 5:01 PM 2022/8/8
 */
@Data
@AllArgsConstructor
public class Person {
    @NotNull
    private String name;
}
