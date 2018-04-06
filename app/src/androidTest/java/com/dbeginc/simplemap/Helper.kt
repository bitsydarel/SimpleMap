package com.dbeginc.simplemap

import android.support.test.InstrumentationRegistry
import com.dbeginc.simplemap.presentation.di.ApplicationComponent
import it.cosenonjaviste.daggermock.DaggerMock

fun espressoDaggerMockRule() = DaggerMock.rule<ApplicationComponent>(MockDataModule()) {
    set { component -> component.inject(app) }
    customizeBuilder<ApplicationComponent.Builder> {
        it.apply { seedInstance(app) }
    }
}

val app: SimpleMap get() = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as SimpleMap