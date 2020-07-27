package com.example.ConstructionCompany.entity

import javax.persistence.*

@Entity
@Table(name = "machinery_type", schema = "public", catalog = "construction")
class MachineryType(

        @Column(name = "name", nullable = false, length = 40)
        @Basic
        var name: String? = null


) : AbstractJpaPersistable<Long>()