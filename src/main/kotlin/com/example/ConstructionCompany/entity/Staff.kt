package com.example.ConstructionCompany.entity

import javax.persistence.*

@Entity
@Table(name = "staff", schema = "public", catalog = "construction")
class Staff(
    @Column(name = "name", nullable = false, length = 40)
    @Basic
    var name: String? = null,

    @Column(name = "phone_number", nullable = false, length = -1)
    @Basic
    var phoneNumber: String? = null,


    @JoinColumn(name = "title_id", referencedColumnName = "id", nullable = false)
    @ManyToOne
    var title: Title? = null,

    @JoinColumn(name = "chief_id", referencedColumnName = "id")
    @ManyToOne
    var chief: Staff? = null

//    @OneToMany(mappedBy = "staff")
//    var staffById: MutableCollection<Staff>? = null

) : AbstractJpaPersistable<Long>()