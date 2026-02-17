package com.roima.hrms.repository;

import com.roima.hrms.entities.EmployeeProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface OrgChartRepository extends JpaRepository<EmployeeProfile, Long> {


//    @Query(value = """
//            WITH RecursiveOrgCTE AS
//            (
//                SELECT *
//                FROM office.employee_profiles
//                WHERE manager_id IS NULL
//                UNION ALL
//
//                SELECT e.*
//                FROM office.employee_profiles e
//                JOIN RecursiveOrgCTE r ON e.manager_id = r.pk_employee_id
//            )
//            SELECT *
//            FROM RecursiveOrgCTE;
//            """, nativeQuery = true)
//    List<EmployeeProfile> getAllOrg();


//    @Query(value=" with Recursee as (select ep from EmployeeProfile ep where ep.manager is null union all " +
//            "select epp from EmployeeProfile epp join fetch Recursee re where epp.manager.id = re.id) select ree from Recursee ree")
//    List<EmployeeProfile> getAllOrgJPQL();
}
