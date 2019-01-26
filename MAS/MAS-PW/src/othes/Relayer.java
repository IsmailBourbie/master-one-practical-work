package othes;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Vector;

public class Relayer extends Agent {

    @Override
    protected void setup() {
        super.setup();
        addBehaviour(new NumberBehaviour());
    }

    class NumberBehaviour extends CyclicBehaviour {

        int countMsg, countU, countD, countC;
        ACLMessage acl = new ACLMessage(ACLMessage.INFORM);

        @Override
        public void action() {

            ACLMessage messgae = receive();
            if (messgae != null && countMsg < 100) {
                countMsg++;
                String number = messgae.getContent();

                acl.setContent(number);
                switch (number.length()) {
                    case 1:
                        AID unity = new AID("unité", AID.ISLOCALNAME);
                        countU += Integer.parseInt(number);
                        acl.addReceiver(unity);
                        break;
                    case 2:
                        AID dizaine = new AID("disaine", AID.ISLOCALNAME);
                        acl.addReceiver(dizaine);
                        countD += Integer.parseInt(number);
                        break;
                    case 3:
                        AID centaine = new AID("centaine", AID.ISLOCALNAME);
                        acl.addReceiver(centaine);
                        countC += Integer.parseInt(number);
                        break;
                    default:
                        break;

                }
                send(acl);

            } else if (countMsg == 100) {
                System.out.println(countC);
                System.out.println(countU);
                System.out.println(countD);
                doDelete();
            } else block();

        }
    }
}
