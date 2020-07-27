package com.example.ConstructionCompany.entity

import javax.persistence.*

@Entity
@Table(name = "title_category", schema = "public", catalog = "construction")
class TitleCategory(

    @Column(name = "name", nullable = false, length = 40)
    @Basic
    var name: String? = null

) : AbstractJpaPersistable<Long>()