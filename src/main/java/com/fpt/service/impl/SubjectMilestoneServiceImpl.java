package com.fpt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.entity.Subject;
import com.fpt.entity.SubjectMilestone;
import com.fpt.form.subjectmilestone.AddSubjectMilestoneForm;
import com.fpt.form.subjectmilestone.UpdateSubjectMilestoneForm;
import com.fpt.repository.SubjectMilestoneRepository;
import com.fpt.repository.SubjectRepository;
import com.fpt.service.SubjectMilestoneService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubjectMilestoneServiceImpl implements SubjectMilestoneService {

	@Autowired
	private SubjectMilestoneRepository subjectMilestoneRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Override
	public List<SubjectMilestone> findAllSubjectMilestone(Long subjectId) {
		return subjectMilestoneRepository.findAllBySubjectId(subjectId);
	}

	@Override
	public void addSubjectMilestone(AddSubjectMilestoneForm addSubjectMilestoneForm, Long subjectId) {
		SubjectMilestone subjectMilestone = new SubjectMilestone();
		Subject subject = subjectRepository.findById(subjectId).get();
		subjectMilestone.setSubject(subject);
		subjectMilestone.setDuration(addSubjectMilestoneForm.getDuration());
		subjectMilestone.setStep(addSubjectMilestoneForm.getStep());
		subjectMilestone.setTitle(addSubjectMilestoneForm.getTitle());
		subjectMilestoneRepository.save(subjectMilestone);
	}

	@Override
	public void updateSubjectMilestone(UpdateSubjectMilestoneForm updateSubjectMilestoneForm, Long id, Long subjectId) {
		SubjectMilestone subjectMilestone = new SubjectMilestone();
		Subject subject = subjectRepository.findById(subjectId).get();
		subjectMilestone.setId(id);
		subjectMilestone.setSubject(subject);
		subjectMilestone.setDuration(updateSubjectMilestoneForm.getDuration());
		subjectMilestone.setStep(updateSubjectMilestoneForm.getStep());
		subjectMilestone.setTitle(updateSubjectMilestoneForm.getTitle());
		subjectMilestoneRepository.save(subjectMilestone);
	}

	@Override
	public void deleteSubject(Long id) {
		subjectMilestoneRepository.deleteById(id);
	}

	@Override
	public boolean isSubjectMilestoneExistsByTitle(String title) {
		return subjectMilestoneRepository.existsByTitle(title);
	}

	@Override
	public boolean isSubjectMilestoneExistsByID(Long id) {
		return subjectMilestoneRepository.existsById(id);
	}
}
