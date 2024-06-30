package com.fpt.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fpt.entity.StudentProject;
import com.fpt.entity.User;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fpt.dto.ProjectDTO;
import com.fpt.dto.ProjectDetailDTO;
import com.fpt.dto.ProjectForSelectionDTO;
import com.fpt.entity.Project;
import com.fpt.form.project.AddProjectForm;
import com.fpt.form.project.FilterProjectForm;
import com.fpt.form.project.UpdateProjectForm;
import com.fpt.service.ProjectService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/projects")
@Validated
public class ProjectController extends BaseController {

	@Autowired
	private ProjectService service;

	@GetMapping
	public Page<ProjectDTO> getAllProjects(Pageable pageable,
			@RequestParam(value = "q", required = false) String search,
			FilterProjectForm filterForm) {
		Page<Project> entityPage = service.getAllProjects(pageable, search, filterForm);
		return convertEntityPageToDtoPage(entityPage, pageable, ProjectDTO.class);
	}
	
	@GetMapping("/selection")
	public List<ProjectForSelectionDTO> getAllProjectsForSelection() {
		List<Project> entities = service.getAllProjectsForSelection();
		return convertListEntityToListDto(entities, ProjectForSelectionDTO.class);
	}

	@GetMapping(value = "/{id}")
	public ProjectDetailDTO getProjectDetails(@PathVariable(name = "id") Long id) {
		Project entityProject = service.findById(id);
		return modelMapper.map(entityProject, ProjectDetailDTO.class);
	}

	@PostMapping()
	public String addProject(@RequestBody @Valid AddProjectForm addProjectForm) {
		service.addProject(addProjectForm);
		return "Add project successfully";
	}

	@PutMapping(value = "/{id}")
	public String updateProject(@PathVariable(name = "id") Long id, @RequestBody @Valid UpdateProjectForm projectForm) {
		projectForm.setId(id);
		service.updateProjectById(projectForm);
		return "Update project successfully";
	}

	@GetMapping(value = "/code/exist")
	public Boolean isSettingNameExists(String code) {
		return service.isProjectExistsByCode(code);
	}

	@PutMapping(value = "/update-status/{id}")
	public String updateStatus(@PathVariable(name = "id") Long id) {
		service.updateStatus(id);
		return "Update project successfully";
	}

//	@PostMapping(value = "/import")
//	public void importListProject(@RequestParam(name = "file") MultipartFile file) throws IOException {
//		List<AddProjectForm> form = new ArrayList<>();
//
//		InputStream excelIs = file.getInputStream();
//		Workbook workbook = WorkbookFactory.create(excelIs);
//		Sheet firstSheet = workbook.getSheetAt(0);
//		Iterator<Row> rows = firstSheet.rowIterator();
//		rows.next();
//		while (rows.hasNext()) {
//			Row currentRow = rows.next();
//			List<Long> teamMemberId = new ArrayList<>();
//			for (String rawTeamMemberId : currentRow.getCell(9).getStringCellValue().split(", ")) {
//				teamMemberId.add(Long.parseLong(rawTeamMemberId));
//			}
//			form.add(new AddProjectForm(Long.getLong(currentRow.getCell(0).getStringCellValue()), // class id
//					currentRow.getCell(1).getStringCellValue(), // group name
//					currentRow.getCell(2).getStringCellValue(), // project code
//					currentRow.getCell(3).getStringCellValue(), // english name
//					currentRow.getCell(4).getStringCellValue(), // vietnamese name
//					Long.getLong(currentRow.getCell(5).getStringCellValue()), // mentor id
//					Long.getLong(currentRow.getCell(6).getStringCellValue()), // co-mentor id
//					Long.getLong(currentRow.getCell(7).getStringCellValue()), // leader id
//					currentRow.getCell(8).getStringCellValue(), currentRow.getCell(9).getStringCellValue(), // note
//					teamMemberId));
//		}
//		if (!form.isEmpty()) {
//			service.importProjectByExcelFile(form);
//		}
//	}

	@GetMapping(value = "/assigned-projects")
	public List<ProjectDTO> getAssignedProjects() {
		List<Project> entities = service.getAssignedProjects();
		return convertListEntityToListDto(entities, ProjectDTO.class);
	}

}
