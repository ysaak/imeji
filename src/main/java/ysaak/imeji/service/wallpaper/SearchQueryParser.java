package ysaak.imeji.service.wallpaper;

import org.springframework.data.jpa.domain.Specification;
import ysaak.imeji.data.Wallpaper;
import ysaak.imeji.db.specification.QueryOperator;
import ysaak.imeji.db.specification.WallpaperSpecifications;
import ysaak.imeji.utils.ColorConverter;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchQueryParser {
    private static final Pattern NUMBER_REGEX = Pattern.compile("(<|<=|>|>=)?(\\d+)");

    private final Map<String, TokenParser> tokenParsers = new HashMap<>();

    private final double colorThreshold;

    public SearchQueryParser(double colorThreshold) {
        this.colorThreshold = colorThreshold;
        tokenParsers.put("width", new TokenParser("width", this::parseIntegerQuery));
        tokenParsers.put("height", new TokenParser("height", this::parseIntegerQuery));
        tokenParsers.put("color", new TokenParser(null, this::parseColorQuery));
    }

    public Specification<Wallpaper> parse(String searchQuery) {
        Specification<Wallpaper> specification = null;

        String[] searchItems = searchQuery.trim().split(" ");
        for (String item : searchItems) {

            int colonIndex = item.indexOf(":");
            if (colonIndex != -1) {

                String token = item.substring(0, colonIndex);
                String input = item.substring(colonIndex + 1);

                if (tokenParsers.containsKey(token)) {
                    final TokenParser tokenParser = tokenParsers.get(token);

                    Specification<Wallpaper> spec = tokenParser.parser.parse(tokenParser.column, input);
                    if (spec != null) {
                        if (specification == null) {
                            specification = spec;
                        } else {
                            specification = specification.and(spec);
                        }
                    }
                }
            }
            else {
                Specification<Wallpaper> tagSpecification = WallpaperSpecifications.hasTag(item);
                if (specification == null) {
                    specification = tagSpecification;
                }
                else {
                    specification = specification.and(tagSpecification);
                }
            }
        }

        return specification;
    }

    private Specification<Wallpaper> parseIntegerQuery(final String column, final String input) {

        Matcher matcher = NUMBER_REGEX.matcher(input);

        if (matcher.matches()) {
            QueryOperator operator = QueryOperator.fromLiteral(matcher.group(1));
            int value = Integer.parseInt(matcher.group(2));

            return WallpaperSpecifications.integerQuery(column, operator, value);
        }

        return null;
    }

    private Specification<Wallpaper> parseColorQuery(final String column, final String input) {
        int[] rgbColor = ColorConverter.hexToRgb(input);
        double[] labColor = ColorConverter.rgbToLab(rgbColor[0], rgbColor[1], rgbColor[2]);

        return WallpaperSpecifications.similarColor(
                labColor[0],
                labColor[1],
                labColor[2],
                this.colorThreshold
        );
    }

    private interface Parser {
        Specification<Wallpaper> parse(String column, String input);
    }

    private static class TokenParser {
        private final String column;
        private final Parser parser;

        public TokenParser(String column, Parser parser) {
            this.column = column;
            this.parser = parser;
        }
    }
}
