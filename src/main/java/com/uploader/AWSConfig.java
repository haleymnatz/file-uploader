package com.uploader;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AWSConfig {
	
	@Bean
	public AmazonS3 s3Client() {
		return AmazonS3ClientBuilder.defaultClient();
	}
	
	@Bean
	public String bucketName() {
		return "mnatzaganian-strongkey-demo";
	}
}
