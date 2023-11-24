package indi.sword.util.validator.annotation;

import indi.sword.util.validator.group.Car;
import indi.sword.util.validator.group.CarChecks;
import indi.sword.util.validator.group.Driver;
import indi.sword.util.validator.group.DriverChecks;
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
public class TestAnnotation {
    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testLicensePlateNotUpperCase() {

        AnnotationCar car = new AnnotationCar("Morris", "dd-ab-123", 4);

        Set<ConstraintViolation<AnnotationCar>> constraintViolations =
                validator.validate(car);
        assertEquals(1, constraintViolations.size());
        assertEquals(
                "Case mode must be UPPER.",
                constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void carIsValid() {

        AnnotationCar car = new AnnotationCar("Morris", "DD-AB-123", 4);

        Set<ConstraintViolation<AnnotationCar>> constraintViolations =
                validator.validate(car);

        assertEquals(0, constraintViolations.size());
    }


}
