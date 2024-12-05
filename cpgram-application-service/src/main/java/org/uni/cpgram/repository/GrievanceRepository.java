package org.uni.cpgram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.uni.cpgram.model.Grievance;

import java.util.List;
import java.util.Set;

@Repository
public interface GrievanceRepository extends JpaRepository<Grievance,String> {
    @Query("SELECT g.grievanceId FROM Grievance g ORDER BY g.grievanceId DESC LIMIT 1")
    String findTopByOrderByGrievanceIdDesc();


    List<Grievance> findAllByGrievanceIdIn(Set<String> grievanceIds);
}
