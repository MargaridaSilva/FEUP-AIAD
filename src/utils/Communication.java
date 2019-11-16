package utils;

public final class Communication {

    // ontologies
    public final class Ontology {

        public static final String VALIDATE_MOVE = "validate-move-ontology";
        public static final String VALIDATE_MOVE_GOAL = "validate-move-goal-ontology";
        public static final String PREDATOR_FIND_MATE = "find-mate-predator-ontology";
        public static final String PREDATOR_REACHED_FEMALE = "reach-female-predator-ontology";
        public static final String TELL_FOOD = "tell-food-ontology";
        public static final String FIND_FOOD = "find-food-ontology";
        public static final String TERMINATE = "terminating";

    }

    // service types
    public final class ServiceType {

        public static final String PREDATOR_MATE = "predator-mate";
        public static final String INFORM_WORLD = "inform-world";
    }

    // service names
    public final class ServiceName {

        public static final String TRACK_WORLD = "track-world";
        public static final String PREDATOR_REPRODUCTION = "predator-reproduction";
    }

    // languages
    public final class Language {

        public static final String MOVE = "move";
        public static final String PREDATOR_MATE = "predator-mate";
        public static final String FOOD = "food";
    }
}