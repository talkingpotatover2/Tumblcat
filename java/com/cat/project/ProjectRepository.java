package com.cat.project;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cat.project.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

	@Query("select p from Project p where p_cate = :pCate")
	List<Project> findAllByCate(@Param("pCate") String pCate);
	
	// 엔티티 기준으로 작성
	//@Param("kw") 는 쿼리에서 :kw로 참조
    @Query("select "
            + "distinct p "
            + "from Project p " 
            + "left outer join Image i1 on p.imgIdR=i1.imgId "
            + "where "
            + "   p.pName like %:kw% "
            + "   or p.pCreator like %:kw% "
            + "   or p.pDesc like %:kw% "
            + "   or p.pCate like %:kw% "
            + "   or i1.imgDesc like %:kw% "
            )
    List<Project> findAllByKeyword(@Param("kw") String kw);
    
    @Query(value="select * "
    		+ "from project "
    		+ "where p_id in ("
    		+ "	select project_p_id "
    		+ "from project_liker "
    		+ "group by project_p_id "
    		+ "order by count(*) desc "
    		+ ");",nativeQuery = true)
    List<Project> findAllByLike();
}
