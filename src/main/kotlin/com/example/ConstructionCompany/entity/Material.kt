package com.example.ConstructionCompany.entity

import javax.persistence.*

@Entity
@Table(name = "material", schema = "public", catalog = "construction")
class Material(

    @Column(name = "name", nullable = false, length = 40)
    @Basic
    var name: String? = null


) : AbstractJpaPersistable<Long>()