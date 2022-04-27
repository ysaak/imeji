package ysaak.imeji.db.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import ysaak.imeji.data.WallpaperColor;

import java.util.List;

@Repository
public interface WallpaperColorRepository extends CrudRepository<WallpaperColor, String> {
    @Query(
            value = "SELECT WACO_WALL_ID" +
                    "FROM WALLPAPER_COLOR wc" +
                    "WHERE CALCULATE_COLOR_DISTANCE(:cieL, :cieA, :cieB, wc.waco_cie_l, wc.waco_cie_a, wc.waco_cie_b) <= :threshold",
            nativeQuery = true
    )
    List<String> findSimilar(
            @Param("cieL") double cieL,
            @Param("cieA") double cieA,
            @Param("cieB") double cieB,
            @Param("threshold") double threshold
    );
}
