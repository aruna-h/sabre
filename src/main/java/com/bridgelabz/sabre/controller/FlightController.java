package com.bridgelabz.sabre.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sun.misc.BASE64Encoder;

/**
 *@author aruna
 * @since 5/10/2018 <br>
 *        <p>
 *        entity to get the list of cheap flights to a certain destination
 *        <br>
 *        </p>
 */
@RestController
@RequestMapping("/flights")
public class FlightController {

	public static final Logger logger = LoggerFactory.getLogger(FlightController.class);
	static String REQ_ID = "IN";
	static String RESP_ID = "OUT";

	@Value("${clientId}")
	String clientId;
	@Value("${clientSecret}")
	String clientSecret;

	static String encodedAuthorization = "VmpFNk5uVndibUl4Wkc4NVkzZ3hjRFJrTVRwUVZVSk1TVU02UlZoVTpTVEJMTTBwMWRtdz0=";

	/**
	 * function to get the url and to get access to token
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	public ResponseEntity<String> gettoken() throws Exception {

		String PARAMS = "grant_type=client_credentials";
		URL url = new URL("https://api-crt.cert.havail.sabre.com/v2/auth/token");

		HttpURLConnection conn = (HttpsURLConnection) url.openConnection();

		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Authorization", "Basic " + encodedAuthorization);

		conn.setDoOutput(true);
		OutputStream os = conn.getOutputStream();
		os.write(PARAMS.getBytes());
		os.flush();
		os.close();

		System.out.println(conn);
		int responseCode = conn.getResponseCode();
		System.out.println(responseCode);
		System.out.println("GET Response Code :: " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			// print result
			System.out.println(response.toString());
		} else {
			System.out.println("POST request not worked");
		}

		return new ResponseEntity("connected to url and got token", HttpStatus.OK);
	}

	/**
	 * function to get the url and to get access to endpoint and get the results
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getendpoint", method = RequestMethod.GET)
	public String get_token() throws Exception {
		URL obj = new URL(
				"https://api-crt.cert.havail.sabre.com/v1/shop/flights/cheapest/fares/LAX?pointofsalecountry=US");
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setRequestProperty("Authorization",
				"Bearer T1RLAQIkkKDFIpjL3zqkq0JFxgURcSA1OhBhHgABcUfovaWu5t6Q/uoMAADAh7VnSWzTfTMbklDmOD56yIrQqwsiTkUZFeBmIQ8K+xgUGi+VhxhqyKrvCf7mNhdSZgbrjww2xvA8gtPku4bGenTxOLEnIgYQ7SCAWNVnBYNCRNcI23EUGgVj6Ppud5XT1xGAU9axHdMZz02a80L6hUDvUX05ILvlAU/s8RPf9mcU895XLvoGgLp8EyGBp7fHcHQAr1rh5u3Yho9M+jWuGgqYpqCp3SUes+AQ7PAJpCvCkB5k4U1ExMTIuAJ7Kppx");

		int responseCode = con.getResponseCode();
		System.out.println("POST Response Code :: " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			System.out.println(response.toString());
		} else {
			System.out.println("GET request not worked");
		}

		return "endpoint accessed successfully";
	}

}
