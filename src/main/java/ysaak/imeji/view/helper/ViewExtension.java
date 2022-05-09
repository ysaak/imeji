package ysaak.imeji.view.helper;

import com.mitchellbosecke.pebble.extension.AbstractExtension;
import com.mitchellbosecke.pebble.extension.Function;

import java.util.Map;

public class ViewExtension extends AbstractExtension {

    @Override
    public Map<String, Function> getFunctions() {
        return Map.of(
                "tag_link", new TagLinkHelper(),
                "file_size", new FileSizeHelper()
        );
    }
}
