package ysaak.imeji.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import ysaak.imeji.data.Wallpaper;
import ysaak.imeji.data.WallpaperColor;
import ysaak.imeji.dto.WallpaperViewDto;
import ysaak.imeji.service.wallpaper.WallpaperService;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Transactional
public class BrowseController {
    private final WallpaperService wallpaperService;

    @Autowired
    public BrowseController(WallpaperService wallpaperService) {
        this.wallpaperService = wallpaperService;
    }

    @GetMapping(path = "/")
    public String indexAction(Model model) {
        List<Wallpaper> wallpapers = wallpaperService.findAll();
        model.addAttribute("wallpapers", wallpapers);
        return "index";
    }

    @GetMapping(path = "/w/{id}")
    public String viewAction(@PathVariable("id") final String id, Model model) {
        Wallpaper wallpaper = wallpaperService.get(id).orElseThrow(this::notFound);
        model.addAttribute("wallpaper", mapToViewDto(wallpaper));
        return "view";
    }

    @GetMapping(path = "/random")
    public String randomAction() {
        Wallpaper wallpaper = wallpaperService.getRandom();
        return "forward:/wallpapers/full/" + wallpaper.getId() + ".png";
    }

    @GetMapping(path = "/search")
    public String searchAction(@RequestParam("q") final String query, Model model) {
        List<Wallpaper> wallpapers = wallpaperService.search(query);
        model.addAttribute("wallpapers", wallpapers);
        return "index";
    }

    protected ResponseStatusException notFound() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    private WallpaperViewDto mapToViewDto(Wallpaper wallpaper) {
        List<WallpaperViewDto.Color> palette = wallpaper.getPalette().stream()
                .map(this::mapColor)
                .sorted(Comparator.comparing(WallpaperViewDto.Color::getOrder))
                .collect(Collectors.toList());

        return new WallpaperViewDto(
                wallpaper.getId(),
                wallpaper.getWidth(),
                wallpaper.getHeight(),
                wallpaper.getFileSize(),
                palette
        );
    }

    private WallpaperViewDto.Color mapColor(WallpaperColor wallpaperColor) {
        String rgbHex = String.format("%02x%02x%02x", wallpaperColor.getRed(), wallpaperColor.getGreen(), wallpaperColor.getBlue());
        return new WallpaperViewDto.Color(
                wallpaperColor.getOrder(),
                rgbHex
        );
    }
}
