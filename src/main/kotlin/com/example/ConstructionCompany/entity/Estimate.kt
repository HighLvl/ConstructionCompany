package com.example.ConstructionCompany.entity

import javax.persistence.*

@Entity
@Table(name = "estimate", schema = "public", catalog = "construction")
class Estimate(

    @Column(name = "amount", nullable = false)
        @Basic
        var amount: Int = 0,

    @JoinColumn(name = "work_shedule_id", referencedColumnName = "id", nullable = false)
        @ManyToOne
        var workSchedule: WorkSchedule? = null,

    @JoinColumn(name = "material_id", referencedColumnName = "id", nullable = false)
        @ManyToOne
        var material: Material? = null
) : AbstractJpaPersistable<Long>()