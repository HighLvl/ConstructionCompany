package com.example.ConstructionCompany.service

import com.example.ConstructionCompany.entity.*
import com.example.ConstructionCompany.entity.query.MaterialConsumptionReport
import com.example.ConstructionCompany.repository.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Persistable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.Serializable

/*Сервис, добавляющий поддержку транзакций*/
abstract class AbstractService<T : Persistable<ID>, ID : Serializable>(private val repository: AbstractRepository<T, ID>) {
    open fun findById(id: ID, pageable: Pageable): Page<T> {
        return repository.findById(id, pageable)
    }

    @Transactional
    open fun deleteByIdIn(ids: Collection<ID>) {
        repository.deleteByIdIn(ids)
    }

    @Transactional
    open fun saveAll(collection: Collection<T>): MutableIterable<T> {
        return repository.saveAll(collection)
    }

    open fun findAll(pageable: Pageable): Page<T> {
        return repository.findAll(pageable)
    }

    open fun findAll(spec: Specification<T>?, pageable: Pageable): Page<T> {
        return repository.findAll(spec, pageable)
    }
}


@Service
class BrigadeMemberService(private val repository: BrigadeMemberRepository) :
    AbstractService<BrigadeMember, Long>(repository) {
    fun findAllByBrigadeId(id: Long, pageable: Pageable): Page<BrigadeMember> {
        return repository.findAllByBrigadeId(id, pageable)
    }

    fun findAllByStaffId(id: Long, pageable: Pageable): Page<BrigadeMember> {
        return repository.findAllByStaffId(id, pageable)
    }

}

@Service
class BrigadeService(private val repository: BrigadeRepository) : AbstractService<Brigade, Long>(repository) {
    fun findAllByTitleId(id: Long, pageable: Pageable): Page<Brigade> {
        return repository.findAllByTitleId(id, pageable)
    }
}

@Service
class BuildObjectService(private val repository: BuildObjectRepository) :
    AbstractService<BuildObject, Long>(repository) {
    fun findAllByPrototypeId(id: Long, pageable: Pageable): Page<BuildObject> {
        return repository.findAllByPrototypeId(id, pageable)
    }

    fun findAllByPlotId(id: Long, pageable: Pageable): Page<BuildObject> {
        return repository.findAllByPlotId(id, pageable)
    }

    fun findAllByCustomerId(id: Long, pageable: Pageable): Page<BuildObject> {
        return repository.findAllByCustomerId(id, pageable)
    }
}

@Service
class CustomerService(private val repository: CustomerRepository) : AbstractService<Customer, Long>(repository)

@Service
class EstimateService(private val repository: EstimateRepository) : AbstractService<Estimate, Long>(repository) {
    fun findAllByWorkScheduleId(id: Long, pageable: Pageable): Page<Estimate> {
        return repository.findAllByWorkScheduleId(id, pageable)
    }

    fun findAllByMaterialId(id: Long, pageable: Pageable): Page<Estimate> {
        return repository.findAllByMaterialId(id, pageable)
    }


}

@Service
class MachineryService(private val repository: MachineryRepository) : AbstractService<Machinery, Long>(repository) {
    fun findAllByModelId(id: Long, pageable: Pageable): Page<Machinery> {
        return repository.findAllByModelId(id, pageable)
    }

    fun findAllByManagementId(id: Long, pageable: Pageable): Page<Machinery> {
        return repository.findAllByManagementId(id, pageable)
    }
}

@Service
class MachineryModelService(private val repository: MachineryModelRepository) :
    AbstractService<MachineryModel, Long>(repository) {
    fun findAllByMachineryTypeId(id: Long, pageable: Pageable): Page<MachineryModel> {
        return repository.findAllByMachineryTypeId(id, pageable)
    }

}

@Service
class MachineryTypeService(private val repository: MachineryTypeRepository) :
    AbstractService<MachineryType, Long>(repository)

@Service
class ManagementService(private val repository: ManagementRepository) : AbstractService<Management, Long>(repository) {
    fun findAllByChiefId(id: Long, pageable: Pageable): Page<Management> {
        return repository.findAllByChiefId(id, pageable)
    }

}

@Service
class MaterialService(private val repository: MaterialRepository) : AbstractService<Material, Long>(repository)

