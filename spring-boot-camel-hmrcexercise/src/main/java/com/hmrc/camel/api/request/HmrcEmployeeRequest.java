package com.hmrc.camel.api.request;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HmrcEmployeeRequest {
	
	 private Integer hmrcempId;
	 private String hmrcempFullName;
	 private BigDecimal hmrcempSalary;

}
