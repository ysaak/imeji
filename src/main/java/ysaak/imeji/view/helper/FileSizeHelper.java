package ysaak.imeji.view.helper;

import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.extension.Function;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.List;
import java.util.Map;

public class FileSizeHelper implements Function {
    private static final String BYTES_ARG = "bytes";

    @Override
    public List<String> getArgumentNames() {
        return List.of(BYTES_ARG);
    }

    @Override
    public Object execute(Map<String, Object> map, PebbleTemplate self, EvaluationContext evaluationContext, int lineNumber) {
        Object arg1 = map.get(BYTES_ARG);

        if (arg1 instanceof Long) {
            long bytes = (Long) arg1;

            long absB = bytes == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(bytes);
            if (absB < 1024) {
                return bytes + " B";
            }
            long value = absB;
            CharacterIterator ci = new StringCharacterIterator("KMGTPE");
            for (int i = 40; i >= 0 && absB > 0xfffccccccccccccL >> i; i -= 10) {
                value >>= 10;
                ci.next();
            }
            value *= Long.signum(bytes);
            return String.format("%.1f %ciB", value / 1024.0, ci.current());
        }
        else {
            throw new PebbleException(null, "First arg is not of type Long", lineNumber, self.getName());
        }
    }
}
