package org.dhis2_haparent.usescases.settings

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.dhis2_haparent.commons.filters.FilterManager
import org.dhis2_haparent.commons.prefs.PreferenceProvider
import org.dhis2_haparent.data.service.workManager.WorkManagerController
import org.junit.Before
import org.junit.Test

class DeleteUserDataTest {

    private lateinit var deleteUserData: DeleteUserData

    private val workManagerController: WorkManagerController = mock()
    private val filterManager: FilterManager = mock()
    private val preferencesProvider: PreferenceProvider = mock()

    @Before
    fun setup() {
        deleteUserData =
            DeleteUserData(workManagerController, filterManager, preferencesProvider)
    }

    @Test
    fun `Should delete user data`() {
        deleteUserData.wipeCacheAndPreferences(null)

        verify(workManagerController).cancelAllWork()
        verify(workManagerController).pruneWork()
        verify(preferencesProvider).clear()
    }
}
