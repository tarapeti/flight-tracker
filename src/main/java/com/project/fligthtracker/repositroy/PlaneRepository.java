package com.project.fligthtracker.repositroy;

import com.project.fligthtracker.model.Planes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaneRepository extends JpaRepository<Planes, Integer> {
    @Override
    <S extends Planes> S save(S entity);

    Planes findByCompanyName(String companyName);
}
