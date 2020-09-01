package com.example.ConstructionCompany.entity

import com.example.ConstructionCompany.entity.query.ImmutablePersistable
import org.hibernate.annotations.Immutable
import org.hibernate.annotations.Subselect
import java.sql.Date
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "report")
class Report: ImmutablePersistable() {

    @JoinColumn(name = "brigade_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @ManyToOne
    var brigade: Brigade? = null

    @JoinColumn(name = "object_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @ManyToOne
    var buildObject : BuildObject? = null

    @JoinColumn(name = "work_type_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @ManyToOne
    var workType: WorkType? = null

    @Column(name = "start_date", nullable = true)
    var startDate: LocalDate? = null

    @Column(name = "finish_date", nullable = true)
    var finishDate: LocalDate? = null

    @Column(name = "deadline", nullable = true)
    var deadline: Int? = null

    @Column(name = "time_overrun", nullable = true)
    var timeOverrun: Int? = null
}