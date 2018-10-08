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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.sabre.dto.ResponseDto;

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
    
	ResponseDto response = new ResponseDto();
	/**
	 * function to get the url and to get access to token
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	public ResponseEntity<ResponseDto> gettoken() throws Exception {

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
		String token = null;

		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;

			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			// print result
		 token=response.toString();
			System.out.println(response.toString());
		} else {
			System.out.println("POST request not worked");
		}

		response.setStatus(200);
		response.setMessage("connected to url and got token"+token);
		return new ResponseEntity(response, HttpStatus.OK);
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
				"Bearer T1RLAQLnjxLnhSXd3spJ/w+Ij8u66C9B5RDu9W3OuS6cHRpxWLOhQSD6AADAQGzSVcD34FCiGVMIXGBEZFM0Xt17gr0MvFiyYHCGuZ4zvVL+mK1dAfvSwVeSsl8t+EuwO+RikMXpNrtatjuYEwcXDhaNaX86cwJTG2rKHv06F0JNrKKV406Ykfm8xA6ncdkY8gb6B95xVZbGBZq0eD+L99cZHyP/EWn4fha0/KEZoWld597VDITg/kD66OfGjsI6Zbf+uJ7GH2TfX9leKB+y//wjeiKGVhbYsTqgv3nirnfBJCx8C9TX+goXFu/A" 
				);

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
