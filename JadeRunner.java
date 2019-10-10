
import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.*;


//Template Jade runner from java application


public class JadeRunner {


    public static void main(String[] args) throws StaleProxyException {
        Runtime rt = Runtime.instance();

        Profile p1 = new ProfileImpl();
        // p1.setParameter(...); // optional
        ContainerController mainContainer = rt.createMainContainer(p1);


        Object[] agentArgs1 = new String[]{"book", "1"};


        AgentController ac1 = mainContainer.createNewAgent("seller1", "BookSellerAgent", agentArgs1);
        ac1.start();

        // Object[] agentArgs2 = new String[]{"book"};
        AgentController ac2 = mainContainer.createNewAgent("buyer1", "BookBuyerAgent", new String[]{"book"});
        ac2.start();
    }

}
