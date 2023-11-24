package indi.sword.util.validator.group;

import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;


/**
 * @author jeb_lin
 * 5:03 PM 2022/8/8
 */
@Data
public class Driver extends Person {
    @Min(value = 18, message = "You have to be 18 to drive a car", groups = DriverChecks.class)
    public int age;

    @AssertTrue(message = "You first have to pass the driving test", groups = DriverChecks.class)
    public boolean hasDrivingLicense;

    public Driver(String name) {
        super( name );
    }

    public void passedDrivingTest(boolean b) {
        hasDrivingLicense = b;
    }

}
