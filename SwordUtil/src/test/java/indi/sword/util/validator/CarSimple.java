package indi.sword.util.validator;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CarSimple {

    /**
     * 制造商
     */
    @NotNull
    private String manufacturer;

    /**
     * 牌照
     */
    @NotNull
    @Size(min = 2, max = 14)
    private String licensePlate;

    /**
     * 座位数量
     */
    @Min(2)
    private int seatCount;

    public CarSimple(String manufacturer, String licencePlate, int seatCount) {
        this.manufacturer = manufacturer;
        this.licensePlate = licencePlate;
        this.seatCount = seatCount;
    }

}
