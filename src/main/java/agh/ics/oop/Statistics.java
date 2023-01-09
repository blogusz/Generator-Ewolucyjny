package agh.ics.oop;

import com.google.common.collect.Multimap;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Statistics
{
    private int numberOfLivingAnimals;
    private int numberOfGrass ;
    private int unoccupiedFields=0;
    private HashMap<Genotype, Integer> mostCommonGenes = new HashMap<>();
    public int averageAnimalEnergy=0;
    private int averageAnimalLifeTime = 0;
    private int totalAnimalLifeTimes = 0;
    private int totalAnimalLivesValue = 0;



    /**
     *Bogusz Laszczyk
     */
    public void changeNumberOfLivingAnimals(int howManyAnimals)
    {
        this.numberOfLivingAnimals = howManyAnimals;
    }


    /**
     *Bogusz Laszczyk
     */
    public void changeNumberOfGrass(int howManyGrass)
    {
        this.numberOfGrass = howManyGrass;
    }


    /**
     *Bogusz Laszczyk
     */
    public void changeNumberOfUnoccupiedFields(int howManyUnoccupiedFields)
    {
        this.unoccupiedFields = howManyUnoccupiedFields;
    }


    /**
     *Bogusz Laszczyk
     */
    public void addToMostCommonGenes(Genotype genotype)
    {
        if(!this.mostCommonGenes.containsKey(genotype))
        {
            this.mostCommonGenes.put(genotype,1);
        }
        else
        {
            this.mostCommonGenes.replace(genotype,this.mostCommonGenes.get(genotype)+1);

        }

    }

    /**
     *Bogusz Laszczyk
     */
    public void removeFromMostCommonGenes(Genotype genotype)
    {
        try {
            this.mostCommonGenes.replace(genotype,this.mostCommonGenes.get(genotype)-1);
            if(this.mostCommonGenes.get(genotype)==0)
            {
                this.mostCommonGenes.remove(genotype);
            }
        }
        catch (Exception NullPointerException)
        {
            return;
        }

    }

    /**
     *Bogusz Laszczyk
     */
    public Genotype getMostCommonGenes()
    {
        return Collections.max(this.mostCommonGenes.entrySet(), Map.Entry.comparingByValue()).getKey();
    }



    /**
     *Bogusz Laszczyk
     */
    public void changeAverageAnimalEnergy(Multimap<Vector2d, Animal> animals)
    {
        try {
        int totalEnergy=0;
        int counter=0;
        for (Animal animal:animals.values()) {
            if (animal.getEnergy() > 0) {
                totalEnergy += animal.getEnergy();
                counter++;
            }
        }

            this.averageAnimalEnergy = totalEnergy/counter;
        }
        catch (Error error)
        {
            this.averageAnimalEnergy=0;
        }


    }



    /**
     *Bogusz Laszczyk
     */
    public void changeAverageAnimalLifeTime(int averageAnimalLifeTime)
    {
        this.totalAnimalLivesValue +=averageAnimalLifeTime;
        this.totalAnimalLifeTimes+=1;

        this.averageAnimalLifeTime= totalAnimalLivesValue /totalAnimalLifeTimes;

    }


    /**
     *Bogusz Laszczyk
     */
    public String checkStatistics(int round)
    {
        return "\nDay: " + round + "\nLiving animals: " + this.numberOfLivingAnimals +
                "\nGrass planted: " + this.numberOfGrass +
                "\nUnoccupied fields: " + this.unoccupiedFields +
                "\nAverage energy: " + this.averageAnimalEnergy +
                "\nAverage lifetime: " + this.averageAnimalLifeTime +
                "\nMost common genotype: " + this.getMostCommonGenes().toString();
    }

}
