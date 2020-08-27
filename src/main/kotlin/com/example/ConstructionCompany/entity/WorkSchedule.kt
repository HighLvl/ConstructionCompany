package com.example.ConstructionCompany.entity

import javax.persistence.*

@Entity
@Table(name = "work_shedule", schema = "public", catalog = "construction")
class WorkSchedule(

    @Column(name = "ord", nullable = false)
    @Basic
    var ord: Int = 0,

    @Column(name = "deadline", nullable = false)
    @Basic
    var deadline: Int = 0,


    @JoinColumn(name = "prototype_id", referencedColumnName = "id", nullable = false)
    @ManyToOne
    var prototype: Prototype? = null,

    @JoinColumn(name = "work_type_id", referencedColumnName = "id", nullable = false)
    @ManyToOne
    var workType: WorkType? = null

) : MutablePersistable<Long>()