package agh.ics.oop;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Simulation implements IEngine
{
    private final int startingAnimals;
    public Multimap<Vector2d, Animal> animals = HashMultimap.create();
    private final int width;
    private final int height;
    private final String mapVariant;
    private final String grassGrowVariant;
    private final int breedEnergyCost;
    private final int minNumOfMutations;
    private final int maxNumOfMutations;
    private final String mutationVariant;
    private final int genotypeLength;
    private final String animalsBehavior;

    private final int grassEnergy;
    private final int moveCost=5;
    private final int energyToBreed;
    private final int startEnergy;
    private final int howManyGrassesDaily;

    private final GrassField map;
    public int rounds=100;
    int currentRound=0;
    Statistics statistics=new Statistics();
    private final Tracker tracker;
//    Visualiser visualiser;
//    Stage primaryStage;


    /**
     *Bogusz Laszczyk
     */
    public Simulation(int width, int height, String mapVariant, int howManyGrassesDaily, int grassEnergy, String grassGrowVariant, int startingAnimals, int startEnergy, int energyToBreed, int breedEnergyCost, int minNumOfMutations, int maxNumOfMutations, String mutationVariant, int genotypeLength, String animalsBehavior)
    {
        this.map=new GrassField(width, height, mapVariant, grassGrowVariant, grassEnergy, howManyGrassesDaily);
        this.width = width;
        this.height = height;
        this.mapVariant = mapVariant;
        this.howManyGrassesDaily = howManyGrassesDaily;
        this.grassEnergy = grassEnergy;
        this.grassGrowVariant = grassGrowVariant;
        this.startingAnimals = startingAnimals;
        this.startEnergy = startEnergy;
        this.energyToBreed = energyToBreed;
        this.breedEnergyCost = breedEnergyCost;
        this.minNumOfMutations = minNumOfMutations;
        this.maxNumOfMutations = maxNumOfMutations;
        this.mutationVariant = mutationVariant;
        this.genotypeLength = genotypeLength;
        this.animalsBehavior = animalsBehavior;

        ArrayList<Vector2d> initialPositions = new ArrayList<>();
        while (startingAnimals > 0)
        {
            Vector2d randomPosition;
            while (true)
            {
                randomPosition=map.randomAnimalPosition();
                if (!initialPositions.contains(randomPosition))
                {
                    initialPositions.add(randomPosition);
                    break;
                }
            }

            startingAnimals--;
        }

        for (Vector2d initialPosition:initialPositions)
        {
            Animal animal=new Animal(map,initialPosition,startEnergy, energyToBreed, breedEnergyCost, genotypeLength,mutationVariant,animalsBehavior);
            this.animals.put(initialPosition,animal);
            map.place(animal);
            this.statistics.addToMostCommonGenes(animal.getGenotype());
        }
        this.tracker=new Tracker(this);

    }


    /**
     *Bogusz Laszczyk
     */
    public void run()
    {
        for (int i = 0; i < this.rounds; i++) {
            if (this.animals.isEmpty())
            {
                System.out.println("\nWszystkie zwierzątka umarły :c");
                break;
            }
            else {
                System.out.println(animals.keySet());

                this.dying();
                this.moving();
                this.eat();
                this.breeding();
                this.planting();
                this.updateStatistics();

                // this.visualiser.drawMap();

           }
        }
        System.out.println("\n\nKONIEC SYMULACJI\n\n");
    }


    /**
     *Bogusz Laszczyk
     */
    public void dying()
    {
        ArrayList<Animal> deadAnimals = this.map.removeDeadAnimals(this.animals);
        for (Animal deadAnimal:deadAnimals)
        {
            //deadAnimal.removeObserver((IPositionChangeObserver) this);
            this.animals.remove(deadAnimal.getPosition(),deadAnimal);
            this.statistics.removeFromMostCommonGenes(deadAnimal.getGenotype());
            this.statistics.changeAverageAnimalLifeTime(deadAnimal.getAge());

            if(this.tracker.isBeingTracked())
            {
                try
                {
                    if(this.tracker.trackedAnimal.equals(deadAnimal))
                    {
                        this.tracker.setDeathDate(this.rounds);
                    }
                }
                catch (NullPointerException exception)
                {
                    return;
                }

            }
        }
    }


    /**
     *Bogusz Laszczyk
     */
    public void moving()
    {
        List<Animal> currentListOfAnimals = new ArrayList<>(this.animals.values());

        for (Animal animal : currentListOfAnimals)
        {
            Vector2d oldPosition = animal.getPosition();

            animal.move(this.moveCost);
            Vector2d newPosition = animal.getPosition();
            this.animals.remove(oldPosition, animal);
            this.animals.put(newPosition, animal);
        }
    }


    /**
     *Bogusz Laszczyk
     */
    public void eat()
    {
        HashMap<Grass, Animal> strongestAnimals = this.map.eating();
        for (Grass grass :strongestAnimals.keySet())
        {
            this.map.grassAll.remove(grass);
        }
        for(Animal animal : strongestAnimals.values())
        {
            animal.changeEnergy(this.grassEnergy);
            animal.howMuchGrassEaten +=1;
        }
    }


    /**
     *Bogusz Laszczyk
     */
    public void breeding()
    {
        ArrayList<Animal> children = this.map.breed();
        for(Animal child: children)
        {
            this.map.placeChild(child);
            this.animals.put(child.getPosition(),child);
            this.statistics.addToMostCommonGenes(child.getGenotype());
        }
    }


    /**
     *Bogusz Laszczyk
     */
    public void planting()
    {
        this.map.plantGrass();
    }


    /**
     *Bogusz Laszczyk :(
     */
    public void updateStatistics()
    {
        try {
            this.statistics.changeNumberOfLivingAnimals(this.animals.values().size());
            this.statistics.changeNumberOfGrass(this.map.grassAll.values().size());
            this.statistics.changeNumberOfUnoccupiedFields(this.width*this.height-this.animals.keySet().size());
            if(!this.animals.isEmpty())
            {
                this.statistics.changeAverageAnimalEnergy(this.animals);
            }

            for (Animal animal:this.animals.values())
            {
                animal.age+=1;
            }
            this.currentRound+=1;

            System.out.println(this.statistics.checkStatistics(this.currentRound));
        }
        catch (Exception exception)
        {
            return;
        }

    }

}
