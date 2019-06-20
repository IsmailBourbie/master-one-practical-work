package model

import java.math.MathContext.UNLIMITED
import java.math.MathContext.UNLIMITED





class Place(name: String, var tokens : Int = 0) : PetriObject(name) {

    private val maxTokens = UNLIMITED

    fun addTokens(weight: Int) {
        this.tokens += weight
    }

    fun removeTokens(weight: Int) {
        this.tokens -= weight
    }

    fun hasAtLeastTokens(number: Int): Boolean {
        return tokens >= number
    }

    override fun toString(): String {
        return super.toString() + " Tokens=" + this.tokens
    }

    fun maxTokensReached(newTokens: Int): Boolean {
        return if (hasUnlimitedMaxTokens()) {
            false
        } else tokens + newTokens > maxTokens
    }

    private fun hasUnlimitedMaxTokens(): Boolean {
        return maxTokens == UNLIMITED
    }

    companion object {
        const val UNLIMITED = -1
    }
}
