package com.practice.lc.arithmetic

class EvaluateBooleanExpression {
    fun evaluateBoolExpression(expression: String): Boolean {
        val stack = ArrayDeque<String>()
        var currEvalStr = "" 

        for (c in expression) {
            if (c == '(') {
                // Go to sub evalutation 
                stack.addLast(currEvalStr) 
                currEvalStr = ""
                continue
            } else if (c == ')') {
                // Finish sub evaluation
                val saved = stack.removeLast()
                currEvalStr = saved + currEvalStr
            } else {
                // Append and go
                currEvalStr += c
            }

            // handle current expresion evaluation
            if (currEvalStr.length == 2 && currEvalStr[0] == '!') {
                currEvalStr = if (currEvalStr[1] == 't') "f" else "t" 
            } else if (currEvalStr.length == 3) {
                if (currEvalStr[1] == '&') {
                    currEvalStr = if (currEvalStr[0] == 't' && currEvalStr[2] == 't') "t" else "f"
                } else if (currEvalStr[1] == '|') {
                    currEvalStr = if (currEvalStr[0] == 't' || currEvalStr[2] == 't') "t" else "f"
                }
            }
        }
        return if (currEvalStr == "t") true else false 
    }
}