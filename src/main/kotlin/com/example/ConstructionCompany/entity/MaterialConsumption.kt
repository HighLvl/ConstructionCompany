package com.example.ConstructionCompany.entity

import javax.persistence.*

@Entity
@Table(name = "material_consumption", schema = "public", catalog = "construction")
class MaterialConsumption(

    @Column(name = "amount", nullable = false)
    @Basic
    var amount: Int = 0,

    @JoinColumn(name = "brigade_object_id", referencedColumnName = "id", nullable = false)
    @ManyToOne
    var objectBrigade: ObjectBrigade? = null,

    @JoinColumn(name = "estimate_id", referencedColumnName = "id", nullable = false)
    @ManyToOne
    var estimate: Estimate? = null

) : AbstractJpaPersistable<Long>()