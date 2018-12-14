package behaviourAgent

import jade.core.Agent
import jade.core.behaviours.Behaviour
import jade.core.behaviours.TickerBehaviour
import jade.core.behaviours.WakerBehaviour


class SimpleAgent : Agent() {

    override fun setup() {
        addBehaviour(SimpleBehaviour())
        addBehaviour(object : WakerBehaviour(this, 5000) {
            override fun handleElapsedTimeout() {
                println("I Was Waker Executed.")
            }
        })
        addBehaviour(object : TickerBehaviour(this, 5000) {
            var int = 0
            override fun onTick() {
                println("This is The $int time.")
                int++
            }
        })
    }

    override fun takeDown() {
        print("I Was Killed -_- .")
    }

    class SimpleBehaviour : Behaviour() {
        var isFinished = false

        override fun action() {
            println("I Was Simple Executed")
            isFinished = true
        }

        override fun done() = isFinished
    }
}