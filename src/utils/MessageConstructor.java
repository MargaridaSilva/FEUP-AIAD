package utils;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import jade.core.AID;
import jade.lang.acl.ACLMessage;

public class MessageConstructor {

    public static ACLMessage getMessage(ArrayList<AID> receivers, int performative, String protocol, String ontology, String language, Serializable s) {
        
        ACLMessage msg = new ACLMessage(performative);

        for (AID receiver : receivers) {
            msg.addReceiver(receiver);
        }

        msg.setProtocol(protocol);
        msg.setOntology(ontology);
        msg.setLanguage(language);

        try {
            msg.setContentObject(s);
        } catch(IOException e) {
            e.printStackTrace();
        }
        
        return msg;
    }

    public static ACLMessage getMessage(AID receiver, int performative, String protocol, String ontology, String language, Serializable s) {
        
        ArrayList<AID> receivers = new ArrayList<>();
        receivers.add(receiver);
        return getMessage(receivers, performative, protocol, ontology, language, s);
    }
}