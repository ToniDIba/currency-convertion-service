package com.example.currencyconvertionservice.controller;

import com.example.currencyconvertionservice.service.CurrencyConversionBean;
import com.example.currencyconvertionservice.repository.CurrencyExchangeServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyConversionController {

    @Autowired
    private CurrencyExchangeServiceProxy proxy;

    @GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from,
                                                       @PathVariable String to,
                                                       @PathVariable BigDecimal quantity
    ) {

        //Feign
        CurrencyConversionBean response = proxy.retrieveExchangeValue(from, to);


        return new CurrencyConversionBean(response.getId(), from, to,
                response.getConversionMultiple(), quantity, quantity.multiply(response.getConversionMultiple()),
                response.getPort());


    }
}

