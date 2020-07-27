package com.example.ConstructionCompany.controller

import com.example.ConstructionCompany.controller.assembler.*
import com.example.ConstructionCompany.entity.*
import com.example.ConstructionCompany.repository.*
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping(value = ["/brigades"])
class BrigadeController(
    private val repository: BrigadeRepository,
    private val assembler: BrigadeModelAssembler
) : AbstractController<Long, Brigade>(repository, assembler) {
    @GetMapping("/title/{id}")
    fun findAllByTitleId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<Brigade>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<Brigade>>> {
        val page = repository.findAllByTitleId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }
}

@RestController
@RequestMapping(value = ["/brigadeMembers"])
class BrigadeMemberController(
    private val repository: BrigadeMemberRepository,
    private val assembler: BrigadeMemberModelAssembler
) : AbstractController<Long, BrigadeMember>(repository, assembler) {

    @GetMapping("/brigade/{id}")
    fun findAllByBrigadeId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<BrigadeMember>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<BrigadeMember>>> {
        val page = repository.findAllByBrigadeId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }

    @GetMapping("/staff/{id}")
    fun findAllByStaffId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<BrigadeMember>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<BrigadeMember>>> {
        val page = repository.findAllByStaffId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }
}

@RestController
@RequestMapping(value = ["/buildObjects"])
class BuildObjectController(
    private val repository: BuildObjectRepository,
    private val assembler: BuildObjectModelAssembler
) : AbstractController<Long, BuildObject>(repository, assembler) {

    @GetMapping("/prototype/{id}")
    fun findAllByPrototypeId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<BuildObject>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<BuildObject>>> {
        val page = repository.findAllByPrototypeId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }

    @GetMapping("/plot/{id}")
    fun findAllByPlotId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<BuildObject>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<BuildObject>>> {
        val page = repository.findAllByPlotId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }

    @GetMapping("/customer/{id}")
    fun findAllByCustomerId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<BuildObject>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<BuildObject>>> {
        val page = repository.findAllByCustomerId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }
}

@RestController
@RequestMapping(value = ["/estimates"])
class EstimateController(
    private val repository: EstimateRepository,
    private val assembler: EstimateModelAssembler
) : AbstractController<Long, Estimate>(repository, assembler) {

    @GetMapping("/workSchedule/{id}")
    fun findAllByWorkScheduleId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<Estimate>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<Estimate>>> {
        val page = repository.findAllByWorkScheduleId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }


    @GetMapping("/material/{id}")
    fun findAllByMaterialId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<Estimate>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<Estimate>>> {
        val page = repository.findAllByMaterialId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }
}

@RestController
@RequestMapping(value = ["/machines"])
class MachineryController(
    private val repository: MachineryRepository,
    private val assembler: MachineryModelAssembler
) : AbstractController<Long, Machinery>(repository, assembler) {

    @GetMapping("/model/{id}")
    fun findAllByModelId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<Machinery>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<Machinery>>> {
        val page = repository.findAllByModelId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }

    @GetMapping("/management/{id}")
    fun findAllByManagementId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<Machinery>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<Machinery>>> {
        val page = repository.findAllByManagementId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }
}

@RestController
@RequestMapping(value = ["/machineryModels"])
class MachineryModelController(
    private val repository: MachineryModelRepository,
    private val assembler: MachineryModelModelAssembler
) : AbstractController<Long, MachineryModel>(repository, assembler) {

    @GetMapping("/machineryType/{id}")
    fun findAllByMachineryTypeId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<MachineryModel>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<MachineryModel>>> {
        val page = repository.findAllByMachineryTypeId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }
}

@RestController
@RequestMapping(value = ["/managements"])
class ManagementController(
    private val repository: ManagementRepository,
    private val assembler: ManagementModelAssembler
) : AbstractController<Long, Management>(repository, assembler) {

    @GetMapping("/chief/{id}")
    fun findAllByChiefId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<Management>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<Management>>> {
        val page = repository.findAllByChiefId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }
}

@RestController
@RequestMapping(value = ["/materialConsumptions"])
class MaterialConsumptionController(
    private val repository: MaterialConsumptionRepository,
    private val assembler: MaterialConsumptionModelAssembler
) : AbstractController<Long, MaterialConsumption>(repository, assembler) {

    @GetMapping("/objectBrigade/{id}")
    fun findAllByObjectBrigadeId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<MaterialConsumption>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<MaterialConsumption>>> {
        val page = repository.findAllByObjectBrigadeId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }

    @GetMapping("/estimate/{id}")
    fun findAllByEstimateId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<MaterialConsumption>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<MaterialConsumption>>> {
        val page = repository.findAllByEstimateId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }
}

