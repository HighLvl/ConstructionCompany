package com.example.ConstructionCompany.service.filter

import cz.jirutka.rsql.parser.ast.AndNode
import cz.jirutka.rsql.parser.ast.ComparisonNode
import cz.jirutka.rsql.parser.ast.OrNode
import cz.jirutka.rsql.parser.ast.RSQLVisitor
import org.springframework.data.jpa.domain.Specification


class CustomRsqlVisitor<T> : RSQLVisitor<Specification<T>?, Void?> {
    private val builder: GenericRsqlSpecBuilder<T> = GenericRsqlSpecBuilder()

    override fun visit(node: AndNode, param: Void?): Specification<T>? {
        return builder.createSpecification(node)
    }

    override fun visit(node: OrNode, param: Void?): Specification<T>? {
        return builder.createSpecification(node)
    }

    override fun visit(node: ComparisonNode, param: Void?): Specification<T>? {
        return builder.createSpecification(node)
    }
}