package com.example.ConstructionCompany.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.GenericGenerator
import org.hibernate.boot.model.naming.Identifier
import org.hibernate.boot.model.relational.QualifiedName
import org.hibernate.boot.model.relational.QualifiedNameParser
import org.hibernate.dialect.Dialect
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment
import org.hibernate.id.enhanced.SequenceStyleGenerator
import org.hibernate.service.ServiceRegistry
import org.springframework.data.domain.Persistable
import org.springframework.data.util.ProxyUtils
import java.io.Serializable
import java.util.*
import javax.persistence.*


@MappedSuperclass
abstract class AbstractJpaPersistable<T : Serializable> : Persistable<T> {
    @Id
    @GenericGenerator(
        name = "sequenceGenerator",
        strategy = "com.example.ConstructionCompany.entity.MaxIdSequenceGenerator"
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    private var id: T? = null

    override fun getId(): T? {
        return id
    }

    @Transient
    @JsonIgnore
    override fun isNew() = null == getId()

    override fun toString() = "Entity of type ${this.javaClass.name} with id: $id"

    override fun equals(other: Any?): Boolean {
        other ?: return false

        if (this === other) return true

        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as AbstractJpaPersistable<*>

        return if (null == this.getId()) false else this.getId() == other.getId()
    }

    override fun hashCode(): Int {
        return 31
    }

    companion object {
        private val serialVersionUID = -5554308939380869754L
    }


}

class MaxIdSequenceGenerator : SequenceStyleGenerator() {
    override fun determineSequenceName(
        params: Properties?,
        dialect: Dialect?,
        jdbcEnv: JdbcEnvironment?,
        serviceRegistry: ServiceRegistry?
    ): QualifiedName {
        val qualifiedName = super.determineSequenceName(params, dialect, jdbcEnv, serviceRegistry)
        val sequenceName = params!!.getProperty(TABLE) + "_" + params.getProperty(PK) + "_seq"

        return QualifiedNameParser.NameParts(
            qualifiedName.catalogName,
            qualifiedName.schemaName,
            Identifier.toIdentifier(sequenceName)
        )
    }
}