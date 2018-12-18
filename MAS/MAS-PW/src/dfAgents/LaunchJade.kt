import dfAgents.SearcherAgent
import dfAgents.ServiceAgent
import jade.core.ProfileImpl
import jade.core.Runtime
import jade.wrapper.AgentContainer

class LaunchJade {

    private var runtime: Runtime? = null

    init {
        try {

            // Get a hold on JADE runtime
            runtime = Runtime.instance()
            // Exit the JVM when there are no more containers around
            runtime?.setCloseVM(true)
            // Launch a complete platform on the 8888 port
            // create a default Profile
            val pMain = ProfileImpl(null, 8888, null)

            System.out.println("Launching a whole in-process platform...$pMain")
            mc = runtime?.createMainContainer(pMain)

            /* set now the default Profile to start a container
        ProfileImpl pContainer = new ProfileImpl(null, 8888, null);
        System.out.println("Launching the agent container ..."+pContainer);
        AgentContainer cont = runtime.createAgentContainer(pContainer);
        System.out.println("Launching the agent container after ..."+pContainer);*/

        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            val serviceOne = mc?.createNewAgent("Service-Agent-One", ServiceAgent::class.java.name,
                    arrayOf<Any>())
            val serviceTwo = mc?.createNewAgent("Service-Agent-Two", ServiceAgent::class.java.name,
                    arrayOf<Any>())
            val serviceThree = mc?.createNewAgent("Service-Agent-Three", ServiceAgent::class.java.name,
                    arrayOf<Any>())

            serviceOne?.start()
            serviceTwo?.start()
            serviceThree?.start()

            val searcher = mc?.createNewAgent("Sercher-Agent", SearcherAgent::class.java.name,
                    arrayOf<Any>())
            searcher?.start()

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    companion object {
        private var mc: AgentContainer? = null
    }

}