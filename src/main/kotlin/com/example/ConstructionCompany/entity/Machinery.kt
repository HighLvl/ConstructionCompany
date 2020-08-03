package com.example.ConstructionCompany.entity

import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "machinery", schema = "public", catalog = "construction")
class Machinery(

    @JoinColumn(name = "model_id", referencedColumnName = "id", nullable = false)
    @ManyToOne
    var model: MachineryModel? = null,

    @JoinColumn(name = "mng_id", referencedColumnName = "id")
    @ManyToOne
    var management: Management? = null

) : AbstractJpaPersistable<Long>()