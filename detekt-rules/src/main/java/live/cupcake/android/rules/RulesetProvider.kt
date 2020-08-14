package live.cupcake.android.rules

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

class RulesetProvider : RuleSetProvider {
    override val ruleSetId: String = "detekt-rules"

    override fun instance(config: Config): RuleSet = RuleSet(
        ruleSetId,
        listOf(
            FunctionReturnRule()
        )
    )
}
