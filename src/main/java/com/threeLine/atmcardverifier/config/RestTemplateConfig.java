package com.threeLine.atmcardverifier.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.ResourceRegionHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {


@Bean
    public RestTemplate restTemplate() {

    HttpComponentsClientHttpRequestFactory httpRequestFactory =
            new HttpComponentsClientHttpRequestFactory(getHttpClient());

    /*Configure connection timeouts so as not to use the infinite timeout default setting
    for restTemplate */
    httpRequestFactory.setConnectionRequestTimeout(60 * 1000);
    httpRequestFactory.setConnectTimeout(40 * 1000);
    httpRequestFactory.setReadTimeout(3 * 60 * 1000);

    RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
    restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
    restTemplate.getMessageConverters().add(new ResourceHttpMessageConverter());
    restTemplate.getMessageConverters().add(new ResourceRegionHttpMessageConverter());
    restTemplate.getMessageConverters().add(new AllEncompassingFormHttpMessageConverter());
    return restTemplate;
}

 private HttpClient getHttpClient() {

    return HttpClients.custom().build();
 }

}
