package com.test.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.exporter.common.TextFormat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Writer;
import java.util.Calendar;
import java.util.Date;
import java.io.*;
import java.text.*;
import java.util.*;

@RestController
public class MyController {
	
	private final Logger logger = LogManager.getLogger(MyController.class);
	
	private final Counter promRequestsTotal = Counter.build()
            .name("requests_total_homersimpson")
            .help("Total number of requests on homersimpson")
            .register();
			
	
	private final Counter promRequestsTotal2 = Counter.build()
            .name("requests_total_covilha")
            .help("Total number of requests on covilha time")
            .register();
			
	@RequestMapping(path = "/hello")
    public @ResponseBody String sayHello() {
       promRequestsTotal.inc();
       return "hello, world";
   }

    @RequestMapping(value = "/homersimpson", method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)

    public void getImage(HttpServletResponse response) throws IOException {
		promRequestsTotal.inc();

        //var imgFile = new ClassPathResource("image/image.jpg");

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(new ClassPathResource("image/image.jpg").getInputStream(), response.getOutputStream());
    }
	
    @RequestMapping(value="/covilha")
    public @ResponseBody String test( ) {
	promRequestsTotal2.inc();
	
	TimeZone tz = TimeZone.getTimeZone("GMT+1"); 
    Calendar c = Calendar.getInstance(tz); 
	  
    DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
    formatter.setTimeZone(tz);
	  //return"Current Time in Covilha, Portugal: " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + formatter.format(c.getTime());
    return"Current Time in Covilha, Portugal: " +  formatter.format(c.getTime());  
}
	
	@RequestMapping(path = "/prometheus")
    public void metrics(Writer responseWriter) throws IOException {
		logger.info("Starting service for metrics");
        TextFormat.write004(responseWriter, CollectorRegistry.defaultRegistry.metricFamilySamples());
        responseWriter.close();
    }
}