package com.fpt.service;

public interface EmailService {
	void sendEmail(String to, String subject, String body);

	void senEmailMultipleRecipients(String[] to, String subject, String body);
}
