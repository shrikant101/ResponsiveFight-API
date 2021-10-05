package utils;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Utils {

	public static RequestSpecification req;

	public RequestSpecification requestSpecification() throws IOException {
		if (req == null) {
		
			PrintStream stream = new PrintStream(new FileOutputStream("logging.txt"));
			req = new RequestSpecBuilder().setBaseUri(getGlobalValues("BASE_URL")).setContentType(ContentType.JSON)
					.addHeader("Authorization", getGlobalValues("TOKENVAL"))

					.addFilter(RequestLoggingFilter.logRequestTo(stream))
					.addFilter(ResponseLoggingFilter.logResponseTo(stream)).build();
		}
		return req;
	}

	public ResponseSpecification responseSpecification() throws FileNotFoundException {
		ResponseSpecification responseSpecBuilder = new ResponseSpecBuilder()
				.expectContentType(ContentType.JSON).build();
		return responseSpecBuilder;

	}

	public ResponseSpecification responseSpecificationNoContent() throws FileNotFoundException {
		ResponseSpecification responseSpecBuilder = new ResponseSpecBuilder()
				.build();
		return responseSpecBuilder;

	}
	public static String getGlobalValues(String key) throws IOException {

		Properties properties = new Properties();
		String basePath = Utils.class.getResource("/").getPath();
		InputStream in = new FileInputStream(basePath + "global.properties");
		properties.load(in);

		return properties.getProperty(key);
	}

}