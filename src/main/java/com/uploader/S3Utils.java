package com.uploader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
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
	
	public S3ObjectInputStream s3DownloadObject(String keyName) {
		
//		try {
		    S3Object o = s3Client.getObject(bucketName, keyName);
		    S3ObjectInputStream s3is = o.getObjectContent();
		    return s3is;
//		    FileOutputStream fos = new FileOutputStream(new File(keyName));
//		    System.out.println(s3is);
//		    byte[] read_buf = new byte[1024];
//		    int read_len = 0;
//		    while ((read_len = s3is.read(read_buf)) > 0) {
//		        fos.write(read_buf, 0, read_len);
//		    }
//		    s3is.close();
//		    fos.close();
//		    System.out.println("Downloaded!");
//		} catch (AmazonServiceException e) {
//		    System.err.println(e.getErrorMessage());
//		    System.exit(1);
//		} catch (FileNotFoundException e) {
//		    System.err.println(e.getMessage());
//		    System.exit(1);
//		} catch (IOException e) {
//		    System.err.println(e.getMessage());
//		    System.exit(1);
//		}
	}
	
	public void s3DeleteObject(String keyName) {
		try {
		    s3Client.deleteObject(bucketName, keyName);
		} catch (AmazonServiceException e) {
		    System.err.println(e.getErrorMessage());
		    System.exit(1);
		}
	}
}
