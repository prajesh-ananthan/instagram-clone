package com.airasiabig.config;

import org.jinstagram.auth.InstagramAuthService;
import org.jinstagram.auth.oauth.InstagramService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Prajesh Ananthan
 *         Created on 10/8/2017.
 */
@Configuration
public class CommonBeanConfig {
  @Value("${client.id}")
  private String clientId;
  @Value("${client.secret}")
  private String clientSecret;
  @Value("${callback.url}")
  private String callbackUrl;

  @Bean
  public InstagramService instagramService() {
    return new InstagramAuthService()
        .apiKey(clientId)
        .apiSecret(clientSecret)
        .callback(callbackUrl)
        .build();
  }
}
