package com.example.ConstructionCompany.controller

import com.example.ConstructionCompany.applyBasePath
import com.example.ConstructionCompany.controller.assembler.AbstractModelAssembler
import com.example.ConstructionCompany.entity.AbstractJpaPersistable
import com.example.ConstructionCompany.repository.AbstractRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.PagedModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.Serializable
import javax.servlet.http.HttpServletRequest


abstract class AbstractController<ID : Serializable, T : AbstractJpaPersistable<ID>>(
    private val repository: AbstractRepository<T, ID>,
    private val assembler: AbstractModelAssembler<T>
) {

    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: ID,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<T>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<T>>> {
        val page = repository.findById(id, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }


    @GetMapping("/", "")
    fun getAll(
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<T>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<T>>> {
        val page = repository.findAll(pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }

    @GetMapping("/collection")
    fun getAllById(
        @RequestParam("id") collection: Collection<ID>,
        pageable: Pageable,
        pagedAssembler: PagedResourcesAssembler<T>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<T>>> {
        val page = repository.findAllByIdIn(collection, pageable)
        return toResponse(pagedAssembler, page, assembler, request)
    }

    @PostMapping("/")
    fun save(
        @RequestBody entity: T
    ) = ResponseEntity.ok(repository.save(entity))

    @PutMapping("/collection")
    fun saveAll(
        @RequestBody collection: Collection<T>
    ) = ResponseEntity.ok(repository.saveAll(collection))


    @DeleteMapping("/{id}")
    fun deleteById(
        @PathVariable id: ID
    ) = repository.deleteById(id).let { ResponseEntity.ok().build<Any>() }


    @DeleteMapping("/collection")
    fun deleteAllById(
        @RequestParam("id") collection: Collection<ID>
    ) = repository.deleteAll(repository.findAllById(collection)).let {
        ResponseEntity.ok().build<Any>()
    }

    protected fun toResponse(
        pagedAssembler: PagedResourcesAssembler<T>,
        page: Page<T>,
        assembler: AbstractModelAssembler<T>,
        request: HttpServletRequest
    ): ResponseEntity<PagedModel<EntityModel<T>>> =
        ResponseEntity.ok(pagedAssembler.toModel(page, assembler, applyBasePath(Link.of(request.servletPath))))

}
