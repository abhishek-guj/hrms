package com.roima.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.roima.hrms.entities.TravelDocumentType;

public interface TravelDocumentTypeRepository extends JpaRepository<TravelDocumentType, Long> {
        @Query("update TravelDocumentType t set t.isDeleted = true where t.id = :id")
        boolean softDeleteById(Long id);

        @Query("select t from TravelDocumentType t where t.isDeleted = false or t.isDeleted is NULL")
        List<TravelDocumentType> findAll();

        @Query("""
                        select count(t)>0
                        from TravelDocumentType t
                        where (t.isDeleted = false
                        or t.isDeleted is NULL)
                        and t.name = :name
                                """)
        boolean existsByName(
                        String name);
}