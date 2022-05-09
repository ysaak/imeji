package ysaak.imeji.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ysaak.imeji.data.Wallpaper;
import ysaak.imeji.service.wallpaper.WallpaperService;

import javax.transaction.Transactional;
import java.util.List;

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
}
