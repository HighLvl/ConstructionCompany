package com.example.ConstructionCompany.entity

import org.hibernate.annotations.Immutable
import java.sql.Date
import javax.persistence.*

@Entity
@Immutable
@Table(name = "report", schema = "public", catalog = "construction")
class Report: AbstractJpaPersistable<Long>() {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    var id: Long? = null

    @JoinColumn(name = "brigade_id", referencedColumnName = "id")
    @ManyToOne
    var brigade: Brigade? = null

    @JoinColumn(name = "object_id", referencedColumnName = "id")
    @ManyToOne
    var build_object : BuildObject? = null

    @JoinColumn(name = "work_type_id", referencedColumnName = "id")
    @ManyToOne
    var workType: WorkType? = null

    @Column(name = "start_date", nullable = true)
    var startDate: Date? = null

    @Column(name = "finish_date", nullable = true)
    var finishDate: Date? = null

    @Column(name = "deadline", nullable = true)
    var deadline: Int? = null

    @Column(name = "time_overrun", nullable = true)
    var timeOverrun: Int? = null

    @JoinColumn(name = "material_id", referencedColumnName = "id")
    @ManyToOne
    var material: Material? = null

    @Column(name = "cons_amount", nullable = true)
    var consAmount: Int? = null

    @Column(name = "est_amount", nullable = true)
    var estAmount: Int? = null

    @Column(name = "mat_cons_overrun", nullable = true)
    var matConsOverrun: Int? = null
}