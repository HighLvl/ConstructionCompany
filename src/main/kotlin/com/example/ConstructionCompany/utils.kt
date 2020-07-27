package com.example.ConstructionCompany

import org.springframework.hateoas.Link
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.net.URISyntaxException


fun applyBasePath(link: Link): Link {
    val basePath = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()
    val uri: URI = link.toUri()
    var newUri: URI? = null
    try {
        newUri = URI(
            uri.scheme, uri.userInfo, uri.host,  //
            uri.port, basePath + uri.path, uri.query, uri.fragment
        )
    } catch (e: URISyntaxException) {
        e.printStackTrace()
    }
    return Link.of(newUri.toString(), link.rel)
}