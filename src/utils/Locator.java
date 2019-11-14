package utils;

import java.util.ArrayList;

import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.core.AID;
import sajas.core.Agent;
import sajas.domain.DFService;

public final class Locator {

    public static ArrayList<AID> locate(Agent sendingAgent, String serviceType) {
        DFAgentDescription agentDescription = new DFAgentDescription();
        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setType(serviceType);
        agentDescription.addServices(serviceDescription);
        DFAgentDescription[] result = new DFAgentDescription[0];
        try {
            result = DFService.search(sendingAgent, agentDescription);
        } catch(FIPAException e) {
            e.printStackTrace();
        }

        ArrayList<AID> aids = new ArrayList<>();
        for (DFAgentDescription ad : result) {
            aids.add(ad.getName());
        }
        return aids;
    }

    public static AID findObserver(Agent sendingAgent) {
        ArrayList<AID> observerAID = locate(sendingAgent, Communication.ServiceType.INFORM_WORLD);
   
        if(observerAID.size() == 0)
            return null;
        else
            return observerAID.get(0);
    }
}