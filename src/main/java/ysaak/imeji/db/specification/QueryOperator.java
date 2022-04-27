package ysaak.imeji.db.specification;

public enum QueryOperator {
    EQUAL("="),
    LESS_THAN("<"),
    LESS_THAN_OR_EQUAL("<="),
    GREATER_THAN(">"),
    GREATER_THAN_OR_EQUAL(">=")
    ;

    private final String literal;

    QueryOperator(String literal) {
        this.literal = literal;
    }

    public static QueryOperator fromLiteral(String literal) {
        for (QueryOperator operator : QueryOperator.values()) {
            if (operator.literal.equals(literal)) {
                return operator;
            }
        }

        return QueryOperator.EQUAL;
    }
}
