package com.rezen.videoplayer.di

import com.rezen.videoplayer.common.ServiceGenerator
import com.rezen.videoplayer.data.remote.YoutubeRemoteDataSource
import com.rezen.videoplayer.data.remote.YoutubeRemoteDataSourceImpl
import com.rezen.videoplayer.data.remote.service.YoutubeService
import com.rezen.videoplayer.data.repositoryImpl.YoutubeDataRepositoryImpl
import com.rezen.videoplayer.domain.repository.YoutubeDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class YoutubeDataModule {

    @Provides
    @Singleton
    fun provideYoutubeService(serviceGenerator: ServiceGenerator): YoutubeService {
        return serviceGenerator.createService(YoutubeService::class.java)
    }

    @Provides
    @Singleton
    fun provideYoutubeRemoteDataSource(youtubeService: YoutubeService): YoutubeRemoteDataSource {
        return YoutubeRemoteDataSourceImpl(youtubeService)
    }

    @Provides
    @Singleton
    fun provideYoutubeRepository(
        youtubeRemoteDataSource: YoutubeRemoteDataSource
    ): YoutubeDataRepository {
        return YoutubeDataRepositoryImpl(youtubeRemoteDataSource)
    }

}