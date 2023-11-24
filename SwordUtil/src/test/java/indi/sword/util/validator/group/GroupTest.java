package indi.sword.util.validator.group;

import indi.sword.util.validator.groupSequence.OrderedChecks;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * @author jeb_lin
 * 5:21 PM 2022/8/8
 */
public class GroupTest {
    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    public void driveAway() {
        // create a car and check that everything is ok with it.
        Car car = new Car("Benz", "京A-88888", 2);

        // 首先我们创建一辆汽车然后在没有明确指定使用哪个校验组的情况下校验它,
        // 可以看到即使passedVehicleInspection的默认值是false也不会校验出错误来.
        // 因为定义在这个属性上的约束条件并不属于默认的校验组,
        Set<ConstraintViolation<Car>> constraintViolations = validator.validate(car);
        assertEquals(0, constraintViolations.size());

        // but has it passed the vehicle inspection?
        constraintViolations = validator.validate(car, CarChecks.class);
        // 接下来,我们来校验CarChecks这个组, 这样就会发现car违反了约束条件, 必须让这个车先通过检测.
        assertEquals(1, constraintViolations.size());
        assertEquals("The car has to pass the vehicle inspection first", constraintViolations.iterator().next().getMessage());

        // let's go to the vehicle inspection
        car.setPassedVehicleInspection(true);
        assertEquals(0, validator.validate(car).size());

        // now let's add a driver. He is 18, but has not passed the driving test yet
        Driver john = new Driver("John Doe");
        john.setAge(18);
        car.setDriver(john);
        constraintViolations = validator.validate(car, DriverChecks.class);
        //  接下来,我们给这个车增加一个司机, 然后在基于DriverChecks来校验, 会发现因为这个司机因为还没有通过驾照考试, 所以又一次得到了校验错误
        assertEquals(1, constraintViolations.size());
        assertEquals("You first have to pass the driving test", constraintViolations.iterator().next().getMessage());

        // ok, John passes the test
        // 如果我们设置passedDrivingTest属性为true之后, DriverChecks组的校验就通过了.
        john.passedDrivingTest(true);
        assertEquals(0, validator.validate(car, DriverChecks.class).size());

        // just checking that everything is in order now
        assertEquals(0, validator.validate(car, Default.class, CarChecks.class, DriverChecks.class).size());
    }


}
