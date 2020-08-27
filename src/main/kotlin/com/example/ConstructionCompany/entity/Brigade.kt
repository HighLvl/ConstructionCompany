package com.example.ConstructionCompany.entity

import javax.persistence.*

@Entity
@Table(name = "brigade", schema = "public", catalog = "construction")
class Brigade(
    @Column(name = "name", nullable = false, length = 40)
    @Basic
    var name: String? = null,

    @JoinColumn(name = "title_id", referencedColumnName = "id")
    @ManyToOne
    var title: Title? = null


) : MutablePersistable<Long>()