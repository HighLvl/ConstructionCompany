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
}