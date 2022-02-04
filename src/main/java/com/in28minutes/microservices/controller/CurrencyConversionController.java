package com.in28minutes.microservices.controller;

import com.in28minutes.microservices.repository.CurrencyExchangeServiceProxy;
import com.in28minutes.microservices.service.CurrencyConversionBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;


// https://www.javatpoint.com/client-side-load-balancing-with-ribbon
// localhost:8100/currency-converter/from/EUR/to/INR/quantity/1000

@RestController
@EnableDiscoveryClient
public class CurrencyConversionController {


    @Autowired
    private Environment env;

    @Autowired
    private CurrencyExchangeServiceProxy proxy;


    @GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from,
                                                       @PathVariable String to,
                                                       @PathVariable BigDecimal quantity )
    {

        //Feign
        CurrencyConversionBean response = proxy.retrieveExchangeValue(from, to);

        return new CurrencyConversionBean(response.getId(), from, to,
                response.getConversionMultiple(), quantity, quantity.multiply(response.getConversionMultiple()),
                response.getPort());
    }






// Sin feign lo iba a buscar directamente a:  "http://localhost:8000/curreny-exchange/from/{from}/to/{to}",
/*
    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrency(@PathVariable String from,
                                                  @PathVariable String to,
                                                  @PathVariable BigDecimal quantity
    ) {

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);

        ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity(

                CurrencyConversionBean.class, uriVariables);

        CurrencyConversionBean response = responseEntity.getBody();

        return new CurrencyConversionBean(response.getId(), from, to,
                response.getConversionMultiple(), quantity, quantity.multiply(response.getConversionMultiple()),
                response.getPort());

    }*/
}

