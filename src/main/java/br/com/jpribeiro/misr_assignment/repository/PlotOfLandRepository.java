package br.com.jpribeiro.misr_assignment.repository;

import br.com.jpribeiro.misr_assignment.model.PlotOfLand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlotOfLandRepository extends JpaRepository<PlotOfLand, Integer> {

    /**
     * This SQL calculates the ratio between sum of time slots and the sum of amount of water for all
     * plots of area that have a specific Area Type.
     *
     * Doing this calculation on the database is more efficient than querying all records and doing the
     * calculation on the Java side.
     *
     * If the sum of time slots is null, or the sum of amount of water is null or zero, the function
     * returns 0 directly - this is useful when the user has created a new Area Type, but hasn't created
     * any Plots of Land for it yet.
     *
     * @param areaTypeId
     * @return
     */
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
