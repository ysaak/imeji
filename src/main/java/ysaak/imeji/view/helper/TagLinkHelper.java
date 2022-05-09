package ysaak.imeji.view.helper;

import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.extension.Function;
import com.mitchellbosecke.pebble.extension.escaper.SafeString;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import ysaak.imeji.view.dto.WallpaperViewDto;

import java.util.List;
import java.util.Map;

public class TagLinkHelper implements Function {
    private static Map<String, String> TYPE_CLASS = Map.of(
            "COPYRIGHT", "text-danger",
            "CHARACTER", "text-success",
            "CIRCLE", "text-info",
            "GENERAL", ""
    );

    private static final String TAG_ARG = "tag";

    @Override
    public List<String> getArgumentNames() {
        return List.of(TAG_ARG);
    }

    @Override
    public Object execute(Map<String, Object> map, PebbleTemplate self, EvaluationContext evaluationContext, int lineNumber) {
        Object arg1 = map.get(TAG_ARG);

        if (arg1 instanceof WallpaperViewDto.Tag) {
            WallpaperViewDto.Tag tag = (WallpaperViewDto.Tag) arg1;

            String tagLink = "<a href=\"/search?q=" +
                    tag.getName() +
                    "\" class=\"" +
                    TYPE_CLASS.getOrDefault(tag.getType(), "") +
                    "\">" +
                    tag.getName() +
                    "</a>";

            return new SafeString(tagLink);
        }
        else {
            throw new PebbleException(null, "First arg is not of type Tag", lineNumber, self.getName());
        }
    }


}
