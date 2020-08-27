package com.example.ConstructionCompany.entity

import javax.persistence.*

@Entity
@Table(name = "prototype", schema = "public", catalog = "construction")
class Prototype(

    @Column(name = "deadline", nullable = false)
    @Basic
    var deadline: Int = 0,

    @JoinColumn(name = "type_id", referencedColumnName = "id", nullable = false)
    @ManyToOne
    var prototypeType: PrototypeType? = null

) : MutablePersistable<Long>()