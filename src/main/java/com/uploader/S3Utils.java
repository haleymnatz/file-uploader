package com.uploader;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;


@Service
public class S3Utils {
	
	@Value("${bucketName}")
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
	
	public List<String> s3ListObjects() {
		ListObjectsV2Result objectList = s3Client.listObjectsV2(bucketName);
		List<S3ObjectSummary> objects = objectList.getObjectSummaries();
		List<String> objectKeys = new ArrayList<String>();
		for (S3ObjectSummary os: objects) {
		    objectKeys.add(os.getKey());
		}
		return objectKeys;
	}
}
