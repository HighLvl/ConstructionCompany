package com.example.ConstructionCompany.controller.processor

import com.example.ConstructionCompany.applyBasePath
import com.example.ConstructionCompany.controller.AbstractController
import com.example.ConstructionCompany.entity.*
import org.springframework.data.domain.Persistable
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.server.RepresentationModelProcessor
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty1
import kotlin.reflect.full.findAnnotation

abstract class AbstractPagedModelProcessor<T : Persistable<*>>(
    entityClass: KClass<T>,
    private val processorHelper: ProcessorHelper
) : RepresentationModelProcessor<PagedModel<EntityModel<T>>> {

    protected val entityClass: KClass<Persistable<*>> = entityClass as KClass<Persistable<*>>

    override fun process(model: PagedModel<EntityModel<T>>): PagedModel<EntityModel<T>> {
        model.addManyToOneLinks()
        model.addOneToManyLinks()
        return model
    }


    private fun PagedModel<EntityModel<T>>.addManyToOneLinks() {
        val properties = processorHelper.getManyToOneProperties(entityClass)
        for (property in properties) {
            val entityControllerClass =
                processorHelper.map(property.returnType.classifier as KClass<Persistable<*>>)::class as KClass<AbstractController<*, *>>
            val requestMappingPath = requestMappingUrl(entityControllerClass).trimEnd('/')
            val relation = MANY_TO_ONE_PREFIX + "_" + property.name
            addManyToOneLink(requestMappingPath, relation)
            content.forEach { it.addManyToOneLink(requestMappingPath, property as KProperty1<T, *>, relation) }
        }
    }

    private fun PagedModel<EntityModel<T>>.addOneToManyLinks() {
        val functionPropertyNamePairs =
            processorHelper.getOneToManyFunctions(entityClass)
        for (pair in functionPropertyNamePairs) {
            val requestMappingPath = requestMappingUrl(pair.first).trimEnd('/')
            val relation = ONE_TO_MANY_PREFIX + "_" + requestMappingPath.trimStart('/') + "_" + pair.second
            addOneToManyLink(requestMappingPath, relation)
            content.forEach { it.addOneToManyLink(pair.first, requestMappingPath, relation) }
        }
    }

    private fun PagedModel<EntityModel<T>>.addOneToManyLink(requestMappingPath: String, relation: String) {
        this.add(applyBasePath(Link.of(requestMappingPath).withRel(relation)))
    }

    private fun EntityModel<T>.addOneToManyLink(function: KFunction<*>, requestMappingPath: String, relation: String) {
        val getMappingPath = function.findAnnotation<GetMapping>()!!.value[0]
        this.add(applyBasePath(Link.of(requestMappingPath + getMappingPath).expand(content!!.id).withRel(relation)))
    }


    private fun PagedModel<EntityModel<T>>.addManyToOneLink(requestMappingPath: String, relation: String) {
        this.add(applyBasePath(Link.of(requestMappingPath).withRel(relation)))
    }

    private fun EntityModel<T>.addManyToOneLink(
        requestMappingPath: String,
        property: KProperty1<T, *>,
        relation: String
    ) {
        val persistable = (property.get(this.content!!) ?: return) as Persistable<*>
        val id = persistable.id
        val getMappingPath = AbstractController<*, *>::getById.findAnnotation<GetMapping>()!!.value[0]
        this.add(applyBasePath(Link.of(requestMappingPath + getMappingPath).expand(id).withRel(relation)))

    }


    private fun requestMappingUrl(kClass: KClass<AbstractController<*, *>>): String {
        return kClass.findAnnotation<RequestMapping>()!!.value[0]
    }

    private fun requestMappingUrl(function: KFunction<*>): String {
        return (function.parameters[0].type.classifier as KClass<*>).findAnnotation<RequestMapping>()!!.value[0]
    }

    companion object {
        const val ONE_TO_MANY_PREFIX = "referencing"
        const val MANY_TO_ONE_PREFIX = "referred"
    }
}

