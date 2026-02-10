package com.eps.android.di

import android.content.Context
import androidx.room.Room
import com.eps.android.data.BlacklistDao
import com.eps.android.data.EpsDatabase
import com.eps.android.data.ThreatEventDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): EpsDatabase {
        return Room.databaseBuilder(
            context,
            EpsDatabase::class.java,
            "hackdefender_secure.db"
        ).fallbackToDestructiveMigration()
         .build()
    }

    @Provides
    fun provideThreatEventDao(db: EpsDatabase): ThreatEventDao {
        return db.threatEventDao()
    }

    @Provides
    fun provideBlacklistDao(db: EpsDatabase): BlacklistDao {
        return db.blacklistDao()
    }

    @Provides
    @Singleton
    fun provideThreatNotifier(@ApplicationContext context: Context): com.eps.android.core.ThreatNotifier {
        return com.eps.android.core.ThreatNotifier(context)
    }
}
