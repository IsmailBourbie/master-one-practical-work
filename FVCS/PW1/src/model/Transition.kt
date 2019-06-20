package model

import java.util.ArrayList

class Transition(name: String) : PetriObject(name) {

    val incoming = ArrayList<Arc>()
    val outgoing = ArrayList<Arc>()

    val isNotConnected: Boolean
        get() = incoming.isEmpty() && outgoing.isEmpty()


    fun canFire(): Boolean {
        var canMove: Boolean

        canMove = !this.isNotConnected

        for (arc in incoming) {
            canMove = canMove and arc.canFire()
        }

        for (arc in outgoing) {
            canMove = canMove and arc.canFire()
        }
        return canMove
    }

    fun fire() {
        for (arc in incoming) {
            arc.fire()
        }

        for (arc in outgoing) {
            arc.fire()
        }
    }

    fun addIncoming(arc: Arc) {
        this.incoming.add(arc)
    }

    fun addOutgoing(arc: Arc) {
        this.outgoing.add(arc)
    }

    override fun toString(): String {
        return super.toString() +
                (if (isNotConnected) " Is Not Connecred " else "") +
                if (canFire()) " Can Move " else ""
    }
}