@Component
class BrigadeProcessor(processorHelper: ProcessorHelper) :
    AbstractPagedModelProcessor<Brigade>(Brigade::class, processorHelper)

@Component
class TitleCategoryProcessor(processorHelper: ProcessorHelper) :
    AbstractPagedModelProcessor<TitleCategory>(TitleCategory::class, processorHelper)

@Component
class TitleProcessor(processorHelper: ProcessorHelper) :
    AbstractPagedModelProcessor<Title>(Title::class, processorHelper)

@Component
class BrigadeMemberProcessor(processorHelper: ProcessorHelper) :
    AbstractPagedModelProcessor<BrigadeMember>(BrigadeMember::class, processorHelper)

@Component
class BuildObjectProcessor(processorHelper: ProcessorHelper) :
    AbstractPagedModelProcessor<BuildObject>(BuildObject::class, processorHelper)

@Component
class CustomerProcessor(processorHelper: ProcessorHelper) :
    AbstractPagedModelProcessor<Customer>(Customer::class, processorHelper)

@Component
class EstimateProcessor(processorHelper: ProcessorHelper) :
    AbstractPagedModelProcessor<Estimate>(Estimate::class, processorHelper)

@Component
class MachineryProcessor(processorHelper: ProcessorHelper) :
    AbstractPagedModelProcessor<Machinery>(Machinery::class, processorHelper)

@Component
class MachineryModelProcessor(processorHelper: ProcessorHelper) :
    AbstractPagedModelProcessor<MachineryModel>(MachineryModel::class, processorHelper)

@Component
class MachineryTypeProcessor(processorHelper: ProcessorHelper) :
    AbstractPagedModelProcessor<MachineryType>(MachineryType::class, processorHelper)

@Component
class ManagementProcessor(processorHelper: ProcessorHelper) :
    AbstractPagedModelProcessor<Management>(Management::class, processorHelper)

@Component
class MaterialProcessor(processorHelper: ProcessorHelper) :
    AbstractPagedModelProcessor<Material>(Material::class, processorHelper)

@Component
class MaterialConsumptionProcessor(processorHelper: ProcessorHelper) :
    AbstractPagedModelProcessor<MaterialConsumption>(MaterialConsumption::class, processorHelper)

@Component
class ObjectBrigadeProcessor(processorHelper: ProcessorHelper) :
    AbstractPagedModelProcessor<ObjectBrigade>(ObjectBrigade::class, processorHelper)

@Component
class ObjectMachineryProcessor(processorHelper: ProcessorHelper) :
    AbstractPagedModelProcessor<ObjectMachinery>(ObjectMachinery::class, processorHelper)

@Component
class PlotProcessor(processorHelper: ProcessorHelper) : AbstractPagedModelProcessor<Plot>(Plot::class, processorHelper)

@Component
class PrototypeProcessor(processorHelper: ProcessorHelper) :
    AbstractPagedModelProcessor<Prototype>(Prototype::class, processorHelper)

@Component
class PrototypeTypeProcessor(processorHelper: ProcessorHelper) :
    AbstractPagedModelProcessor<PrototypeType>(PrototypeType::class, processorHelper)

@Component
class StaffProcessor(processorHelper: ProcessorHelper) :
    AbstractPagedModelProcessor<Staff>(Staff::class, processorHelper)

@Component
class WorkScheduleProcessor(processorHelper: ProcessorHelper) :
    AbstractPagedModelProcessor<WorkSchedule>(WorkSchedule::class, processorHelper)

@Component
class WorkTypeProcessor(processorHelper: ProcessorHelper) :
    AbstractPagedModelProcessor<WorkType>(WorkType::class, processorHelper)

@Component
class ReportProcessor(processorHelper: ProcessorHelper) :
    AbstractPagedModelProcessor<Report>(Report::class, processorHelper)