package com.udyata.lifelog.di

import androidx.room.Room
import com.udyata.lifelog.data.database.AppDatabase
import com.udyata.lifelog.data.repository.DiaryRepository
import com.udyata.lifelog.data.repositoryimpl.DiaryRepositoryImpl
import com.udyata.lifelog.domain.usecase.DeleteDiaryEntryUseCase
import com.udyata.lifelog.domain.usecase.GetAllDiaryEntriesUseCase
import com.udyata.lifelog.domain.usecase.InsertDiaryEntryUseCase
import com.udyata.lifelog.presentation.DiaryViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appModule = module {

}

internal val appDataBaseModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "life_log"

        ).build()
    }
}

internal val daoModule = module {
    single { get<AppDatabase>().diaryEntryDao() }
}

internal val repositoryModule = module {
    single<DiaryRepository> { DiaryRepositoryImpl(get()) }
}
internal val useCaseModule = module {
    factory { GetAllDiaryEntriesUseCase(get()) }
    factory { InsertDiaryEntryUseCase(get()) }
    factory { DeleteDiaryEntryUseCase(get()) }
}

internal val viewModelModule = module {
    viewModel { DiaryViewModel(get(), get(), get()) }
}
