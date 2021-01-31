package dev.danilovteodoro.placesapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.danilovteodoro.placesapp.data.AppDatabase
import dev.danilovteodoro.placesapp.data.EventDao
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context):AppDatabase{
        return Room.databaseBuilder(context,AppDatabase::class.java,AppDatabase.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideEventDao(database:AppDatabase):EventDao{
        return database.eventDao()
    }
}