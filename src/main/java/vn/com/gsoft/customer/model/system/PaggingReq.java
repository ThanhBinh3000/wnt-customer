package vn.com.gsoft.customer.model.system;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;


@Data
public class PaggingReq {
    @NotNull(message = "Không được để trống")
    @ApiModelProperty(example = "20")
    Integer limit;
    @NotNull(message = "Không được để trống")
    @PositiveOrZero(message = "Trang tìm kiếm phải >= 1")
    @ApiModelProperty(example = "1")
    Integer page;
}