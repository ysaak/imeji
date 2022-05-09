package ysaak.imeji.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ysaak.imeji.data.Tag;
import ysaak.imeji.data.Wallpaper;
import ysaak.imeji.data.WallpaperColor;
import ysaak.imeji.service.tag.TagService;
import ysaak.imeji.service.wallpaper.WallpaperService;
import ysaak.imeji.view.dto.WallpaperEditDto;
import ysaak.imeji.view.dto.WallpaperViewDto;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@Transactional
@RequestMapping(path = "/w/")
public class WallpaperController extends AbstractController {
    private final WallpaperService wallpaperService;
    private final TagService tagService;

    @Autowired
    public WallpaperController(WallpaperService wallpaperService, TagService tagService) {
        this.wallpaperService = wallpaperService;
        this.tagService = tagService;
    }

    @GetMapping(path = "{id}")
    public String viewAction(@PathVariable("id") final String id, Model model) {
        Wallpaper wallpaper = wallpaperService.get(id).orElseThrow(this::notFound);
        model.addAttribute("wallpaper", mapToViewDto(wallpaper));
        return "view";
    }

    @PostMapping(path = "{id}"/*, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}*/)
    public String updateAction(@ModelAttribute final WallpaperEditDto editDto) {
        Wallpaper wallpaper = wallpaperService.get(editDto.getId()).orElseThrow(this::notFound);

        final Set<Tag> tags = new HashSet<>();
        if (editDto.getTags() != null) {
            List<String> strTags = Arrays.asList(editDto.getTags().split(" "));
            tags.addAll(
                    tagService.getOrCreate(strTags)
            );
        }

        wallpaper.setTags(tags);
        wallpaperService.save(wallpaper);

        return redirect("/w/" + wallpaper.getId());
    }

    private WallpaperViewDto mapToViewDto(Wallpaper wallpaper) {
        List<WallpaperViewDto.Color> palette = wallpaper.getPalette().stream()
                .map(this::mapColor)
                .sorted(Comparator.comparing(WallpaperViewDto.Color::getOrder))
                .collect(Collectors.toList());

        List<WallpaperViewDto.Tag> tags = wallpaper.getTags().stream()
                .sorted(Comparator.comparing(Tag::getName))
                .map(this::mapTag)
                .collect(Collectors.toList());

        return new WallpaperViewDto(
                wallpaper.getId(),
                wallpaper.getWidth(),
                wallpaper.getHeight(),
                wallpaper.getFileSize(),
                palette,
                tags);
    }

    private WallpaperViewDto.Color mapColor(WallpaperColor wallpaperColor) {
        String rgbHex = String.format("%02x%02x%02x", wallpaperColor.getRed(), wallpaperColor.getGreen(), wallpaperColor.getBlue());
        return new WallpaperViewDto.Color(
                wallpaperColor.getOrder(),
                rgbHex
        );
    }

    private WallpaperViewDto.Tag mapTag(Tag tag) {
        return new WallpaperViewDto.Tag(tag.getName(), tag.getType().name());
    }
}
