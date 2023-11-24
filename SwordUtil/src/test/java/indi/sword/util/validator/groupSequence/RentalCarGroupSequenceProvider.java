package indi.sword.util.validator.groupSequence;

import indi.sword.util.validator.group.CarChecks;
import indi.sword.util.validator.group.DriverChecks;
import org.hibernate.validator.group.DefaultGroupSequenceProvider;

import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jeb_lin
 * 11:19 AM 2022/8/9
 */
public class RentalCarGroupSequenceProvider implements DefaultGroupSequenceProvider<RentalCar> {

    public List<Class<?>> getValidationGroups(RentalCar car) {
        List<Class<?>> defaultGroupSequence = new ArrayList<Class<?>>();
        defaultGroupSequence.add( Default.class );
        defaultGroupSequence.add( CarChecks.class );

        if ( car != null && car.isRented() ) {
            defaultGroupSequence.add( DriverChecks.class );
        }

        return defaultGroupSequence;
    }
}