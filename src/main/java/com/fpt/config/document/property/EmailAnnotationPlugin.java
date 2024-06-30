package com.fpt.config.document.property;

import static springfox.bean.validators.plugins.Validators.annotationFromBean;

import java.util.Optional;


import jakarta.validation.constraints.Email;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import springfox.bean.validators.plugins.Validators;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;

@Component
@Order(Validators.BEAN_VALIDATOR_PLUGIN_ORDER)
public class EmailAnnotationPlugin implements ModelPropertyBuilderPlugin {

	@Override
	public boolean supports(DocumentationType delimiter) {
		// allow any documentation type like Swagger 1.2 and Swagger 2.
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void apply(ModelPropertyContext context) {
		Optional<Email> email = annotationFromBean(context, Email.class);
		if (email.isPresent()) {
			context.getBuilder().pattern(email.get().regexp());
			context.getBuilder().example("duynn8@fpt.com");
		}

	}
}