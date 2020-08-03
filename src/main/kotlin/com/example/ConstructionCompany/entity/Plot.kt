package com.example.ConstructionCompany.entity

import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "plot", schema = "public", catalog = "construction")
class Plot(

    @JoinColumn(name = "chief_id", referencedColumnName = "id")
    @ManyToOne
    var chief: Staff? = null,

    @JoinColumn(name = "mng_id", referencedColumnName = "id")
    @ManyToOne
    var management: Management? = null

) : AbstractJpaPersistable<Long>()