package com.example.ConstructionCompany.entity

import java.sql.Date
import javax.persistence.*

@Entity
@Table(name = "build_object", schema = "public", catalog = "construction")
class BuildObject(

    @Column(name = "start_date", nullable = false)
        @Basic
        var startDate: Date? = null,

    @Column(name = "finish_date", nullable = true)
        @Basic
        var finishDate: Date? = null,

    @JoinColumn(name = "prototype_id", referencedColumnName = "id", nullable = false)
        @ManyToOne
        var prototype: Prototype? = null,

    @JoinColumn(name = "plot_id", referencedColumnName = "id", nullable = false)
        @ManyToOne
        var plot: Plot? = null,

    @JoinColumn(name = "customer_id", referencedColumnName = "id")
        @ManyToOne
        var customer: Customer? = null


) : AbstractJpaPersistable<Long>()