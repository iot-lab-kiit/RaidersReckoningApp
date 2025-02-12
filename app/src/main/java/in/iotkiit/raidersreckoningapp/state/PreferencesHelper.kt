package `in`.iotkiit.raidersreckoningapp.state

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesHelper @Inject constructor(@ApplicationContext context: Context) {
    private val sharedPreferences = context.getSharedPreferences("quiz_prefs", Context.MODE_PRIVATE)

    fun savePoints(points: Int) {
        val currentPoints = getTotalPoints()
        val newTotalPoints = currentPoints + points
        sharedPreferences.edit().putInt("total_points", newTotalPoints).apply()
    }

    fun getTotalPoints(): Int {
        return sharedPreferences.getInt("total_points", 0)
    }

    fun resetPoints() {
        sharedPreferences.edit().putInt("total_points", 0).apply()
    }
}