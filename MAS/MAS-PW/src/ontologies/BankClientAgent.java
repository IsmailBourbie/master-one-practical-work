package ontologies;
/* BankClienAgent:
 * ---------------
 * This class is an example showing how to use the classes OneShotBehaviour,
 * SimpleBehaviour, WakerBehaviour and ParallelBehaviour to program an agent.
 *
 *********************************************************************************/

import jade.content.AgentAction;
import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.basic.Action;
import jade.content.onto.basic.Result;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.leap.ArrayList;
import jade.util.leap.Iterator;
import jade.util.leap.List;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;


public class BankClientAgent extends Agent implements BankVocabulary {

    private static final int WAIT = -1;
    private static final int QUIT = 0;
    private int command = WAIT;
    private int cnt = 0;
    private AID server;
    private List accounts = new ArrayList();
    private Codec codec = new SLCodec();
    private Ontology ontology = BankOntology.getInstance();

    protected void setup() {

        // Register language and ontologies
        getContentManager().registerLanguage(codec);
        getContentManager().registerOntology(ontology);
        // Set this agent main behaviour
        addBehaviour(new WaitUserCommand(this));
    }

    private void createAccount() {
        // Process to the server agent the request to create a new account
        CreateAccount ca = new CreateAccount();
        ca.setName("acc" + cnt++);
        sendMessage(ACLMessage.REQUEST, ca);
    }

    private void requestOperation() {
        // Process to the server agent the request to make an operation
        String accName = chooseAccount();
        if (accName == null) {
            addBehaviour(new WaitUserCommand(this));
            return;
        }
        Account acc;
        if ((acc = findAccountByName(accName)) == null) {
            System.out.println("Invalid account!");
            addBehaviour(new WaitUserCommand(this));
            return;
        }
        String in = getUserInput("\nAmount: ");
        if (in == null) {
            System.out.println("Unable to process operation!");
            return;
        }
        float amount;
        amount = Float.parseFloat(in);
        MakeOperation mo = new MakeOperation();
        mo.setType(command);
        mo.setAmount(amount);
        mo.setAccountId(acc.getId());
        sendMessage(ACLMessage.REQUEST, mo);
    }

    private void queryInformation() {
        // Process to the server agent the request a query for information
        String accName = chooseAccount();
        Account acc;
        if (accName == null || (acc = findAccountByName(accName)) == null) {
            System.out.println("Invalid account!");
            addBehaviour(new WaitUserCommand(this));
            return;
        }
        String s = " charge applies for this operation. Continue?\ny/n: ";
        s = command == BALANCE ? "\n\n\t$" + BAL_CHARGE + s : "\n\n\t$" + OPER_CHARGE + s;
        String in = getUserInput(s);
        if (Objects.equals(in, "y")) {
            Information info = new Information();
            info.setType(command);
            info.setAccountId(acc.getId());
            sendMessage(ACLMessage.QUERY_REF, info);
            return;
        }
        if (Objects.equals(in, "n")) System.out.println("\nOperation aborted!");
        else System.out.println("\nInvalid choice!");
        addBehaviour(new WaitUserCommand(this));
    }

    private void lookupServer() {
        // Search in the DF to retrieve the server AID
        ServiceDescription sd = new ServiceDescription();
        sd.setType(SERVER_AGENT);
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.addServices(sd);
        try {
            DFAgentDescription[] dfds = DFService.search(this, dfd);
            if (dfds.length > 0) {
                server = dfds[0].getName();
                System.out.println("Localized server");
            } else System.out.println("\nCouldn't localize server!");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("\nFailed searching int the DF!");
        }
    }

    private Account findAccountByName(String name) {

        for (Iterator it = accounts.iterator(); it.hasNext(); ) {
            Account acc = (Account) it.next();
            if (acc.getName().equals(name))
                return acc;
        }
        return null;
    }

    private Account findAccountById(String id) {

        for (Iterator it = accounts.iterator(); it.hasNext(); ) {
            Account acc = (Account) it.next();
            if (acc.getId().equals(id))
                return acc;
        }
        return null;
    }

    private String chooseAccount() {

        if (accounts.isEmpty()) {
            System.out.println("\nNo account currently available!");
            return null;
        }
        // Display account list
        StringBuilder out = new StringBuilder("\n\t<<----- AVAILABLE ACCOUNT(S) ----->>\n");
        for (Iterator it = accounts.iterator(); it.hasNext(); )
            out.append("\n\t").append(((Account) it.next()).getName());

        return getUserInput(out + "\n\nAccount: ");
    }


    private int getUserChoice() {

        System.out.print("\n\t<<---- SAVINGS ACOUNT - MENU ---->>" +
                "\n\n\t0. Terminate program" +
                "\n\t1. Create a new account\n\t2. Make a deposit" +
                "\n\t3. Make a withdrawal\n\t4. Get account balance" +
                "\n\t5. Get list of operations\n> ");
        Integer in = getInteger();
        if (in != null) return in;
        return WAIT;
    }

