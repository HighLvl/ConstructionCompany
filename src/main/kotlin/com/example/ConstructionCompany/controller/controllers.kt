package com.example.ConstructionCompany.controller

import com.example.ConstructionCompany.applyBasePath
import com.example.ConstructionCompany.controller.assembler.*
import com.example.ConstructionCompany.entity.*
import com.example.ConstructionCompany.entity.query.MaterialConsumptionReport
import com.example.ConstructionCompany.service.*
import com.example.ConstructionCompany.service.filter.CustomRsqlVisitor
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import cz.jirutka.rsql.parser.RSQLParser
import cz.jirutka.rsql.parser.ast.Node
import org.hibernate.JDBCException
import org.hibernate.exception.ConstraintViolationException
import org.hibernate.exception.GenericJDBCException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Persistable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.PagedModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.Serializable
import java.lang.reflect.ParameterizedType
import javax.servlet.http.HttpServletRequest

/*Абстрактный контроллер с основными crud операциями*/
abstract class AbstractController<ID : Serializable, T : Persistable<ID>>(
    private val service: AbstractService<T, ID>,
    private val assembler: AbstractModelAssembler<T>
) {
    val entityClass =
        ((javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<Persistable<*>>).kotlin


    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: ID,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<T>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<T>>> {
        val page = service.findById(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }


    @GetMapping("/", "")
    fun getAll(
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<T>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<T>>> {
        val page = service.findAll(pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }

    @PutMapping("/collection")
    fun saveAll(
        @RequestBody json: String
    ): ResponseEntity<*> {
        val objectMapper = jacksonObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        val typeFactory = objectMapper.typeFactory
        val collection = objectMapper.readValue<Collection<T>>(
            json,
            typeFactory.constructCollectionType(Collection::class.java, entityClass.java)
        )
        return try {
            ResponseEntity.ok(service.saveAll(collection))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body((e.cause as JDBCException).sqlException.cause?.message.orEmpty())
        }
    }


    @DeleteMapping("/collection")
    fun deleteAll(
        @RequestParam("id") ids: Collection<ID>
    ) = service.deleteByIdIn(ids).let {
        ResponseEntity.ok().build<Any>()
    }

    @GetMapping("/search")
    @ResponseBody
    open fun findAllByRsql(@RequestParam("filter") filter: String,
                           pageable: Pageable,
                           pagedAssembler: PagedResourcesAssembler<T>,
                           request: HttpServletRequest): ResponseEntity<PagedModel<EntityModel<T>>> {
        val page = service.findAll(convertToSpecifications(filter), pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }

    protected fun <S: Persistable<*>>convertToSpecifications(filter: String): Specification<S>? {
        val rootNode: Node = RSQLParser().parse(filter)
        return rootNode.accept(CustomRsqlVisitor())
    }

    protected fun <T: Persistable<*>>toResponse(
        pagedAssembler: PagedResourcesAssembler<T>,
        page: Page<T>,
        assembler: AbstractModelAssembler<T>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<T>>> {
        return ResponseEntity.ok(
            pagedAssembler.toModel(
                page,
                assembler,
                applyBasePath(Link.of(request.servletPath))
            )
        )

    }
}

/*Реализации контроллеров добавляют методы для поиска ссылающихся сущностей по ссылаемому id*/

@RestController
@RequestMapping(value = ["/brigades"])
class BrigadeController(
    private val service: BrigadeService,
    private val assembler: BrigadeModelAssembler
) : AbstractController<Long, Brigade>(service, assembler) {
    @GetMapping("/title/{id}")
    fun findAllByTitleId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<Brigade>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<Brigade>>> {
        val page = service.findAllByTitleId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }
}

@RestController
@RequestMapping(value = ["/brigadeMembers"])
class BrigadeMemberController(
    private val service: BrigadeMemberService,
    private val assembler: BrigadeMemberModelAssembler
) : AbstractController<Long, BrigadeMember>(service, assembler) {

    @GetMapping("/brigade/{id}")
    fun findAllByBrigadeId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<BrigadeMember>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<BrigadeMember>>> {
        val page = service.findAllByBrigadeId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }

    @GetMapping("/staff/{id}")
    fun findAllByStaffId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<BrigadeMember>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<BrigadeMember>>> {
        val page = service.findAllByStaffId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }
}

@RestController
@RequestMapping(value = ["/buildObjects"])
class BuildObjectController(
    private val service: BuildObjectService,
    private val assembler: BuildObjectModelAssembler
) : AbstractController<Long, BuildObject>(service, assembler) {

    @GetMapping("/prototype/{id}")
    fun findAllByPrototypeId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<BuildObject>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<BuildObject>>> {
        val page = service.findAllByPrototypeId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }

    @GetMapping("/plot/{id}")
    fun findAllByPlotId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<BuildObject>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<BuildObject>>> {
        val page = service.findAllByPlotId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }

    @GetMapping("/customer/{id}")
    fun findAllByCustomerId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<BuildObject>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<BuildObject>>> {
        val page = service.findAllByCustomerId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }
}

@RestController
@RequestMapping(value = ["/estimates"])
class EstimateController(
    private val service: EstimateService,
    private val assembler: EstimateModelAssembler
) : AbstractController<Long, Estimate>(service, assembler) {

    @GetMapping("/workSchedule/{id}")
    fun findAllByWorkScheduleId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<Estimate>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<Estimate>>> {
        val page = service.findAllByWorkScheduleId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }


    @GetMapping("/material/{id}")
    fun findAllByMaterialId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<Estimate>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<Estimate>>> {
        val page = service.findAllByMaterialId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }
}

@RestController
@RequestMapping(value = ["/machines"])
class MachineryController(
    private val service: MachineryService,
    private val assembler: MachineryModelAssembler
) : AbstractController<Long, Machinery>(service, assembler) {

    @GetMapping("/model/{id}")
    fun findAllByModelId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<Machinery>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<Machinery>>> {
        val page = service.findAllByModelId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }

    @GetMapping("/management/{id}")
    fun findAllByManagementId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<Machinery>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<Machinery>>> {
        val page = service.findAllByManagementId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }
}

@RestController
@RequestMapping(value = ["/machineryModels"])
class MachineryModelController(
    private val service: MachineryModelService,
    private val assembler: MachineryModelModelAssembler
) : AbstractController<Long, MachineryModel>(service, assembler) {

    @GetMapping("/machineryType/{id}")
    fun findAllByMachineryTypeId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<MachineryModel>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<MachineryModel>>> {
        val page = service.findAllByMachineryTypeId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }
}

@RestController
@RequestMapping(value = ["/managements"])
class ManagementController(
    private val service: ManagementService,
    private val assembler: ManagementModelAssembler
) : AbstractController<Long, Management>(service, assembler) {

    @GetMapping("/chief/{id}")
    fun findAllByChiefId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<Management>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<Management>>> {
        val page = service.findAllByChiefId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }
}

@RestController
@RequestMapping(value = ["/materialConsumptions"])
class MaterialConsumptionController(
    private val service: MaterialConsumptionService,
    private val assembler: MaterialConsumptionModelAssembler
) : AbstractController<Long, MaterialConsumption>(service, assembler) {

    @GetMapping("/objectBrigade/{id}")
    fun findAllByObjectBrigadeId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<MaterialConsumption>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<MaterialConsumption>>> {
        val page = service.findAllByObjectBrigadeId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }

    @GetMapping("/estimate/{id}")
    fun findAllByEstimateId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<MaterialConsumption>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<MaterialConsumption>>> {
        val page = service.findAllByEstimateId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }
}

@RestController
@RequestMapping(value = ["/objectBrigades"])
class ObjectBrigadeController(
    private val service: ObjectBrigadeService,
    private val assembler: ObjectBrigadeModelAssembler
) : AbstractController<Long, ObjectBrigade>(service, assembler) {

    @GetMapping("/brigade/{id}")
    fun findAllByBrigadeId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<ObjectBrigade>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<ObjectBrigade>>> {
        val page = service.findAllByBrigadeId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }

    @GetMapping("/buildObject/{id}")
    fun findAllByBuildObjectId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<ObjectBrigade>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<ObjectBrigade>>> {
        val page = service.findAllByBuildObjectId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }

    @GetMapping("/workSchedule/{id}")
    fun findAllByWorkScheduleId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<ObjectBrigade>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<ObjectBrigade>>> {
        val page = service.findAllByWorkScheduleId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }

}

@RestController
@RequestMapping(value = ["/objectMachines"])
class ObjectMachineryController(
    private val service: ObjectMachineryService,
    private val assembler: ObjectMachineryModelAssembler
) : AbstractController<Long, ObjectMachinery>(service, assembler) {

    @GetMapping("/buildObject/{id}")
    fun findAllByBuildObjectId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<ObjectMachinery>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<ObjectMachinery>>> {
        val page = service.findAllByBuildObjectId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }

    @GetMapping("/machinery/{id}")
    fun findAllByMachineryId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<ObjectMachinery>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<ObjectMachinery>>> {
        val page = service.findAllByMachineryId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }


}


@RestController
@RequestMapping(value = ["/plots"])
class PlotController(
    private val service: PlotService,
    private val assembler: PlotModelAssembler
) : AbstractController<Long, Plot>(service, assembler) {

    @GetMapping("/chief/{id}")
    fun findAllByChiefId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<Plot>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<Plot>>> {
        val page = service.findAllByChiefId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }

    @GetMapping("/management/{id}")
    fun findAllByManagementId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<Plot>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<Plot>>> {
        val page = service.findAllByManagementId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }
}

@RestController
@RequestMapping(value = ["/prototypes"])
class PrototypeController(
    private val service: PrototypeService,
    private val assembler: PrototypeModelAssembler
) : AbstractController<Long, Prototype>(service, assembler) {

    @GetMapping("/prototypeType/{id}")
    fun findAllByPrototypeTypeId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<Prototype>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<Prototype>>> {
        val page = service.findAllByPrototypeTypeId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }

}


@RestController
@RequestMapping(value = ["/titles"])
class TitleController(
    private val service: TitleService,
    private val assembler: TitleModelAssembler
) : AbstractController<Long, Title>(service, assembler) {

    @GetMapping("/titleCategory/{id}")
    fun findAllByTitleCategoryId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<Title>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<Title>>> {
        val page = service.findAllByTitleCategoryId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }
}

@RestController
@RequestMapping(value = ["/titleCategories"])
class TitleCategoryController(
    private val service: TitleCategoryService,
    private val assembler: TitleCategoryModelAssembler
) : AbstractController<Long, TitleCategory>(service, assembler)

@RestController
@RequestMapping(value = ["/workTypes"])
class WorkTypeController(
    private val service: WorkTypeService,
    private val assembler: WorkTypeModelAssembler
) : AbstractController<Long, WorkType>(service, assembler)

@RestController
@RequestMapping(value = ["/prototypeTypes"])
class PrototypeTypeController(
    private val service: PrototypeTypeService,
    private val assembler: PrototypeTypeModelAssembler
) : AbstractController<Long, PrototypeType>(service, assembler)

@RestController
@RequestMapping(value = ["/materials"])
class MaterialController(
    private val service: MaterialService,
    private val assembler: MaterialModelAssembler
) : AbstractController<Long, Material>(service, assembler)

@RestController
@RequestMapping(value = ["/machineryTypes"])
class MachineryTypeController(
    private val service: MachineryTypeService,
    private val assembler: MachineryTypeModelAssembler
) : AbstractController<Long, MachineryType>(service, assembler)

@RestController
@RequestMapping(value = ["/customers"])
class CustomerController(
    private val service: CustomerService,
    private val assembler: CustomerModelAssembler
) : AbstractController<Long, Customer>(service, assembler)

@RestController
@RequestMapping(value = ["/staff"])
class StaffController(
    private val service: StaffService,
    private val assembler: StaffModelAssembler
) : AbstractController<Long, Staff>(service, assembler) {
    @GetMapping("/title/{id}")
    fun findAllByTitleId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<Staff>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<Staff>>> {
        val page = service.findAllByTitleId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }

    @GetMapping("/chief/{id}")
    fun findAllByChiefId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<Staff>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<Staff>>> {
        val page = service.findAllByChiefId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }
}

@RestController
@RequestMapping(value = ["/workSchedules"])
class WorkScheduleController(
    private val service: WorkScheduleService,
    private val assembler: WorkScheduleModelAssembler
) : AbstractController<Long, WorkSchedule>(service, assembler) {
    @GetMapping("/prototype/{id}")
    fun findAllByPrototypeId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<WorkSchedule>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<WorkSchedule>>> {
        val page = service.findAllByPrototypeId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }

    @GetMapping("/workType/{id}")
    fun findAllByWorkTypeId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<WorkSchedule>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<WorkSchedule>>> {
        val page = service.findAllByWorkTypeId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }
}


@RestController
@RequestMapping(value = ["/report"])
class ReportController(
    private val service: ReportService,
    private val assembler: ReportModelAssembler
) : AbstractController<Long, Report>(service, assembler)

@RestController
@RequestMapping(value = ["/materialConsumptionReport"])
class MaterialConsumptionReportController(
    private val service: MaterialConsumptionReportService,
    private val assembler: MaterialConsumptionReportModelAssembler
) : AbstractController<Long, MaterialConsumptionReport>(service, assembler) {
    @GetMapping("/report/{id}")
    fun findAllByReportId(
        @PathVariable id: Long,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<MaterialConsumptionReport>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<MaterialConsumptionReport>>> {
        val page = service.findAllByReportId(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }
}