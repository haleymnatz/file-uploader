package com.uploader;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;


@Service
public class S3Utils {
	
	@Autowired
	private String bucketName;
	
	@Autowired
	private AmazonS3 s3Client;
	
	
	// Add throws IOException
	public void s3UploadFile(String keyName, MultipartFile file)  {
		
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(file.getSize());
		try {
		    s3Client.putObject(new PutObjectRequest(
		    		bucketName, keyName, file.getInputStream(), metadata));
		} catch (Exception e) {
		    System.err.println(e.getMessage());
		    System.exit(1);
		}
		System.out.println("Done!");
    }
}