@RestController
@RequestMapping(value = ["/objectBrigades"])
class ObjectBrigadeController(
    private val repository: ObjectBrigadeRepository,
    private val assembler: ObjectBrigadeModelAssembler
) : AbstractController<Long, ObjectBrigade>(repository, assembler) {

    @GetMapping("/brigade/{id}")
    fun findAllByBrigadeId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<ObjectBrigade>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<ObjectBrigade>>> {
        val page = repository.findAllByBrigadeId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }

    @GetMapping("/buildObject/{id}")
    fun findAllByBuildObjectId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<ObjectBrigade>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<ObjectBrigade>>> {
        val page = repository.findAllByBuildObjectId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }

    @GetMapping("/workSchedule/{id}")
    fun findAllByWorkScheduleId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<ObjectBrigade>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<ObjectBrigade>>> {
        val page = repository.findAllByWorkScheduleId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }

}

@RestController
@RequestMapping(value = ["/objectBrigades"])
class ObjectMachineryController(
    private val repository: ObjectMachineryRepository,
    private val assembler: ObjectMachineryModelAssembler
) : AbstractController<Long, ObjectMachinery>(repository, assembler) {

    @GetMapping("/buildObject/{id}")
    fun findAllByBuildObjectId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<ObjectMachinery>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<ObjectMachinery>>> {
        val page = repository.findAllByBuildObjectId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }

    @GetMapping("/machinery/{id}")
    fun findAllByMachineryId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<ObjectMachinery>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<ObjectMachinery>>> {
        val page = repository.findAllByMachineryId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }


}


@RestController
@RequestMapping(value = ["/plots"])
class PlotController(
    private val repository: PlotRepository,
    private val assembler: PlotModelAssembler
) : AbstractController<Long, Plot>(repository, assembler) {

    @GetMapping("/chief/{id}")
    fun findAllByChiefId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<Plot>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<Plot>>> {
        val page = repository.findAllByChiefId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }

    @GetMapping("/management/{id}")
    fun findAllByManagementId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<Plot>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<Plot>>> {
        val page = repository.findAllByManagementId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }
}

@RestController
@RequestMapping(value = ["/prototyps"])
class PrototypeController(
    private val repository: PrototypeRepository,
    private val assembler: PrototypeModelAssembler
) : AbstractController<Long, Prototype>(repository, assembler) {

    @GetMapping("/prototypeType/{id}")
    fun findAllByChiefId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<Prototype>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<Prototype>>> {
        val page = repository.findAllByPrototypeTypeId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }

}


@RestController
@RequestMapping(value = ["/titles"])
class TitleController(
    private val repository: TitleRepository,
    private val assembler: TitleModelAssembler
) : AbstractController<Long, Title>(repository, assembler) {

    @GetMapping("/titleCategory/{id}")
    fun findAllByTitleCategoryId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<Title>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<Title>>> {
        val page = repository.findAllByTitleCategoryId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }
}

@RestController
@RequestMapping(value = ["/titleCategories"])
class TitleCategoryController(
    private val repository: TitleCategoryRepository,
    private val assembler: TitleCategoryModelAssembler
) : AbstractController<Long, TitleCategory>(repository, assembler)

@RestController
@RequestMapping(value = ["/workTypes"])
class WorkTypeController(
    private val repository: WorkTypeRepository,
    private val assembler: WorkTypeModelAssembler
) : AbstractController<Long, WorkType>(repository, assembler)

@RestController
@RequestMapping(value = ["/prototypeTypes"])
class PrototypeTypeController(
    private val repository: PrototypeTypeRepository,
    private val assembler: PrototypeTypeModelAssembler
) : AbstractController<Long, PrototypeType>(repository, assembler)

@RestController
@RequestMapping(value = ["/materials"])
class MaterialController(
    private val repository: MaterialRepository,
    private val assembler: MaterialModelAssembler
) : AbstractController<Long, Material>(repository, assembler)

@RestController
@RequestMapping(value = ["/machineryTypes"])
class MachineryTypeController(
    private val repository: MachineryTypeRepository,
    private val assembler: MachineryTypeModelAssembler
) : AbstractController<Long, MachineryType>(repository, assembler)

@RestController
@RequestMapping(value = ["/customers"])
class CustomerController(
    private val repository: CustomerRepository,
    private val assembler: CustomerModelAssembler
) : AbstractController<Long, Customer>(repository, assembler)

@RestController
@RequestMapping(value = ["/staff"])
class StaffController(
    private val repository: StaffRepository,
    private val assembler: StaffModelAssembler
) : AbstractController<Long, Staff>(repository, assembler) {
    @GetMapping("/title/{id}")
    fun findAllByTitleId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<Staff>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<Staff>>> {
        val page = repository.findAllByTitleId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }

    @GetMapping("/chief/{id}")
    fun findAllByChiefId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<Staff>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<Staff>>> {
        val page = repository.findAllByChiefId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }
}

@RestController
@RequestMapping(value = ["/workSchedules"])
class WorkScheduleController(
    private val repository: WorkScheduleRepository,
    private val assembler: WorkScheduleModelAssembler
) : AbstractController<Long, WorkSchedule>(repository, assembler) {
    @GetMapping("/prototype/{id}")
    fun findAllByPrototypeId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<WorkSchedule>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<WorkSchedule>>> {
        val page = repository.findAllByPrototypeId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }

    @GetMapping("/workType/{id}")
    fun findAllByWorkTypeId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<WorkSchedule>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<WorkSchedule>>> {
        val page = repository.findAllByWorkTypeId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }
}