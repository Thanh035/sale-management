package com.example.demo.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.example.demo.domain.dto.admin.account.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.dto.UserRegistrationRequest;
import com.example.demo.services.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1.0")
@Slf4j
public class AccountController {

	private final UserService userService;

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public void registerAccount(@Valid @RequestBody UserRegistrationRequest request) {
		userService.registerUser(request);
	}

	@GetMapping("/authenticate")
	public String isAuthenticated(HttpServletRequest request) {
		log.debug("REST request to check if the current user is authenticated");
		return request.getRemoteUser();
	}

	@GetMapping("/activate")
	public void activateAccount(@RequestParam(value = "key") String key) {
		userService.activateRegistration(key);
	}

	@GetMapping("/account")
	public AccountDTO getAccount() {
		return userService.getUserFromAuthentication();
	}

	@GetMapping("/securityInfo")
	public SecurityInfoDTO getSecurity() {
		return userService.getSecurityInfoFromAuthentication();
	}

	@PutMapping("/account")
	public void saveAccount(@Valid @RequestBody AccountUpdateDTO updateRequest) {
		userService.updateUser(updateRequest);
	}

	@PostMapping(path = "/account/change-password")
	public void changePassword(@RequestBody PasswordChangeDTO passwordChangeDTO) {
		userService.changePassword(passwordChangeDTO.getCurrentPassword(), passwordChangeDTO.getNewPassword());
	}

	@PostMapping(path = "/account/reset-password/init")
	public void requestPasswordReset(@RequestBody String mail) {
		userService.requestPasswordReset(mail);
	}

	@PostMapping(path = "/account/reset-password/finish")
	public void finishPasswordReset(@RequestBody KeyAndPasswordDTO keyAndPassword) {
		userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey());
	}

	@GetMapping(value = "account/profile-image", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getUserProfileImage() {
		return userService.getUserProfileImage();
	}

	@PostMapping(value = "account/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void uploadUserProfileImage(@RequestParam("file") MultipartFile file) {
		userService.uploadUserProfileImage(file);

	}
}
