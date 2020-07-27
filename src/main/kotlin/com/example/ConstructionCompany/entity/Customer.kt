package com.example.ConstructionCompany.entity

import javax.persistence.*

@Entity
@Table(name = "customer", schema = "public", catalog = "construction")
class Customer(
        @Column(name = "name", nullable = false, length = 40)
        @Basic
        var name: String? = null,

        @Column(name = "phone_number", nullable = false, length = -1)
        @Basic
        var phoneNumber: String? = null


) : AbstractJpaPersistable<Long>()