package com.gojek.sample.di.component

import android.app.Application
import com.gojek.sample.MainApplication
import com.gojek.sample.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        AndroidInjectionModule::class,
        ActivityModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        ExecuterModule::class,
        ViewModelModuleDI::class]
)

interface ApplicationComponent {

    /*
    * We will call this builder interface from our project custom Application class.
    * This will set our application object to the AppComponent.
    * So inside the AppComponent the application instance is available.
    * So this application instance can be accessed by our modules
    * such as ApiModule when needed
    *
    * */

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(application: MainApplication)
}