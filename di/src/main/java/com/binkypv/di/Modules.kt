package com.binkypv.di

import com.binkypv.data.datasource.CatFactsDataSource
import com.binkypv.data.datasource.CatImagesDataSource
import com.binkypv.data.repository.CatFactsRepositoryImpl
import com.binkypv.data.utils.Constants.CAT_FACTS_BASE_URL
import com.binkypv.data.utils.Constants.CAT_IMAGES_BASE_URL
import com.binkypv.domain.repository.CatFactsRepository
import com.binkypv.presentation.viewmodel.CatFactsViewModel
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val API_KEY = "YOUR_API_KEY_HERE"
private const val API_KEY_HEADER = "x-api-key"

private const val CAT_IMAGES_HTTP_CLIENT = "catImagesHttpClient"
private const val CAT_FACTS_HTTP_CLIENT = "catFactsHttpClient"
private const val CAT_IMAGES_RETROFIT = "catImagesRetrofit"
private const val CAT_FACTS_RETROFIT = "catFactsRetrofit"

object Modules {
    val viewModelModule = module {
        viewModel { CatFactsViewModel(get()) }
    }

    val repositoryModule = module {
        fun provideCatFactsRepository(
            catImagesDataSource: CatImagesDataSource,
            catFactsDataSource: CatFactsDataSource
        ): CatFactsRepository =
            CatFactsRepositoryImpl(
                catImagesDataSource, catFactsDataSource
            )

        single { provideCatFactsRepository(get(), get()) }
    }

    val dataSourceModule = module {
        fun provideCatImagesDataSource(retrofit: Retrofit): CatImagesDataSource {
            return retrofit.create(CatImagesDataSource::class.java)
        }

        fun provideCatFactsDataSource(retrofit: Retrofit): CatFactsDataSource {
            return retrofit.create(CatFactsDataSource::class.java)
        }

        single { provideCatImagesDataSource(get(named(CAT_IMAGES_RETROFIT))) }
        single { provideCatFactsDataSource(get(named(CAT_FACTS_RETROFIT))) }
    }

    val netModule = module {

        fun provideCatImagesHttpClient(): OkHttpClient {
            val okHttpClientBuilder = OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                )
                .addInterceptor { chain ->
                    val original = chain.request()

                    val request = original.newBuilder()
                        .header(API_KEY_HEADER, API_KEY)
                        .build()

                    chain.proceed(request)
                }
            return okHttpClientBuilder.build()
        }

        fun provideCatFactsHttpClient(): OkHttpClient {
            val okHttpClientBuilder = OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                )

            return okHttpClientBuilder.build()
        }

        fun provideGson(): Gson {
            return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
        }

        fun provideCatImagesRetrofit(gson: Gson, client: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(CAT_IMAGES_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
        }

        fun provideCatFactsRetrofit(gson: Gson, client: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(CAT_FACTS_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
        }

        single(named(CAT_IMAGES_HTTP_CLIENT)) { provideCatImagesHttpClient() }
        single(named(CAT_FACTS_HTTP_CLIENT)) { provideCatFactsHttpClient() }
        single(named(CAT_IMAGES_RETROFIT)) {
            provideCatImagesRetrofit(
                get(),
                get(named(CAT_IMAGES_HTTP_CLIENT))
            )
        }
        single(named(CAT_FACTS_RETROFIT)) {
            provideCatFactsRetrofit(
                get(),
                get(named(CAT_FACTS_HTTP_CLIENT))
            )
        }
        single { provideGson() }

    }
}