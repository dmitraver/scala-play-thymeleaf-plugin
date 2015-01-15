package com.dmitraver.play.thymeleaf.dialect

import com.dmitraver.play.thymeleaf.expression.PlayOgnlVariableExpressionEvaluator
import org.thymeleaf.standard.StandardDialect

class PlayDialect extends StandardDialect {
	setVariableExpressionEvaluator(new PlayOgnlVariableExpressionEvaluator)
}
