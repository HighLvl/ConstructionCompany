package com.example.ConstructionCompany.controller.processor

import com.example.ConstructionCompany.controller.AbstractController
import com.example.ConstructionCompany.entity.AbstractJpaPersistable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RestController
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.memberProperties


@Component
class ProcessorHelper {
    @Autowired
    private lateinit var context: ApplicationContext
    private val entityControllerMap by lazy {
        val controllerMap =
            context.getBeansWithAnnotation(RestController::class.java) as Map<String, AbstractController<*, *>>
        val map = mutableMapOf<KClass<AbstractJpaPersistable<*>>, AbstractController<*, *>>()
        for (controller in controllerMap.values) {
            map[controller.entityClass] = controller
        }
        map
    }

    private val manyToOnePropertiesMap: Map<KClass<AbstractJpaPersistable<*>>, Set<KProperty1<*, *>>> by lazy {
        val map = mutableMapOf<KClass<AbstractJpaPersistable<*>>, MutableSet<KProperty1<*, *>>>()
        entityControllerMap.keys.forEach { map[it] = mutableSetOf() }
        for (entity in entityControllerMap.keys) {
            val properties = entity::memberProperties.get() as Collection<KProperty1<AbstractJpaPersistable<*>, *>>
            for (property in properties) {
                val propertyClass = property.returnType.classifier as KClass<*>
                if (propertyClass in entityControllerMap.keys) {
                    map[entity]!!.add(property)
                }
            }
        }
        map
    }

    private val oneToManyFunctionsMap: Map<KClass<AbstractJpaPersistable<*>>, Set<Pair<KFunction<*>, String>>> by lazy {
        val map = mutableMapOf<KClass<AbstractJpaPersistable<*>>, MutableSet<Pair<KFunction<*>, String>>>()
        entityControllerMap.keys.forEach { map[it] = mutableSetOf() }
        for (entry in manyToOnePropertiesMap.entries) {
            val controller = map(entry.key)
            for (property in entry.value) {
                val function = controller::class::memberFunctions.get().firstOrNull {
                    it.name == "findAllBy" + property.name.capitalize() + "Id"
                } ?: continue

                map[property.returnType.classifier]!!.add(Pair(function, property.name))
            }

        }
        map
    }

    fun getManyToOneProperties(kClass: KClass<AbstractJpaPersistable<*>>) = manyToOnePropertiesMap.getValue(kClass)

    fun getOneToManyFunctions(kClass: KClass<AbstractJpaPersistable<*>>) = oneToManyFunctionsMap.getValue(kClass)

    fun map(kClass: KClass<AbstractJpaPersistable<*>>): AbstractController<*, *> {
        return entityControllerMap.getValue(kClass)
    }


}