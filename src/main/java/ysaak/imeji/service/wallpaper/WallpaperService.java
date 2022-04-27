package ysaak.imeji.service.wallpaper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ysaak.imeji.data.Wallpaper;
import ysaak.imeji.data.WallpaperColor;
import ysaak.imeji.db.repository.WallpaperColorRepository;
import ysaak.imeji.db.repository.WallpaperRepository;
import ysaak.imeji.utils.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class WallpaperService {

    private final WallpaperRepository wallpaperRepository;
    private final WallpaperColorRepository wallpaperColorRepository;

    private final SearchQueryParser searchQueryParser;

    @Autowired
    public WallpaperService(WallpaperRepository wallpaperRepository, WallpaperColorRepository wallpaperColorRepository) {
        this.wallpaperRepository = wallpaperRepository;
        this.wallpaperColorRepository = wallpaperColorRepository;

        this.searchQueryParser = new SearchQueryParser(20.);
    }

    public List<Wallpaper> findAll() {
        return CollectionUtils.toList(wallpaperRepository.findAll());
    }

    public Wallpaper save(final Wallpaper wallpaper) {
        final Wallpaper savedWallpaper = wallpaperRepository.save(wallpaper);

        Set<WallpaperColor> palette = wallpaper.getPalette();
        palette.forEach(c -> c.setWallpaper(savedWallpaper));
        palette = CollectionUtils.toSet(wallpaperColorRepository.saveAll(palette));

        savedWallpaper.setPalette(palette);

        return savedWallpaper;
    }

    public Optional<Wallpaper> get(String id) {
        return wallpaperRepository.findById(id);
    }

    public Wallpaper getRandom() {
        return wallpaperRepository.getRandom();
    }

    public List<Wallpaper> search(String query) {
        final Specification<Wallpaper> spec = this.searchQueryParser.parse(query);
        return wallpaperRepository.findAll(spec, Sort.by(Sort.Direction.DESC, "uploadDate"));
    }
}
