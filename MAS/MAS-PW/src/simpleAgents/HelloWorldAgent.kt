package simpleAgents

import jade.core.Agent

class HelloWorldAgent : Agent() {

    override fun setup() {
        print("Hello World my name is $localName")
    }
}
