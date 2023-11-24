package indi.sword.util.validator.groupSequence;

import indi.sword.util.validator.group.Car;
import indi.sword.util.validator.group.CarChecks;
import indi.sword.util.validator.group.DriverChecks;
import org.hibernate.validator.group.GroupSequenceProvider;

import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.groups.Default;

/**
 * GroupSequence 注解写到类的头上，也就是validate方法中，重新定义该类的 Default.class
 * @author jeb_lin
 * 9:52 PM 2022/8/8
 */
//@GroupSequence({ Default.class, CarChecks.class, DriverChecks.class })
@GroupSequenceProvider(RentalCarGroupSequenceProvider.class)
public class RentalCar extends Car {
    private boolean rented;

    public RentalCar(String manufacturer, String licencePlate, int seatCount) {
        super( manufacturer, licencePlate, seatCount );
    }

    public boolean isRented() {
        return rented;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }
}
