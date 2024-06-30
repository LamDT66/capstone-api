package com.fpt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.entity.Milestone;
import com.fpt.repository.MilestoneRepository;
import com.fpt.service.MilestoneService;
import com.fpt.service.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MilestoneServiceImpl implements MilestoneService {

	@Autowired
	private MilestoneRepository milestoneRepository;

	@Autowired
	private UserService userService;
	
	@Override
	public List<Milestone> getMilestoneByStudent() {
		return milestoneRepository.getMilestoneByStudent(userService.getCurrentLoginUser().getId());
	}
	
	@Override
	public List<Milestone> getMilestoneByClassIdOrderByStartDate(Long id) {
		return milestoneRepository.getMilestoneByClassIdOrderByStartDate(id);
	}

	@Override
	public List<Milestone> getMilestoneByProjectIdOrderByStartDate(Long id) {
		return milestoneRepository.getMilestoneByProjectIdOrderByStartDate(id);
	}

	@Override
	public boolean isMilestoneExistsByID(Long id) {
		return milestoneRepository.existsById(id);
	}

	@Override
	public void createMilestone(Milestone milestone) {
		milestoneRepository.save(milestone);
	}

	@Override
	public void updateMilestone(Milestone milestone) {
		milestoneRepository.save(milestone);
	}

	@Override
	public void deleteMilestone(Long id) {
		milestoneRepository.deleteById(id);
	}

	@Override
	public Milestone getMilestoneById(Long id) {
		return milestoneRepository.findById(id).get();
	}

	@Override
	public boolean isMilestoneExistsByTitle(String title) {
		return milestoneRepository.existsByTitle(title);
	}
}
