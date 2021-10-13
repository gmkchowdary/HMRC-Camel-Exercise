package com.hmrc.camel.api.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HmrcEmployeeResponse {
    
     private Integer hmrcempId;
	 private String hmrcempFullName;
	 private BigDecimal hmrcempSalary;
	 private BigDecimal bonus;
    
}
