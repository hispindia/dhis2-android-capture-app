package org.dhis2_haparent.commons.orgunitselector

interface OUTreeComponentProvider {
    fun provideOUTreeComponent(module: OUTreeModule): OUTreeComponent?
}
