package com.sagar.lahade.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sagar.lahade.jobportal.entity.IRecruiterJobs;
import com.sagar.lahade.jobportal.entity.JobPostActivity;

import java.time.LocalDate;
import java.util.List;

public interface JobPostActivityRepository extends JpaRepository<JobPostActivity, Integer> {

    @Query(value = " SELECT COUNT(s.user_id) as totalCandidates, j.job_post_id, j.job_title, l.id as locationId, l.city, l.state, l.country, c.id as companyId, c.name " +
            " FROM job_post_activity j " +
            " INNER JOIN job_location l ON j.job_location_id = l.id " +
            " INNER JOIN job_company c ON j.job_company_id = c.id " +
            " LEFT JOIN job_seeker_apply s ON s.job = j.job_post_id " +
            " WHERE j.posted_by_id = :recruiter " +
            " GROUP BY j.job_post_id, j.job_title, l.id, l.city, l.state, l.country, c.id, c.name", nativeQuery = true)
    List<IRecruiterJobs> getRecruiterJobs(@Param("recruiter") int recruiter);

    @Query(value = "SELECT j.* FROM job_post_activity j " +
            "INNER JOIN job_location l ON j.job_location_id = l.id " +
            "WHERE j.job_title ILIKE %:job% " +
            "AND (l.city ILIKE %:location% " +
            "OR l.country ILIKE %:location% " +
            "OR l.state ILIKE %:location%) " +
            "AND j.job_type = ANY(ARRAY[:type]::varchar[]) " +
            "AND j.remote = ANY(ARRAY[:remotes]::varchar[])", nativeQuery = true)
    List<JobPostActivity> searchWithoutDate(@Param("job") String job,
                                            @Param("location") String location,
                                            @Param("remotes") String[] remote,
                                            @Param("type") String[] type);

    @Query(value = "SELECT j.* FROM job_post_activity j " +
            "INNER JOIN job_location l ON j.job_location_id = l.id " +
            "WHERE j.job_title ILIKE %:job% " +
            "AND (l.city ILIKE %:location% " +
            "OR l.country ILIKE %:location% " +
            "OR l.state ILIKE %:location%) " +
            "AND j.job_type = ANY(ARRAY[:types]::varchar[]) " +
            "AND j.remote = ANY(ARRAY[:remotes]::varchar[]) "+
            "AND j.posted_date >= :date", nativeQuery = true)
    List<JobPostActivity> search(@Param("job") String job,
                                 @Param("location") String location,
                                 @Param("remotes") String[] remote,
                                 @Param("types") String[] type,
                                 @Param("date") LocalDate searchDate);
}
