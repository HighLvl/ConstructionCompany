package com.example.ConstructionCompany.entity

import java.sql.Date
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "brigade_members", schema = "public", catalog = "construction")
class BrigadeMember(

    @Column(name = "start_date", nullable = false)
    @Basic
    var startDate: LocalDate? = null,

    @Column(name = "finish_date", nullable = true)
    @Basic
    var finishDate: LocalDate? = null,

    @Column(name = "is_brigadier", nullable = false)
    @Basic
    var isBrigadier: Boolean = false,

    @JoinColumn(name = "brigade_id", referencedColumnName = "id", nullable = false)
    @ManyToOne
    //@JsonBackReference
    var brigade: Brigade? = null,

    @JoinColumn(name = "staff_id", referencedColumnName = "id", nullable = false)
    @ManyToOne
    var staff: Staff? = null


) : AbstractJpaPersistable<Long>()