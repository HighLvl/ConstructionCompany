package com.example.ConstructionCompany.entity

import javax.persistence.*

@Entity
@Table(name = "machinery_model", schema = "public", catalog = "construction")
class MachineryModel(
    @Column(name = "name", nullable = false, length = 40)
    @Basic
    var name: String? = null,

    @JoinColumn(name = "type_id", referencedColumnName = "id", nullable = false)
    @ManyToOne
    var machineryType: MachineryType? = null
) : MutablePersistable<Long>()