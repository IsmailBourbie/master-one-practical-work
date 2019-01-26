package othes;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class Alphabet extends Agent {

    @Override
    protected void setup() {
        super.setup();
        addBehaviour(new CountBehaviour());
    }

    class CountBehaviour extends CyclicBehaviour {

        private int countMessages, counterVoyels, counterConsonne;

        @Override
        public void action() {
            ACLMessage message = new ACLMessage(ACLMessage.INFORM);

            if (message != null && countMessages <= 20) {
                countMessages++;
                String content = message.getContent();
                if ("aeouiyAEOUIY".contains(String.valueOf(content.charAt(0)))) {
                    counterVoyels++;
                } else {
                    counterConsonne++;
                }

            } else if (countMessages == 20) {
                System.out.println("nombre de mot avec voyelle=" + counterVoyels);
                System.out.println("nombre de mot avec consonne=" + counterConsonne);
            } else block();

        }
    }
}
