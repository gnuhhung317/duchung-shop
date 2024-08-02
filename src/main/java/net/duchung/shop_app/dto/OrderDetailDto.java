package net.duchung.shop_app.dto;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDto {
    @Min(value = 1,message = "Order's ID cannot be less than 1")
    private Long orderId;

    @Min(value = 1,message = "Product's ID cannot be less than 1")
    private Long productId;

    @Min(value = 1, message = "Number of products cannot be less than 1")
    private int numberOfProducts;

    @Min(value = 0, message = "Price cannot be less than 0")
    private Float price;

    @Min(value = 0, message = "Total money cannot be less than 0")
    private Float totalMoney;

    private String color;
}
