package com.example.myapp.domain.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

	private String path;
	private String message;
	private int statusCode;
	private LocalDateTime localDateTime;

}
