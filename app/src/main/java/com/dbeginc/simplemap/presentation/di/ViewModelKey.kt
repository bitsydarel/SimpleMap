package com.dbeginc.simplemap.presentation.di

import android.arch.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Created by darel on 26.03.18.
 *
 * ViewModel Key, this key allow dagger to find
 * specific viewmodel by it's  KCLASS
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey()
annotation class ViewModelKey(val value: KClass<out ViewModel>)