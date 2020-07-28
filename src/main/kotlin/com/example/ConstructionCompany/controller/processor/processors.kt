package com.example.ConstructionCompany.controller.processor

import com.example.ConstructionCompany.applyBasePath
import com.example.ConstructionCompany.controller.AbstractController
import com.example.ConstructionCompany.entity.*
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.server.RepresentationModelProcessor
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty1
import kotlin.reflect.full.findAnnotation

abstract class AbstractProcessor<T : AbstractJpaPersistable<*>>(private val processorHelper: ProcessorHelper) :
    RepresentationModelProcessor<EntityModel<T>> {

    override fun process(model: EntityModel<T>): EntityModel<T> {
        model.addManyToOneLinks()
        model.addOneToManyLinks()
        return model
    }

    private fun EntityModel<T>.addManyToOneLinks() {
        val properties =
            processorHelper.getManyToOneProperties(content!!::class as KClass<AbstractJpaPersistable<*>>)
        for (property in properties) {
            addManyToOneLink(property as KProperty1<T, *>)
        }
    }

    private fun EntityModel<T>.addOneToManyLinks() {
        val functionPropertyNamePairs =
            processorHelper.getOneToManyFunctions(content!!::class as KClass<AbstractJpaPersistable<*>>)
        for (pair in functionPropertyNamePairs) {
            addOneToManyLink(pair.first, pair.second)
        }
    }

    private fun EntityModel<T>.addOneToManyLink(function: KFunction<*>, propertyName: String) {
        addLink(
            function,
            this.content!!.id!!,
             function.requestMappingUrl().trim('/').capitalize() + "_" + propertyName.capitalize()
        )
    }

    private fun EntityModel<T>.addManyToOneLink(
        property: KProperty1<T, *>
    ) {
        val persistable = (property.get(this.content!!) ?: return) as AbstractJpaPersistable<*>
        val entityController = processorHelper.map(persistable::class as KClass<AbstractJpaPersistable<*>>)
        addLink(
            entityController::class as KClass<AbstractController<*, *>>,
            entityController::getById,
            persistable.id!!,
            property.name
        )
    }

    private fun EntityModel<T>.addLink(
        kClass: KClass<AbstractController<*, *>>,
        function: KFunction<*>,
        id: Any,
        relation: String
    ) {
        this.add(applyBasePath(function.link(kClass).expand(id).withRel(relation)))
    }

    private fun EntityModel<T>.addLink(function: KFunction<*>, id: Any, relation: String) {
        this.add(applyBasePath(function.link().expand(id).withRel(relation)))
    }

    private fun KFunction<*>.link(kClass: KClass<AbstractController<*, *>>): Link {
        val getMappingUrl = this.findAnnotation<GetMapping>()!!.value[0]
        return Link.of((requestMappingUrl(kClass).trimEnd('/') + getMappingUrl))
    }

    private fun KFunction<*>.link(): Link {
        val getMappingUrl = this.findAnnotation<GetMapping>()!!.value[0]
        return Link.of((requestMappingUrl().trimEnd('/') + getMappingUrl))
    }


    private fun requestMappingUrl(kClass: KClass<AbstractController<*, *>>): String {
        return kClass.findAnnotation<RequestMapping>()!!.value[0]
    }

    private fun KFunction<*>.requestMappingUrl(): String {
        return (this.parameters[0].type.classifier as KClass<*>).findAnnotation<RequestMapping>()!!.value[0]
    }
}

@Component
class BrigadeProcessor(processorHelper: ProcessorHelper) : AbstractProcessor<Brigade>(
    processorHelper
)

@Component
class TitleCategoryProcessor(processorHelper: ProcessorHelper) : AbstractProcessor<TitleCategory>(
    processorHelper
)

@Component
class TitleProcessor(processorHelper: ProcessorHelper) :
    AbstractProcessor<Title>(processorHelper)

@Component
class BrigadeMemberProcessor(processorHelper: ProcessorHelper) : AbstractProcessor<BrigadeMember>(
    processorHelper
)

@Component
class BuildObjectProcessor(processorHelper: ProcessorHelper) : AbstractProcessor<BuildObject>(
    processorHelper
)

@Component
class CustomerProcessor(processorHelper: ProcessorHelper) : AbstractProcessor<Customer>(
    processorHelper
)

@Component
class EstimateProcessor(processorHelper: ProcessorHelper) : AbstractProcessor<Estimate>(
    processorHelper
)

@Component
class MachineryProcessor(processorHelper: ProcessorHelper) : AbstractProcessor<Machinery>(
    processorHelper
)

@Component
class MachineryModelProcessor(processorHelper: ProcessorHelper) : AbstractProcessor<MachineryModel>(
    processorHelper
)

@Component
class MachineryTypeProcessor(processorHelper: ProcessorHelper) : AbstractProcessor<MachineryType>(
    processorHelper
)

@Component
class ManagementProcessor(processorHelper: ProcessorHelper) : AbstractProcessor<Management>(
    processorHelper
)

@Component
class MaterialProcessor(processorHelper: ProcessorHelper) : AbstractProcessor<Material>(
    processorHelper
)

@Component
class MaterialConsumptionProcessor(processorHelper: ProcessorHelper) :
    AbstractProcessor<MaterialConsumption>(
        processorHelper
    )

@Component
class ObjectBrigadeProcessor(processorHelper: ProcessorHelper) : AbstractProcessor<ObjectBrigade>(
    processorHelper
)

@Component
class ObjectMachineryProcessor(processorHelper: ProcessorHelper) : AbstractProcessor<ObjectMachinery>(
    processorHelper
)

@Component
class PlotProcessor(processorHelper: ProcessorHelper) : AbstractProcessor<Plot>(processorHelper)

@Component
class PrototypeProcessor(processorHelper: ProcessorHelper) : AbstractProcessor<Prototype>(
    processorHelper
)

@Component
class PrototypeTypeProcessor(processorHelper: ProcessorHelper) : AbstractProcessor<PrototypeType>(
    processorHelper
)

@Component
class StaffProcessor(processorHelper: ProcessorHelper) :
    AbstractProcessor<Staff>(processorHelper)

@Component
class WorkScheduleProcessor(processorHelper: ProcessorHelper) : AbstractProcessor<WorkSchedule>(
    processorHelper
)

@Component
class WorkTypeProcessor(processorHelper: ProcessorHelper) : AbstractProcessor<WorkType>(
    processorHelper
)