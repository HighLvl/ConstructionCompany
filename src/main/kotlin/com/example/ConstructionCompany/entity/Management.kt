package com.example.ConstructionCompany.entity

import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "management", schema = "public", catalog = "construction")
class Management(
    @JoinColumn(name = "chief_id", referencedColumnName = "id")
    @ManyToOne
    var chief: Staff? = null


) : MutablePersistable<Long>()