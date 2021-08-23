package org.medhis2yes.form.data

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.medhis2yes.form.ui.style.BasicFormUiModelStyle
import org.medhis2yes.form.ui.style.FormUiColorFactory
import org.medhis2yes.form.ui.style.FormUiColorType
import org.medhis2yes.form.ui.style.FormUiModelStyle
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

class BasicFormUiModelStyleTest {

    private val colorFactory: FormUiColorFactory = mock()
    private lateinit var basicFormUiModelStyle: FormUiModelStyle

    @Before
    fun setUp() {
        basicFormUiModelStyle = BasicFormUiModelStyle(colorFactory)
    }

    @Test
    @Ignore
    fun shouldGetColorsFromStyle() {
        val mapOfColors = mapOf(
            FormUiColorType.PRIMARY to 1,
            FormUiColorType.TEXT_PRIMARY to 2,
            FormUiColorType.WARNING to 3,
            FormUiColorType.ERROR to 4
        )
        whenever(colorFactory.getBasicColors()) doReturn mapOfColors

        val result = basicFormUiModelStyle.getColors()

        assert(result[FormUiColorType.PRIMARY] == 1)
        assert(result[FormUiColorType.TEXT_PRIMARY] == 2)
        assert(result[FormUiColorType.WARNING] == 3)
        assert(result[FormUiColorType.ERROR] == 4)
    }
}
