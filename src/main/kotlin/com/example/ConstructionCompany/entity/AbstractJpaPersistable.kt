package com.example.ConstructionCompany.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.domain.Persistable
import org.springframework.data.util.ProxyUtils
import java.io.Serializable
import javax.persistence.*


@MappedSuperclass
abstract class AbstractJpaPersistable<T : Serializable> : Persistable<T> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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