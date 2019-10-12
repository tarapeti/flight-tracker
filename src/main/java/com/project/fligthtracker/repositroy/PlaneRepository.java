package com.project.fligthtracker.repositroy;

import com.project.fligthtracker.model.Planes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaneRepository extends JpaRepository<Planes, Integer> {
    @Override
    <S extends Planes> S save(S entity);

    @Override
    <S extends Planes> List<S> saveAll(Iterable<S> entities);

    Planes findByCompanyName(String companyName);

    @Override
    List<Planes> findAll();

    @Override
    void delete(Planes entity);
}
