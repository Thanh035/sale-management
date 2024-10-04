package com.example.myapp.services;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StorageService {

	@Value("${firebase.storage.bucket}")
	private String bucketName;

	private final Storage storage;

	public StorageService() throws IOException {
		InputStream serviceAccount = getClass().getResourceAsStream("/google-services.json");
		GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
		storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
	}

	@SuppressWarnings("deprecation")
	@Async
	public String uploadImage(String path, MultipartFile file) throws IOException {
		String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

		String filePath = path + "/" + fileName;

		BlobInfo blobInfo = storage.create(
				BlobInfo.newBuilder(bucketName, filePath).setContentType(file.getContentType()).build(),
				file.getInputStream());

		String imageUrl = "https://firebasestorage.googleapis.com/v0/b/" + bucketName + "/o/"
				+ blobInfo.getName().replace("/", "%2F") + "?generation=" + blobInfo.getGeneration() + "&alt=media";
		return imageUrl;
	}

	public void putObject(String bucketName, String key, byte[] file) {
		storage.create(BlobInfo.newBuilder(bucketName, key).build(), file);
	}

	public byte[] getObject(String bucketName, String key) {
		Blob blob = storage.get(BlobId.of(bucketName, key));
		try {
			return blob.getContent();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Async
	public void deleteObject(String bucketName, String key) {
		storage.delete(bucketName, key);
	}

}
