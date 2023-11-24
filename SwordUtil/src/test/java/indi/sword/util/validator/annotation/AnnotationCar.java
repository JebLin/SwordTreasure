package indi.sword.util.validator.annotation;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
/**
 * @author jeb_lin
 * 8:42 PM 2022/8/10
 */
@Data
public class AnnotationCar {
    @NotNull
    private String manufacturer;

    @NotNull
    @Size(min = 2, max = 14)
//    @CheckCase(CaseMode.UPPER)
    @ValidLicensePlate
    private String licensePlate;

    @Min(2)
    private int seatCount;

    public AnnotationCar(String manufacturer, String licencePlate, int seatCount) {

        this.manufacturer = manufacturer;
        this.licensePlate = licencePlate;
        this.seatCount = seatCount;
    }

}
