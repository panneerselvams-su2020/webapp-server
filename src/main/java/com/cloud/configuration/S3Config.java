package com.cloud.configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3Config {
        
          
        @Value("${s3.region}")
        private String region;
          
        @Bean
        public AmazonS3 getAmazonS3Cient() {
        	return AmazonS3ClientBuilder.defaultClient();
        }
}
