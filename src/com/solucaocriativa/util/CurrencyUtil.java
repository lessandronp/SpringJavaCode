package com.solucaocriativa.util;

import java.math.BigDecimal;

public class CurrencyUtil {

    public static BigDecimal convertCurrency(String value) {
	value = value.replace(".", "").replace(",", "");
	return new BigDecimal(value).movePointLeft(2);
    }

}
