package indi.sword.util.validator.groupSequence;

import indi.sword.util.validator.group.*;
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
public class GroupSequenceTest {
    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    public void testOrderedChecksWithRedefinedDefault() {
        RentalCar rentalCar = new RentalCar( null, "DD-AB-123", 2 );
        rentalCar.setPassedVehicleInspection( true );

        Driver john = new Driver( "John Doe" );
        john.setAge( 18 );
        john.passedDrivingTest( true );
        rentalCar.setDriver( john );

        // 因为 RentalCar类注解了 GroupSequence ，所以 Default 类就被覆盖了
        assertEquals( 0, validator.validate( rentalCar, Default.class ).size() );
    }

    @Test
    public void testOrderedChecks() {
        Car car = new Car( "benz", "DD-AB-123", 2 );
        car.setPassedVehicleInspection( true );

        Driver john = new Driver( "John Doe" );
        john.setAge( 18 );
        john.passedDrivingTest( true );
        car.setDriver( john );

        assertEquals( 0, validator.validate( car, OrderedChecks.class ).size() );
    }
}
