package model

class Arc private constructor(name: String, internal var direction: Direction, internal var place: Place, internal var transition: Transition) : PetriObject(name) {

    var weight = 1

    enum class Direction {

        PLACE_TO_TRANSITION {
            override fun canFire(place: Place, howMuch: Int): Boolean {
                return place.hasAtLeastTokens(howMuch)
            }

            override fun fire(place: Place, howMuch: Int) {
                place.removeTokens(howMuch)
            }

        },

        TRANSITION_TO_PLACE {
            override fun canFire(place: Place, howMuch: Int): Boolean {
                return !place.maxTokensReached(howMuch)
            }

            override fun fire(place: Place, howMuch: Int) {
                place.addTokens(howMuch)
            }
        };

        abstract fun canFire(place: Place, howMuch: Int): Boolean

        abstract fun fire(place: Place, howMuch: Int)
    }

    constructor(name: String, place: Place, transition: Transition) : this(name, Direction.PLACE_TO_TRANSITION, place, transition) {
        transition.addIncoming(this)
    }

    constructor(name: String, transition: Transition, place: Place) : this(name, Direction.TRANSITION_TO_PLACE, place, transition) {
        transition.addOutgoing(this)
    }

    fun canMove(): Boolean {
        return direction.canFire(place, weight)
    }

    fun move() {
        this.direction.fire(place, this.weight)
    }
}
