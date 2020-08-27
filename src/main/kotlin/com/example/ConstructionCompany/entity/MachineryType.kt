package com.example.ConstructionCompany.entity

import javax.persistence.Basic
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "machinery_type", schema = "public", catalog = "construction")
class MachineryType(

    @Column(name = "name", nullable = false, length = 40)
    @Basic
    var name: String? = null


) : MutablePersistable<Long>()