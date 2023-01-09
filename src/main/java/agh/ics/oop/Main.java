package agh.ics.oop;

import javafx.application.Application;

public class Main
{
    public static void main(String[] args)
    {
        Application.launch(Visualiser.class, args);
        try {
            SettingsLoader settings = SettingsLoader.loadPropFromFile();

            int width=settings.getMapWidth();
            int height=settings.getMapHeight();
            String mapVariant=settings.getMapVariant();
            int howManyGrassesDaily=settings.getHowManyGrassesDaily();
            int grassEnergy=settings.getGrassEnergy();
            String grassGrowVariant=settings.getGrassGrowVariant();
            int startingAnimals=settings.getStartingAnimals();
            int startEnergy=settings.getAnimalsStartEnergy();
            int energyToBreed=settings.getEnergyToBreed();
            int breedEnergyCost=settings.getBreedEnergyCost();
            int minNumOfMutations= settings.getMinNumOfMutations();
            int maxNumOfMutations= settings.getMaxNumOfMutations();
            String mutationVariant= settings.getMutationVariant();
            int genotypeLength= settings.getGenotypeLength();
            String animalsBehavior= settings.getAnimalsBehavior();

            IEngine simulation=new Simulation(width, height, mapVariant, howManyGrassesDaily, grassEnergy, grassGrowVariant, startingAnimals, startEnergy, energyToBreed, breedEnergyCost, minNumOfMutations, maxNumOfMutations,mutationVariant,genotypeLength,animalsBehavior );
            simulation.run();


    }
        catch (Exception exception)
        {
            System.out.println(exception);
        }

    }
}