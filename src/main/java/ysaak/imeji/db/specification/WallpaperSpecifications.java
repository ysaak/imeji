package ysaak.imeji.db.specification;

import org.springframework.data.jpa.domain.Specification;
import ysaak.imeji.data.Wallpaper;
import ysaak.imeji.data.WallpaperColor;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.SetJoin;

public final class WallpaperSpecifications {
    private WallpaperSpecifications() { /**/ }

    public static Specification<Wallpaper> similarColor(final double cieL, final double cieA, final double cieB, final double threshold) {
        return (root, query, criteriaBuilder) -> {
            SetJoin<Wallpaper, WallpaperColor> palette = root.joinSet("palette", JoinType.INNER);
            Expression<Double> colorDistance = criteriaBuilder.function(
                    "CALCULATE_COLOR_DISTANCE",
                    Double.class,
                    criteriaBuilder.literal(cieL),
                    criteriaBuilder.literal(cieA),
                    criteriaBuilder.literal(cieB),
                    palette.get("cieL"),
                    palette.get("cieA"),
                    palette.get("cieB")
            );
            query.distinct(true);

            return criteriaBuilder.lessThan(colorDistance, threshold);
        };
    }

    public static Specification<Wallpaper> integerQuery(final String column, final QueryOperator operator, final int value) {
        return (root, query, criteriaBuilder) -> switch (operator) {
            case EQUAL -> criteriaBuilder.equal(
                    root.get(column),
                    value
            );
            case LESS_THAN -> criteriaBuilder.lessThan(
                    root.get(column),
                    value
            );
            case LESS_THAN_OR_EQUAL -> criteriaBuilder.lessThanOrEqualTo(
                    root.get(column),
                    value
            );
            case GREATER_THAN -> criteriaBuilder.greaterThan(
                    root.get(column),
                    value
            );
            case GREATER_THAN_OR_EQUAL -> criteriaBuilder.greaterThanOrEqualTo(
                    root.get(column),
                    value
            );
        };
    }
}