    int getUserChoice(String msg) {

        System.out.print(msg);
        Integer in = getInteger();
        if (in != null) return in;
        return -1;
    }

    @Nullable
    private Integer getInteger() {
        try {
            BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
            String in = buf.readLine();
            return Integer.parseInt(in);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private String getUserInput(String msg) {

        System.out.print(msg);
        try {
            BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
            return buf.readLine();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private void sendMessage(int performative, AgentAction action) {


        if (server == null) lookupServer();
        if (server == null) {
            System.out.println("Unable to localize the server! \nOperation aborted!");
            return;
        }
        ACLMessage msg = new ACLMessage(performative);
        msg.setLanguage(codec.getName());
        msg.setOntology(ontology.getName());
        try {
            getContentManager().fillContent(msg, new Action(server, action));
            msg.addReceiver(server);
            send(msg);
            System.out.println("Contacting server... Please wait!");
            addBehaviour(new WaitServerResponse(this));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    class WaitUserCommand extends OneShotBehaviour {
// ----------------------------------  Get the user command

        WaitUserCommand(Agent a) {
            super(a);
            command = WAIT;
        }

        public void action() {

            command = getUserChoice();

            if (command == QUIT) {
                System.out.println(getLocalName() + " is shutting down...Bye!");
                doDelete();
                System.exit(0);
            }
            if (command == NEW_ACCOUNT)
                createAccount();

            else if (command == DEPOSIT || command == WITHDRAWAL)
                requestOperation();

            else if (command == BALANCE || command == OPERATIONS)
                queryInformation();

            else {
                System.out.println("Invalid choice!");
                addBehaviour(new WaitUserCommand(myAgent));
            }
        }
    }

    class WaitServerResponse extends ParallelBehaviour {
// ------launch a SimpleBehaviour to receive
//       servers response and a WakerBehaviour
//       to terminate the waiting if there is
//       no response from the server

        WaitServerResponse(Agent a) {

            super(a, 1);

            addSubBehaviour(new ReceiveResponse(myAgent));

            addSubBehaviour(new WakerBehaviour(myAgent, 5000) {

                protected void handleElapsedTimeout() {
                    System.out.println("\n\tNo response from server. Please, try later!");
                    addBehaviour(new WaitUserCommand(myAgent));
                }
            });
        }
    }

    class ReceiveResponse extends SimpleBehaviour {

        private boolean finished = false;

        ReceiveResponse(Agent a) {
            super(a);
        }

        public void action() {

            ACLMessage msg = receive(MessageTemplate.MatchSender(server));

            if (msg == null) {
                block();
                return;
            }

            if (msg.getPerformative() == ACLMessage.NOT_UNDERSTOOD) {
                System.out.println("\n\n\tResponse from server: NOT UNDERSTOOD!");
            } else if (msg.getPerformative() != ACLMessage.INFORM) {
                System.out.println("\nUnexpected msg from server!");
            } else {
                try {
                    ContentElement content = getContentManager().extractContent(msg);

                    if (content instanceof Result) {

                        Result result = (Result) content;

                        if (result.getValue() instanceof Problem) {

                            Problem prob = (Problem) result.getValue();
                            System.out.println("\n\n\tResponse from server: " + prob.getMsg().toUpperCase());
                        } else if (result.getValue() instanceof Account) {

                            Account acc = (Account) result.getValue();

                            if (command == NEW_ACCOUNT) {
                                accounts.add(acc);
                            } else {
                                Account ac = findAccountById(acc.getId());
                                accounts.remove(ac);
                                accounts.add(acc);
                            }
                            System.out.println("\n\n\tResponse from server: \n\tAccount [" +
                                    acc.toString() + "]");
                        } else if (result.getValue() instanceof List) {
                            List list = result.getItems();
                            Information info = (Information) ((Action) result.getAction()).getAction();
                            Account acc = findAccountById(info.getAccountId());
                            StringBuilder s = new StringBuilder("\n\n\tLIST OF OPERATIONS: Account [" + (acc != null ? acc.getName() : null) + " # " + (acc != null ? acc.getId() : null) + "]\n\tDate\tType\tAmount\tBalance");
                            for (Iterator it = list.iterator(); it.hasNext(); )
                                s.append(it.next().toString());
                            System.out.println(s);
                        } else System.out.println("\n\tUnexpected result from server!");
                    } else {
                        System.out.println("\n\tUnable de decode response from server!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            finished = true;
        }

        public boolean done() {
            return finished;
        }

        public int onEnd() {
            addBehaviour(new WaitUserCommand(myAgent));
            return 0;
        }
    }
}
