package com.example.ConstructionCompany.entity

import java.sql.Date
import javax.persistence.*

@Entity
@Table(name = "object_brigade", schema = "public", catalog = "construction")
class ObjectBrigade(

    @Column(name = "start_date", nullable = false)
    @Basic
    var startDate: Date? = null,

    @Column(name = "finish_date", nullable = true)
    @Basic
    var finishDate: Date? = null,

    @JoinColumn(name = "brigade_id", referencedColumnName = "id", nullable = false)
    @ManyToOne
    var brigade: Brigade? = null,

    @JoinColumn(name = "object_id", referencedColumnName = "id", nullable = false)
    @ManyToOne
    var buildObject: BuildObject? = null,

    @JoinColumn(name = "work_shedule_id", referencedColumnName = "id", nullable = false)
    @ManyToOne
    var workSchedule: WorkSchedule? = null

) : AbstractJpaPersistable<Long>()