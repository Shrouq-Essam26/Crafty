package com.kidscrafts.utilities

import android.content.Context
import com.kidscrafts.data.AppDatabase
import com.kidscrafts.data.GardenPlantingRepository
import com.kidscrafts.data.PlantRepository
import com.kidscrafts.viewmodels.GardenPlantingListViewModelFactory
import com.kidscrafts.viewmodels.LoginFragmentViewModelFactory
import com.kidscrafts.viewmodels.PlantDetailViewModelFactory
import com.kidscrafts.viewmodels.PlantListViewModelFactory

/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 */
object InjectorUtils {

    fun provideLoginFragmentViewModelFactory(
            context: Context
    ): LoginFragmentViewModelFactory {
        return LoginFragmentViewModelFactory()
    }

    private fun getPlantRepository(context: Context): PlantRepository {
        return PlantRepository.getInstance(AppDatabase.getInstance(context).plantDao())
    }

    private fun getGardenPlantingRepository(context: Context): GardenPlantingRepository {
        return GardenPlantingRepository.getInstance(
                AppDatabase.getInstance(context).gardenPlantingDao())
    }

    fun provideGardenPlantingListViewModelFactory(
        context: Context
    ): GardenPlantingListViewModelFactory {
        val repository = getGardenPlantingRepository(context)
        return GardenPlantingListViewModelFactory(repository)
    }

    fun providePlantListViewModelFactory(context: Context): PlantListViewModelFactory {
        val repository = getPlantRepository(context)
        return PlantListViewModelFactory(repository)
    }

    fun providePlantDetailViewModelFactory(
        context: Context,
        plantId: String
    ): PlantDetailViewModelFactory {
        return PlantDetailViewModelFactory(getPlantRepository(context),
                getGardenPlantingRepository(context), plantId)
    }
}
