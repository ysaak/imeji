package ysaak.imeji.db.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ysaak.imeji.data.Wallpaper;

@Repository
public interface WallpaperRepository extends CrudRepository<Wallpaper, String>, JpaSpecificationExecutor<Wallpaper> {
    @Query(value = "SELECT * FROM WALLPAPER w ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Wallpaper getRandom();

}
