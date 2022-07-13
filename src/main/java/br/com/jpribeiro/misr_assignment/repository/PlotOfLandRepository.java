package br.com.jpribeiro.misr_assignment.repository;

import br.com.jpribeiro.misr_assignment.model.PlotOfLand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlotOfLandRepository extends JpaRepository<PlotOfLand, Integer> {

    @Query(nativeQuery = true,
            value = "SELECT " +
                    "CASE " +
                    "     WHEN SUM(p.time_slots) IS NULL " +
                    "          OR SUM(p.amount_of_water) IS NULL " +
                    "          OR SUM(p.amount_of_water) = 0 " +
                    "          THEN 0 " +
                    "     ELSE (CAST(SUM(p.time_slots) AS DOUBLE) / SUM(p.amount_of_water)) " +
                    "END " +
                    "FROM plot_of_land p " +
                    "WHERE p.area_type_id = :areaTypeId")
    double predictRatio(@Param("areaTypeId") Integer areaTypeId);

    @Query("SELECT p FROM PlotOfLand p JOIN FETCH p.areaType WHERE p.id = :id")
    PlotOfLand findPlotOfLand(@Param("id") Integer id);

    @Query("SELECT p FROM PlotOfLand p JOIN FETCH p.areaType WHERE p.areaType.id = :areaTypeId")
    List<PlotOfLand> findPlotsOfLandByAreaType(@Param("areaTypeId") Integer areaTypeId);

}
