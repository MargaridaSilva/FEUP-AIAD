#!/bin/bash
cd ..

CLASSPATH="-classpath out/production/AIAD:lib/repast/mediaplayer.jar:lib/repast/repast.jar:lib/repast/openmap.jar:lib/repast/OpenForecast-0.4.0.jar:lib/repast/log4j-1.2.8.jar:lib/repast/asm.jar:lib/repast/jh.jar:lib/repast/velocity-dep-1.4.jar:lib/repast/colt.jar:lib/repast/trove.jar:lib/repast/jgap.jar:lib/repast/jmf.jar:lib/repast/jep-2.24.jar:lib/repast/violinstrings-1.0.2.jar:lib/repast/junit.jar:lib/repast/JTS.jar:lib/repast/geotools_r.jar:lib/repast/commons-logging.jar:lib/repast/plot.jar:lib/repast/geotools_repast.jar:lib/repast/commons-collections.jar:lib/repast/beanbowl.jar:lib/repast/ProActive.jar:lib/repast/multiplayer.jar:lib/repast/joone.jar:lib/repast/jakarta-poi.jar:lib/repast/jode-1.1.2-pre1.jar:lib/jade/jade.jar:lib/SAJaS/SAJaS.jar"

PARAMS="5 5 5 0.5 0.5 0.0075 0.0075"

java $CLASSPATH simulation.PredatorPreyModel $PARAMS

# int num_plants, int num_predators, int num_preys, double ratio_predators, double ratio_preys, double energy_expenditure_predators, double energy_expenditure_preys