@Service
class MaterialConsumptionService(private val repository: MaterialConsumptionRepository) :
    AbstractService<MaterialConsumption, Long>(repository) {
    fun findAllByObjectBrigadeId(id: Long, pageable: Pageable): Page<MaterialConsumption> {
        return repository.findAllByObjectBrigadeId(id, pageable)
    }

    fun findAllByEstimateId(id: Long, pageable: Pageable): Page<MaterialConsumption> {
        return repository.findAllByEstimateId(id, pageable)
    }
}

@Service
class ObjectBrigadeService(private val repository: ObjectBrigadeRepository) :
    AbstractService<ObjectBrigade, Long>(repository) {
    fun findAllByBrigadeId(id: Long, pageable: Pageable): Page<ObjectBrigade> {
        return repository.findAllByBrigadeId(id, pageable)
    }

    fun findAllByBuildObjectId(id: Long, pageable: Pageable): Page<ObjectBrigade> {
        return repository.findAllByBuildObjectId(id, pageable)
    }

    fun findAllByWorkScheduleId(id: Long, pageable: Pageable): Page<ObjectBrigade> {
        return repository.findAllByWorkScheduleId(id, pageable)
    }


}

@Service
class ObjectMachineryService(private val repository: ObjectMachineryRepository) :
    AbstractService<ObjectMachinery, Long>(repository) {
    fun findAllByBuildObjectId(id: Long, pageable: Pageable): Page<ObjectMachinery> {
        return repository.findAllByBuildObjectId(id, pageable)
    }

    fun findAllByMachineryId(id: Long, pageable: Pageable): Page<ObjectMachinery> {
        return repository.findAllByMachineryId(id, pageable)
    }


}

@Service
class PlotService(private val repository: PlotRepository) : AbstractService<Plot, Long>(repository) {
    fun findAllByChiefId(id: Long, pageable: Pageable): Page<Plot> {
        return repository.findAllByChiefId(id, pageable)
    }

    fun findAllByManagementId(id: Long, pageable: Pageable): Page<Plot> {
        return repository.findAllByManagementId(id, pageable)
    }

}

@Service
class PrototypeService(private val repository: PrototypeRepository) : AbstractService<Prototype, Long>(repository) {
    fun findAllByPrototypeTypeId(id: Long, pageable: Pageable): Page<Prototype> {
        return repository.findAllByPrototypeTypeId(id, pageable)
    }

}

@Service
class PrototypeTypeService(private val repository: PrototypeTypeRepository) :
    AbstractService<PrototypeType, Long>(repository)

@Service
class StaffService(private val repository: StaffRepository) : AbstractService<Staff, Long>(repository) {
    fun findAllByTitleId(id: Long, pageable: Pageable): Page<Staff> {
        return repository.findAllByTitleId(id, pageable)
    }

    fun findAllByChiefId(id: Long, pageable: Pageable): Page<Staff> {
        return repository.findAllByChiefId(id, pageable)
    }
}

@Service
class TitleService(private val repository: TitleRepository) : AbstractService<Title, Long>(repository) {
    fun findAllByTitleCategoryId(id: Long, pageable: Pageable): Page<Title> {
        return repository.findAllByTitleCategoryId(id, pageable)
    }
}

@Service
class TitleCategoryService(private val repository: TitleCategoryRepository) :
    AbstractService<TitleCategory, Long>(repository)

@Service
class WorkScheduleService(private val repository: WorkScheduleRepository) :
    AbstractService<WorkSchedule, Long>(repository) {
    fun findAllByPrototypeId(id: Long, pageable: Pageable): Page<WorkSchedule> {
        return repository.findAllByPrototypeId(id, pageable)
    }

    fun findAllByWorkTypeId(id: Long, pageable: Pageable): Page<WorkSchedule> {
        return repository.findAllByWorkTypeId(id, pageable)
    }
}

@Service
class WorkTypeService(private val repository: WorkTypeRepository) : AbstractService<WorkType, Long>(repository)

@Service
class ReportService(private val repository: ReportRepository) : AbstractService<Report, Long>(repository)

@Service
class MaterialConsumptionReportService(private val repository: MaterialConsumptionReportRepository) : AbstractService<MaterialConsumptionReport, Long>(repository) {
    fun findAllByReportId(id: Long, pageable: Pageable): Page<MaterialConsumptionReport> {
        return repository.findAllByReportId(id, pageable)
    }
}