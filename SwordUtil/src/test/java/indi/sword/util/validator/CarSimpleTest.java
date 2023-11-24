package indi.sword.util.validator;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.google.common.collect.Lists;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 学习文档：
 * https://docs.jboss.org/hibernate/validator/4.2/reference/zh-CN/html_single/
 *
 */
public class CarSimpleTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    public void manufacturerIsNull() {
        CarSimple car = new CarSimple(null, "京A-88888", 4);

        Set<ConstraintViolation<CarSimple>> constraintViolations = validator.validate(car);
        assertEquals(1, constraintViolations.size());
        System.out.println(constraintViolations.iterator().next().getMessage());
        assertEquals("may not be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void licensePlateTooShort() {
        CarSimple car = new CarSimple("Benz", "D", 4);

        Set<ConstraintViolation<CarSimple>> constraintViolations =  validator.validate(car);
        List<ConstraintViolation<CarSimple>> constraintViolationList = Lists.newArrayList(constraintViolations);

        assertEquals(1, constraintViolations.size());

        System.out.println(constraintViolationList.get(0).getMessage());
        assertEquals("size must be between 2 and 14", constraintViolationList.get(0).getMessage());
    }

    @Test
    public void seatCountTooLow() {
        CarSimple car = new CarSimple("Benz", "京A-88888", 1);

        Set<ConstraintViolation<CarSimple>> constraintViolations = validator.validate(car);
        List<ConstraintViolation<CarSimple>> constraintViolationList = Lists.newArrayList(constraintViolations);

        assertEquals(1, constraintViolations.size());

        System.out.println(constraintViolationList.get(0).getMessage());

        assertEquals("must be greater than or equal to 2", constraintViolationList.get(0).getMessage());
    }


    /*
        如果一个对象没有校验出问题的话,那么validate() 会返回一个空的set对象.
     */
    @Test
    public void carIsValid() {
        CarSimple car = new CarSimple("Benz", "京A-88888", 2);

        Set<ConstraintViolation<CarSimple>> constraintViolations =
                validator.validate(car);

        assertEquals(0, constraintViolations.size());
    }


    @Test
    public void testValidateProperty() {
        CarSimple car = new CarSimple(null, "京A-88888", 2);

        // 只校验指定字段，就算 manufacturer 第一个校验条件不符合，也不报错
//        Set<ConstraintViolation<Car>> constraintViolations = validator.validateProperty(car, "manufacturer");
        Set<ConstraintViolation<CarSimple>> constraintViolations = validator.validateProperty(car, "seatCount");

        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void testValidateValue() {
        Set<ConstraintViolation<CarSimple>> constraintViolations = validator.validateValue(CarSimple.class, "manufacturer", null);

        assertEquals(1, constraintViolations.size());
        assertEquals("may not be null", constraintViolations.iterator().next().getMessage());
    }
}
