package com.example.ConstructionCompany.entity.query

import com.example.ConstructionCompany.entity.Material
import com.example.ConstructionCompany.entity.Report
import javax.persistence.*

@Entity
@Table(name = "material_consumption_report")
class MaterialConsumptionReport : ImmutablePersistable() {
    @JoinColumn(name = "report_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @ManyToOne
    var report: Report? = null

    @JoinColumn(name = "material_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @ManyToOne
    var material: Material? = null

    @Column(name = "cons_amount", nullable = true)
    var consAmount: Int? = null

    @Column(name = "est_amount", nullable = true)
    var estAmount: Int? = null

    @Column(name = "mat_cons_overrun", nullable = true)
    var matConsOverrun: Int? = null
}