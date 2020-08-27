package com.example.ConstructionCompany.entity

import javax.persistence.*

@Entity
@Table(name = "title", schema = "public", catalog = "construction")
class Title(

    @Column(name = "name", nullable = false, length = 40)
    @Basic
    var name: String? = null,

    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    @ManyToOne
    var titleCategory: TitleCategory? = null

) : MutablePersistable<Long>()