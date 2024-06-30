package com.fpt.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import org.springframework.core.io.FileSystemResource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "api/v1/files")
@Validated
public class FileController extends BaseController {

	@GetMapping(value = "/templates/studentTemplateFile")
	public void getStudentTemplateFile(HttpServletResponse response) throws IOException {
		File file = new File("templates//StudentTemplate.xlsx");

		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());

		ServletOutputStream outputStream = response.getOutputStream();
		BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));

		byte[] buffer = new byte[8192]; // 8KB
		int bytesRead = -1;

		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, bytesRead);
		}

		inputStream.close();
		outputStream.close();
	}

	@PostMapping(value = "/reports")
	public String upLoadReport(@RequestParam(name = "file") MultipartFile file) throws IOException {

		String originalFileName = file.getOriginalFilename();
		String nameFile = getNameFile(originalFileName) + "__" + new Date().getTime() + "." + getFormatFile(originalFileName);
		String path = new FileSystemResource("").getFile().getAbsolutePath() + "\\reports\\" + nameFile;
		// write file
		file.transferTo(new File(path));

		return nameFile;
	}
	
	private String getNameFile(String input) {
		String[] results = input.split("\\.");
		return results[0];
	}
	
	private String getFormatFile(String input) {
		String[] results = input.split("\\.");
		return results[results.length - 1];
	}

	@GetMapping(value = "/reports")
	public void getReportFile(
			@RequestParam String nameFile,
			HttpServletResponse response) throws IOException {

		File file = new File("reports//" + nameFile);

		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=" + (file.getName()).split("__")[0] + "." + getFormatFile(file.getName()));

		ServletOutputStream outputStream = response.getOutputStream();
		BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));

		byte[] buffer = new byte[8192]; // 8KB
		int bytesRead = -1;

		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, bytesRead);
		}

		inputStream.close();
		outputStream.close();
	}

}
