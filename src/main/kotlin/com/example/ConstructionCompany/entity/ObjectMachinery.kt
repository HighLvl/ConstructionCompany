package com.example.ConstructionCompany.entity

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "object_machinery", schema = "public", catalog = "construction")
class ObjectMachinery(

    @Column(name = "start_date", nullable = false)
    @Basic
    var startDate: LocalDate? = null,

    @Column(name = "finish_date", nullable = true)
    @Basic
    var finishDate: LocalDate? = null,

    @JoinColumn(name = "object_id", referencedColumnName = "id", nullable = false)
    @ManyToOne
    var buildObject: BuildObject? = null,

    @JoinColumn(name = "machinery_id", referencedColumnName = "id", nullable = false)
    @ManyToOne
    var machinery: Machinery? = null

) : MutablePersistable<Long>()