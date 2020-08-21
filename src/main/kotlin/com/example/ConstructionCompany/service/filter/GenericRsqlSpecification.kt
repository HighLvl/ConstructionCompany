package com.example.ConstructionCompany.service.filter

import com.example.ConstructionCompany.entity.AbstractJpaPersistable
import cz.jirutka.rsql.parser.ast.ComparisonOperator
import org.springframework.data.jpa.domain.Specification
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.persistence.criteria.*

@Suppress("UNCHECKED_CAST")
class GenericRsqlSpecification<T>(
    private val property: String,
    private val operator: ComparisonOperator,
    private val arguments: List<String>
) : Specification<T> {

    override fun toPredicate(root: Root<T>, query: CriteriaQuery<*>, builder: CriteriaBuilder): Predicate? {
        val propertyExpression = parseProperty(root)
        val args = castArguments(propertyExpression)
        val argument = args[0]

        return when(argument.javaClass.kotlin) {
            LocalDate::class -> buildCriteria(propertyExpression as Expression<LocalDate>, args as List<LocalDate>, builder)
            Long::class -> buildCriteria(propertyExpression as Expression<Long>, args as List<Long>, builder)
            Byte::class -> buildCriteria(propertyExpression as Expression<Byte>, args as List<Byte>, builder)
            Int::class -> buildCriteria(propertyExpression as Expression<Int>, args as List<Int>, builder)
            Boolean::class -> buildCriteria(propertyExpression as Expression<Boolean>, args as List<Boolean>, builder)
            else -> buildCriteria<String>(propertyExpression, args as List<String>, builder)
        }
    }

    private fun <Y : Comparable<Y>?> buildCriteria(propertyExpression: Expression<out Y>, args: List<Y>, builder: CriteriaBuilder): Predicate? {
        val argument = args[0]

        when (RsqlSearchOperation.getSimpleOperator(operator)) {
            RsqlSearchOperation.EQUAL ->
                return if (argument is String) builder.like(
                    propertyExpression as Expression<String>,
                    argument.toString().replace('*', '%')
                ) else builder.equal(
                    propertyExpression, argument
                )
            RsqlSearchOperation.NOT_EQUAL ->
                return if (argument is String) builder.notLike(
                    propertyExpression as Expression<String>,
                    argument.toString().replace('*', '%')
                ) else builder.notEqual(
                    propertyExpression,
                    argument
                )
            RsqlSearchOperation.GREATER_THAN ->
                return builder.greaterThan<Y>(
                    propertyExpression,
                    argument
                )
            RsqlSearchOperation.GREATER_THAN_OR_EQUAL ->
                return builder.greaterThanOrEqualTo<Y>(
                    propertyExpression,
                    argument
                )
            RsqlSearchOperation.LESS_THAN ->
                return builder.lessThan<Y>(
                    propertyExpression,
                    argument
                )
            RsqlSearchOperation.LESS_THAN_OR_EQUAL ->
                return builder.lessThanOrEqualTo<Y>(
                    propertyExpression,
                    argument
                )
            RsqlSearchOperation.IN ->
                return propertyExpression.`in`(args)
            RsqlSearchOperation.NOT_IN ->
                return builder.not(propertyExpression.`in`(args))
        }
        return null
    }

    private fun parseProperty(root: Root<T>): Path<String> {
        var path: Path<String>
        if (property.contains(".")) {
            val pathSteps = property.split("\\.".toRegex()).toTypedArray()
            val step = pathSteps[0]
            path = root.get(step)
            for (i in 1 until pathSteps.size) {
                path = path.get(pathSteps[i])
            }
        } else {
            path = root.get(property)
        }
        return path
    }

    private fun castArguments(propertyExpression: Path<*>): List<Any> {
        val type = propertyExpression.javaType
        return arguments.map { arg: String ->
            when (type) {
                Int::class.java -> return@map arg.toInt()
                Long::class.java -> return@map arg.toLong()
                Byte::class.java -> return@map arg.toByte()
                Boolean::class.java -> return@map arg.toBoolean()
                LocalDate::class.java -> return@map LocalDate.parse(arg, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                else -> {
                    if(property.contains("^id\$|\\.id\$".toRegex())) return@map arg.toLong()
                    return@map arg
                }
            }
        }.toList()
    }
}