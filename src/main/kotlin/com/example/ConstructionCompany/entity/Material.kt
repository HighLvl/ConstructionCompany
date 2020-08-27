package com.example.ConstructionCompany.entity

import javax.persistence.Basic
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "material", schema = "public", catalog = "construction")
class Material(

    @Column(name = "name", nullable = false, length = 40)
    @Basic
    var name: String? = null


) : MutablePersistable<Long>()