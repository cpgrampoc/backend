package org.uni.cpgram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uni.cpgram.model.OnboardDemo;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface OnboardDemoRepository extends JpaRepository<OnboardDemo, String> {


  boolean existsByDescription(String description);

  Optional<OnboardDemo> findIdByDescription(String description);

  List<OnboardDemo> findAllByParent(String parentId);
}