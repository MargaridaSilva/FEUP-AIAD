package agents;

import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import sajas.core.Agent;
import sajas.domain.DFService;
import simulation.PredatorPreyModel;

public class GenericAgent extends Agent {
 
    protected PredatorPreyModel model;

    protected GenericAgent(PredatorPreyModel model) {
        
        this.model = model;
    }

    protected void registerService(String serviceType, String serviceName, String[] languages, String[] ontologies) {
        
        // Register the service in the yellow pages
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(this.getAID());

        ServiceDescription sd = new ServiceDescription();
        sd.setType(serviceType);
        sd.setName(serviceName);
        for (String language : languages)
            sd.addLanguages(language);
        for (String ontology : ontologies)
            sd.addOntologies(ontology);
        dfd.addServices(sd);

        try {
            DFService.register(this, dfd);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }
    }

    protected void deRegisterServices() {
        
        // Deregister from the yellow pages
        try {
            DFService.deregister(this);
        }
        catch(FIPAException fe) {
            fe.printStackTrace();
        }
    }

    public PredatorPreyModel getModel() {
        return model;
    }
}