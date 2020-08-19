package com.example.ConstructionCompany.service.filter

import cz.jirutka.rsql.parser.ast.ComparisonOperator
import cz.jirutka.rsql.parser.ast.RSQLOperators


enum class RsqlSearchOperation(private val operator: ComparisonOperator) {
    EQUAL(RSQLOperators.EQUAL), NOT_EQUAL(RSQLOperators.NOT_EQUAL), GREATER_THAN(RSQLOperators.GREATER_THAN), GREATER_THAN_OR_EQUAL(
        RSQLOperators.GREATER_THAN_OR_EQUAL
    ),
    LESS_THAN(RSQLOperators.LESS_THAN), LESS_THAN_OR_EQUAL(RSQLOperators.LESS_THAN_OR_EQUAL), IN(RSQLOperators.IN), NOT_IN(
        RSQLOperators.NOT_IN
    );

    companion object {
        fun getSimpleOperator(operator: ComparisonOperator?): RsqlSearchOperation? {
            return values().firstOrNull { it.operator === operator }
        }
    }

}