package com.dbeginc.simplemap.presentation.di

import javax.inject.Scope

/**
 * This annotation class, create a scope in dagger dependencies graph
 * so that it's will return same instance for all dependencies in this scope
 */
@Retention(AnnotationRetention.RUNTIME)
@Scope
annotation class ApplicationScope