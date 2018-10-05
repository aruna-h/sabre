package com.bridgelabz.sabre.configuration;

import org.springframework.context.annotation.Bean;


import org.springframework.context.annotation.Configuration;


import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author bridgelabz
 * @since 12/July/2018 <br>
 *        <p>
 *        Entity for swagger configuration<br>
 *        </p>
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("Authorization").apiInfo(apiInfo()).select()
				.paths(PathSelectors.any()).build();
	}

	/*@Bean
	public PreConditions precondition() {
		return new PreConditions();
	}
*/
	/*@Bean
	SecurityConfiguration security() {
		return new SecurityConfiguration(null, null, null, null, "Token", ApiKeyVehicle.HEADER, "Authorization", ",");
	}*/

	@SuppressWarnings("deprecation")
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("TODO APPLICATION")
				.description("NOTE TAKING APP USING SPRING BOOT AND MONGODB").contact("arunakush6@gmail.com")
				.version("1.0").build();
	}

}