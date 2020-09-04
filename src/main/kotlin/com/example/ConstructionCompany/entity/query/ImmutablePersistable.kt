package com.example.ConstructionCompany.entity.query

import com.example.ConstructionCompany.entity.MutablePersistable
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.Formula
import org.hibernate.annotations.Immutable
import org.hibernate.annotations.Subselect
import org.springframework.data.domain.Persistable
import org.springframework.data.util.ProxyUtils
import javax.persistence.*

/*Абстрактный класс для view*/
@MappedSuperclass
@Immutable
abstract class ImmutablePersistable : Persistable<Long> {
    @Id
    private var id: Long? = null

    override fun getId(): Long? {
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

        other as MutablePersistable<*>

        return if (null == this.getId()) false else this.getId() == other.getId()
    }

    override fun hashCode(): Int {
        return 31
    }

    companion object {
        private val serialVersionUID = -5554308939380869754L
    }


}