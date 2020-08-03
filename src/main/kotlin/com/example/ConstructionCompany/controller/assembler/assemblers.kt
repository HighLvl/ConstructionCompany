package com.example.ConstructionCompany.controller.assembler

import com.example.ConstructionCompany.entity.*
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.RepresentationModelAssembler
import org.springframework.stereotype.Component
import org.springframework.util.Assert
import java.util.*

@Component
class TitleCategoryModelAssembler : AbstractModelAssembler<TitleCategory>()

@Component
class TitleModelAssembler : AbstractModelAssembler<Title>()

@Component
class BuildObjectModelAssembler : AbstractModelAssembler<BuildObject>()

@Component
class CustomerModelAssembler : AbstractModelAssembler<Customer>()

@Component
class EstimateModelAssembler : AbstractModelAssembler<Estimate>()

@Component
class MachineryModelAssembler : AbstractModelAssembler<Machinery>()

@Component
class MachineryModelModelAssembler : AbstractModelAssembler<MachineryModel>()

@Component
class MachineryTypeModelAssembler : AbstractModelAssembler<MachineryType>()

@Component
class ManagementModelAssembler : AbstractModelAssembler<Management>()

@Component
class MaterialModelAssembler : AbstractModelAssembler<Material>()

@Component
class MaterialConsumptionModelAssembler : AbstractModelAssembler<MaterialConsumption>()

@Component
class ObjectBrigadeModelAssembler : AbstractModelAssembler<ObjectBrigade>()

@Component
class ObjectMachineryModelAssembler : AbstractModelAssembler<ObjectMachinery>()

@Component
class PlotModelAssembler : AbstractModelAssembler<Plot>()

@Component
class PrototypeModelAssembler : AbstractModelAssembler<Prototype>()

@Component
class PrototypeTypeModelAssembler : AbstractModelAssembler<PrototypeType>()

@Component
class StaffModelAssembler : AbstractModelAssembler<Staff>()

@Component
class WorkScheduleModelAssembler : AbstractModelAssembler<WorkSchedule>()

@Component
class WorkTypeModelAssembler : AbstractModelAssembler<WorkType>()

@Component
class BrigadeMemberModelAssembler : AbstractModelAssembler<BrigadeMember>()

@Component
class BrigadeModelAssembler : AbstractModelAssembler<Brigade>()

abstract class AbstractModelAssembler<T : AbstractJpaPersistable<*>> :
    RepresentationModelAssembler<T, EntityModel<T>> {
    override fun toCollectionModel(entities: MutableIterable<T>): CollectionModel<EntityModel<T>> {
        Assert.notNull(entities, "entities must not be null!")
        val resourceList: MutableList<EntityModel<T>> =
            ArrayList()

        for (entity in entities) {
            resourceList.add(toModel(entity))
        }

        val resources = CollectionModel.of(resourceList)
        return resources
    }

    override fun toModel(entity: T): EntityModel<T> =
        EntityModel.of(entity)
}