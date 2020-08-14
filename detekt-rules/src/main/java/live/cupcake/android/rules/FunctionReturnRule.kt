package live.cupcake.android.rules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.PsiWhiteSpaceImpl
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtReturnExpression

class FunctionReturnRule(config: Config = Config.empty) : Rule(config) {

    override val issue = Issue(
        javaClass.simpleName,
        Severity.Defect,
        "This rule reports no space before return",
        Debt.FIVE_MINS
    )

    override fun visitNamedFunction(function: KtNamedFunction) {
        super.visitNamedFunction(function)

        val body = function.bodyBlockExpression

        var child: PsiElement? = body?.firstChild

        while (child != null && child !is KtReturnExpression) {
            child = child.nextSibling
        }

        val isLeftBraceBeforeReturn = child?.prevSibling?.prevSibling is LeafPsiElement

        if (child != null && !isLeftBraceBeforeReturn) {
            val returnExpression = child as KtReturnExpression

            val isPrevChildWhiteSpace = returnExpression.prevSibling !is PsiWhiteSpaceImpl

            if (isPrevChildWhiteSpace || !returnExpression.prevSibling.text.containsDoubleLineBreak()) {
                report(CodeSmell(issue, Entity.from(returnExpression), "Put one line break before return"))
            }
        }
    }

    private fun String.containsDoubleLineBreak() =
        this.contains(Regex(" *([\\n\\r]) *([\\n\\r]) *"))
}