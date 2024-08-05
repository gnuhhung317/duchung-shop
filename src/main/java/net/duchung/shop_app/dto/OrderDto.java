package net.duchung.shop_app.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long id;
    @Min(value = 1, message = "User's ID must be > 0")
    private Long userId;

    private String fullName;

    private String email;

    @NotBlank(message = "Phone number is required")
    @Size(min = 5, message = "Phone number must be at least 5 characters")
    private String phoneNumber;

    private String address;

    private String note;

    @Min(value = 0, message = "Total money must be >= 0")
    private Float totalMoney;

    private String shippingMethod;

    private String shippingAddress;
    private Date shippingDate;
//    private String trackingNumber;


    private String paymentMethod;


}
