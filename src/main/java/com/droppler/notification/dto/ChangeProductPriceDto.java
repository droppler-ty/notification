package com.droppler.notification.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
public class ChangeProductPriceDto {
  private Long productId;
  private BigDecimal oldPrice;
  private BigDecimal newPrice;
  private BigDecimal oldMobilePrice;
  private BigDecimal newMobilePrice;
  private List<UserDto> userList;
}
