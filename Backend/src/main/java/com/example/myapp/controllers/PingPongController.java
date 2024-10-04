package com.example.myapp.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingPongController {

	private static int COUNTER = 0;

	public static class PingPong {
		private final String result;

		public PingPong(String result) {
			this.result = result;
		}

		public String getResult() {
			return result;
		}
	}

	@RequestMapping(value = "/ping", method = RequestMethod.OPTIONS)
	public PingPong getPingPong() {
		return new PingPong(String.format("Pong: %s", ++COUNTER));
	}

}
