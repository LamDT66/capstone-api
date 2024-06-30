package com.fpt.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fpt.entity.Milestone;

@Repository
public interface MilestoneRepository extends JpaRepository<Milestone, Long>, JpaSpecificationExecutor<Milestone> {

	@Query(value = "SELECT 	m.* "
				 + "FROM 	milestone m "
				 + "JOIN	student_project sp ON sp.project_id = m.project_id "
				 + "WHERE 	sp.student_id = :studentId", nativeQuery = true)
	List<Milestone> getMilestoneByStudent(@Param("studentId") Long studentId);
	
    @Query(value = "select * from milestone m where m.class_id = :id order by m.start_date", nativeQuery = true)
    List<Milestone> getMilestoneByClassIdOrderByStartDate(@Param("id") Long id);

    @Query(value = "SELECT * FROM milestone m where m.project_id = :id order by m.start_date", nativeQuery = true)
    List<Milestone> getMilestoneByProjectIdOrderByStartDate(@Param("id") Long id);

    boolean existsByTitle(String title);
    
    @Query(value="select IF(count(1)> 0, 'true', 'false') from milestone WHERE title = :title AND project_id= :projectId", nativeQuery = true)
    boolean existsByTitle(
    		@Param(value="title") String title, 
    		@Param(value="projectId") Long projectId);

    @Query(value = "select * from milestone m where m.project_id = :id", nativeQuery = true)
    Page<Milestone> findAllByProjectId(@Param("id") Long id, Pageable pageable);
}
