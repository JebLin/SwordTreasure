package indi.sword.util.validator.group;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/*
    在我们的例子中有三个不同的校验组, Person.name, Car.manufacturer, Car.licensePlate 和 Car.seatCount都属于默认(Default) 组,
    Driver.age 和 Driver.hasDrivingLicense 从属于 DriverChecks组,
    而Car.passedVehicleInspection 在CarChecks组中
 */
/**
 * @author jeb_lin
 * 5:00 PM 2022/8/8
 */
@Data
public class Car {
    @NotNull
    private String manufacturer;

    @NotNull
    @Size(min = 2, max = 14)
    private String licensePlate;

    @Min(2)
    private int seatCount;

    @AssertTrue(message = "The car has to pass the vehicle inspection first", groups = CarChecks.class)
    private boolean passedVehicleInspection;

    @Valid
    private Driver driver;

    public Car(String manufacturer, String licencePlate, int seatCount) {
        this.manufacturer = manufacturer;
        this.licensePlate = licencePlate;
        this.seatCount = seatCount;
    }
}
