package com.example.ConstructionCompany.controller.processor

import com.example.ConstructionCompany.applyBasePath
import com.example.ConstructionCompany.entity.AbstractJpaPersistable
import com.example.ConstructionCompany.entity.Brigade
import com.example.ConstructionCompany.entity.Title
import com.example.ConstructionCompany.entity.TitleCategory
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
class TitleProcessor: AbstractProcessor<Title>() {
    override fun process(model: EntityModel<Title>): EntityModel<Title> {
        model.addLink("/titleCategories/${model.content?.titleCategory?.id}", "titleCategory")
        model.addLink("/brigades/title/${model.content?.id}", "brigades")
        model.addLink("/staff/title/${model.content?.id}", "staff")
        return model
    }
}

