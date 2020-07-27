package com.example.ConstructionCompany.controller.processor

import com.example.ConstructionCompany.applyBasePath
import com.example.ConstructionCompany.entity.*
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.server.RepresentationModelProcessor
import org.springframework.stereotype.Component

abstract class AbstractProcessor<T : AbstractJpaPersistable<*>> : RepresentationModelProcessor<EntityModel<T>> {
    protected fun EntityModel<T>.addLink(href: String, relation: String) {
        this.add(applyBasePath(Link.of(href).withRel(relation)))
    }
}

@Component
class BrigadeProcessor : AbstractProcessor<Brigade>() {
    override fun process(model: EntityModel<Brigade>): EntityModel<Brigade> {
        model.addLink("/brigadeMembers/brigade/${model.content?.id}", "members")
        model.addLink("/objectBrigades/brigade/${model.content?.id}", "objects")
        model.addLink("/titles/${model.content?.title?.id}", "title")
        return model
    }
}

@Component
class TitleCategoryProcessor : AbstractProcessor<TitleCategory>() {
    override fun process(model: EntityModel<TitleCategory>): EntityModel<TitleCategory> {
        model.addLink("/titles/titleCategory/${model.content?.id}", "titles")
        return model
    }
}

@Component
class TitleProcessor : AbstractProcessor<Title>() {
    override fun process(model: EntityModel<Title>): EntityModel<Title> {
        model.addLink("/titleCategories/${model.content?.titleCategory?.id}", "titleCategory")
        model.addLink("/brigades/title/${model.content?.id}", "brigades")
        model.addLink("/staff/title/${model.content?.id}", "staff")
        return model
    }
}

@Component
class BrigadeMemberProcessor : AbstractProcessor<BrigadeMember>() {
    override fun process(model: EntityModel<BrigadeMember>): EntityModel<BrigadeMember> {
        TODO("Not yet implemented")
    }
}

@Component
class BuildObjectProcessor : AbstractProcessor<BuildObject>() {
    override fun process(model: EntityModel<BuildObject>): EntityModel<BuildObject> {
        TODO("Not yet implemented")
    }
}

@Component
class CustomerProcessor : AbstractProcessor<Customer>() {
    override fun process(model: EntityModel<Customer>): EntityModel<Customer> {
        TODO("Not yet implemented")
    }
}

@Component
class EstimateProcessor : AbstractProcessor<Estimate>() {
    override fun process(model: EntityModel<Estimate>): EntityModel<Estimate> {
        TODO("Not yet implemented")
    }
}

@Component
class MachineryProcessor : AbstractProcessor<Machinery>() {
    override fun process(model: EntityModel<Machinery>): EntityModel<Machinery> {
        TODO("Not yet implemented")
    }
}

@Component
class MachineryModelProcessor : AbstractProcessor<MachineryModel>() {
    override fun process(model: EntityModel<MachineryModel>): EntityModel<MachineryModel> {
        TODO("Not yet implemented")
    }
}

@Component
class MachineryTypeProcessor : AbstractProcessor<MachineryType>() {
    override fun process(model: EntityModel<MachineryType>): EntityModel<MachineryType> {
        TODO("Not yet implemented")
    }
}

@Component
class ManagementProcessor : AbstractProcessor<Management>() {
    override fun process(model: EntityModel<Management>): EntityModel<Management> {
        TODO("Not yet implemented")
    }
}

@Component
class MaterialProcessor : AbstractProcessor<Material>() {
    override fun process(model: EntityModel<Material>): EntityModel<Material> {
        TODO("Not yet implemented")
    }
}

@Component
class MaterialConsumptionProcessor : AbstractProcessor<MaterialConsumption>() {
    override fun process(model: EntityModel<MaterialConsumption>): EntityModel<MaterialConsumption> {
        TODO("Not yet implemented")
    }
}

@Component
class ObjectBrigadeProcessor : AbstractProcessor<ObjectBrigade>() {
    override fun process(model: EntityModel<ObjectBrigade>): EntityModel<ObjectBrigade> {
        TODO("Not yet implemented")
    }
}

@Component
class ObjectMachineryProcessor : AbstractProcessor<ObjectMachinery>() {
    override fun process(model: EntityModel<ObjectMachinery>): EntityModel<ObjectMachinery> {
        TODO("Not yet implemented")
    }
}

@Component
class PlotProcessor : AbstractProcessor<Plot>() {
    override fun process(model: EntityModel<Plot>): EntityModel<Plot> {
        TODO("Not yet implemented")
    }
}

@Component
class PrototypeProcessor : AbstractProcessor<Prototype>() {
    override fun process(model: EntityModel<Prototype>): EntityModel<Prototype> {
        TODO("Not yet implemented")
    }
}

@Component
class PrototypeTypeProcessor : AbstractProcessor<PrototypeType>() {
    override fun process(model: EntityModel<PrototypeType>): EntityModel<PrototypeType> {
        TODO("Not yet implemented")
    }
}

@Component
class StaffProcessor : AbstractProcessor<Staff>() {
    override fun process(model: EntityModel<Staff>): EntityModel<Staff> {
        TODO("Not yet implemented")
    }
}

@Component
class WorkScheduleProcessor : AbstractProcessor<WorkSchedule>() {
    override fun process(model: EntityModel<WorkSchedule>): EntityModel<WorkSchedule> {
        TODO("Not yet implemented")
    }
}

@Component
class WorkTypeProcessor : AbstractProcessor<WorkType>() {
    override fun process(model: EntityModel<WorkType>): EntityModel<WorkType> {
        TODO("Not yet implemented")
    }
}