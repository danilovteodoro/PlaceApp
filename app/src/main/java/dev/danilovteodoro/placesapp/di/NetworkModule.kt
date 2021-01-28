package dev.danilovteodoro.placesapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dev.danilovteodoro.placesapp.network.api.CheckInApi
import dev.danilovteodoro.placesapp.network.api.EventApi
import dev.danilovteodoro.placesapp.util.DateJsonAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import util.Constantes
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesGson():Gson{
        return GsonBuilder()
            .registerTypeAdapter(Date::class.java,DateJsonAdapter())
            .create()
    }

    @Provides
    @Singleton
    fun providesRetrofit(gson: Gson):Retrofit.Builder{
        return Retrofit.Builder()
            .baseUrl(Constantes.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Provides
    @Singleton
    fun providesEventApi(retrofit:Retrofit.Builder):EventApi{
        return retrofit.build().create(EventApi::class.java)
    }
    @Provides
    @Singleton
    fun providesCheckInpi(retrofit:Retrofit.Builder):CheckInApi{
        return retrofit.build().create(CheckInApi::class.java)
    }
}