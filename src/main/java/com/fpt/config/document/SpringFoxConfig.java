//package com.fpt.config.document;
//
//import java.util.Collections;
//
//import com.fpt.Application;
//import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//
//import com.fpt.config.document.property.EmailAnnotationPlugin;
//
//import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;
//
//@SuppressWarnings("deprecation")
//@Configuration
//@EnableSwagger2WebMvc
//@Import({ BeanValidatorPluginsConfiguration.class, SpringDataRestConfiguration.class })
//public class SpringFoxConfig{
//
//	@Bean
//	public Docket api() {
//		return new Docket(DocumentationType.SWAGGER_2).select()
//				.apis(RequestHandlerSelectors.basePackage("com.fpt.controller"))
//				.paths(PathSelectors.any())
//				.build()
//				.apiInfo(apiInfo());
//	}
//
//	private ApiInfo apiInfo() {
//	    return new ApiInfo(
//	      "CPMP API", // title
//	      "This is API description for CPNP", // description
//	      "1.0", // version
//	      "Terms of service", // termsOfServiceUrl
//	      new Contact("EDP01 Team", "https://www.facebook.com/VTIAcademy/", "info@vtiacademy.edu.vn"), // contact
//	      "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0.html", // license
//	      Collections.emptyList()); // vendors
//	}
//
//	@Bean
//	public EmailAnnotationPlugin emailPlugin() {
//		return new EmailAnnotationPlugin();
//	}
//
//}