package com.example.ConstructionCompany.controller.processor

import com.example.ConstructionCompany.controller.AbstractController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.data.domain.Persistable
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
        val map = mutableMapOf<KClass<Persistable<*>>, AbstractController<*, *>>()
        for (controller in controllerMap.values) {
            map[controller.entityClass] = controller
        }
        map
    }

    private val manyToOnePropertiesMap: Map<KClass<Persistable<*>>, Set<KProperty1<*, *>>> by lazy {
        val map = mutableMapOf<KClass<Persistable<*>>, MutableSet<KProperty1<*, *>>>()
        entityControllerMap.keys.forEach { map[it] = mutableSetOf() }
        for (entity in entityControllerMap.keys) {
            val properties = entity::memberProperties.get()
            for (property in properties) {
                val propertyClass = property.returnType.classifier as KClass<*>
                if (propertyClass in entityControllerMap.keys) {
                    map[entity]!!.add(property)
                }
            }
        }
        map
    }

    private val oneToManyFunctionsMap: Map<KClass<Persistable<*>>, Set<Pair<KFunction<*>, String>>> by lazy {
        val map = mutableMapOf<KClass<Persistable<*>>, MutableSet<Pair<KFunction<*>, String>>>()
        entityControllerMap.keys.forEach { map[it] = mutableSetOf() }
        for (entry in manyToOnePropertiesMap.entries) {
            val controller = map(entry.key)
            for (property in entry.value) {
                val function = controller::class::memberFunctions.get().firstOrNull {
                    it.name == ONE_TO_MANY_FUN_NAME_PREFIX + property.name.capitalize() + ONE_TO_MANY_FUN_NAME_SUFFIX
                } ?: continue

                map[property.returnType.classifier]!!.add(Pair(function, property.name))
            }

        }
        map
    }

    fun getManyToOneProperties(kClass: KClass<Persistable<*>>) = manyToOnePropertiesMap.getValue(kClass)

    fun getOneToManyFunctions(kClass: KClass<Persistable<*>>) = oneToManyFunctionsMap.getValue(kClass)

    fun map(kClass: KClass<Persistable<*>>): AbstractController<*, *> {
        return entityControllerMap.getValue(kClass)
    }

    companion object {
        private const val ONE_TO_MANY_FUN_NAME_PREFIX = "findAllBy"
        private const val ONE_TO_MANY_FUN_NAME_SUFFIX = "Id"
    }
}