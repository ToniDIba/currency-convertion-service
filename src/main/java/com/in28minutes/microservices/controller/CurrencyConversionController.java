package com.in28minutes.microservices.controller;

//import com.example.currencyconvertionservice.repository.CurrencyExchangeServiceProxy;

import com.in28minutes.microservices.repository.CurrencyExchangeServiceProxy;
import com.in28minutes.microservices.service.CurrencyConversionBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerContext;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// localhost:8100/currency-converter/from/EUR/to/INR/quantity/1000

@RestController
@EnableDiscoveryClient
//public class CurrencyConversionController implements ServiceInstanceChooser {
//public class CurrencyConversionController  implements LoadBalancedRetryPolicy {
public class CurrencyConversionController {

    @LoadBalanced
    //@Autowired
    //private Environment env;

    private String serviceId;
   // private RibbonLoadBalancerContext lbContext;
   // private ServiceInstanceChooser loadBalanceChooser;


    // https://www.javatpoint.com/client-side-load-balancing-with-ribbon

    //http://localhost:8100/prova
    //  @GetMapping("/prova")
    //    public String tonto() {

    //       System.out.println("Paso por aca>");
    //ExchangeValue exchangeValue = new ExchangeValue(1000L, from, to, BigDecimal.valueOf(Long.parseLong("65")));
    //      return "funciona";
    //  }

    //com.example.currencyconvertionservice.controller.CurrencyConversionController.loadBalancerClient


    @Autowired
    private CurrencyExchangeServiceProxy proxy;

    @Autowired
    private DiscoveryClient discoveryClient;



    // localhost:8100/currency-converter-feign/from/EUR/to/INR/quantity/1000
    @GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from,
                                                       @PathVariable String to,
                                                       @PathVariable BigDecimal quantity )
    {


        System.out.println("paso por aqui");

        //Feign
        CurrencyConversionBean response = proxy.retrieveExchangeValue(from, to);

        return new CurrencyConversionBean(response.getId(), from, to,
                response.getConversionMultiple(), quantity, quantity.multiply(response.getConversionMultiple()),
                response.getPort());
    }











    /*
    public void ejecutaChoose() {

        List<ServiceInstance> instances = discoveryClient.getInstances("currency-exchange-service");
        List<String> instances2 = discoveryClient.getServices();
        ServiceInstance instance = instances.get(0);

        ServiceInstance instancex = choose("currency-exchange-service", instance.getHost().);
        // System.out.println("Tras choose: " + instancex.getInstanceId());

        // System.out.println("Antes: " + instance.getServiceId() );

        //ServiceInstance instance2  = this.loadBalanceChooser.choose ("currency-exchange-service");
        //loadBalanceChooser.choose (instance.getServiceId());


        System.out.println("Despues");

    }

    @Override
    public ServiceInstance choose(String serviceId) {

        return null;
    }

    @Override
    public <T> ServiceInstance choose(String serviceId, Request<T> request) {
        System.out.println("probando");
        ServiceInstance instancex = choose("currency-exchange-service");


        Request lbRequest = request instanceof Request ? (Request) request : new DefaultRequest<>();

        LoadBalancerRequestAdapter<T, DefaultRequestContext> lbRequest = new LoadBalancerRequestAdapter<>(request,
                new DefaultRequestContext(request, "currency-exchange-service"));

        return instancex;
    }



    //https://github.com/spring-cloud/spring-cloud-commons/blob/main/spring-cloud-loadbalancer/src/main/java/org/springframework/cloud/loadbalancer/blocking/client/BlockingLoadBalancerClient.java


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
                "http://localhost:8000/curreny-exchange/from/{from}/to/{to}",
                CurrencyConversionBean.class, uriVariables);

        CurrencyConversionBean response = responseEntity.getBody();

        return new CurrencyConversionBean(response.getId(), from, to,
                response.getConversionMultiple(), quantity, quantity.multiply(response.getConversionMultiple()),
                response.getPort());

    }*/
}

