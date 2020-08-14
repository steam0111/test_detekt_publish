package live.cupcake.android.rules

import io.gitlab.arturbosch.detekt.test.lint
import org.junit.Test
import kotlin.test.assertEquals

class FunctionReturnRuleTest {

    private val noSpaceBeforeReturn = """
        override suspend fun shouldShowSubscriptionPrompt(): Boolean {
            val shouldShow = !wasSubscriptionPromptShown.get()

            wasSubscriptionPromptShown.set(true)
            return shouldShow
        }
    """

    private val spaceBeforeReturn = """
        override suspend fun shouldShowSubscriptionPrompt(): Boolean {
            val shouldShow = !wasSubscriptionPromptShown.get()

            wasSubscriptionPromptShown.set(true)
            
            return shouldShow
        }
    """

    private val doubleSpaceBeforeReturn = """
        override suspend fun shouldShowSubscriptionPrompt(): Boolean {
            val shouldShow = !wasSubscriptionPromptShown.get()

            wasSubscriptionPromptShown.set(true)
            
            
            return shouldShow
        }
    """

    private val inlineCase = """
        private fun is24Format(): Boolean {
           return DateFormat.is24HourFormat(context)
        }
    """

    @Test
    fun report_ifNoSpaceBeforeReturn() {
        val findings = FunctionReturnRule().lint(noSpaceBeforeReturn.trimIndent())

        assertEquals(1, findings.size)
    }

    @Test
    fun noReport_ifSpaceBeforeReturn() {
        val findings = FunctionReturnRule().lint(spaceBeforeReturn.trimIndent())

        assertEquals(0, findings.size)
    }

    @Test
    fun report_ifDoubleSpaceBeforeReturn() {
        val findings = FunctionReturnRule().lint(doubleSpaceBeforeReturn.trimIndent())

        assertEquals(0, findings.size)
    }

    @Test
    fun noReport_ifInlineCase() {
        val findings = FunctionReturnRule().lint(inlineCase.trimIndent())

        assertEquals(0, findings.size)
    }
}