package agh.ics.oop;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class SettingsLoader {

    private int mapWidth;
    private int mapHeight;
    private String mapVariant;
    private int howManyGrassesDaily;
    private int grassEnergy;
    private String grassGrowVariant;
    private int startingAnimals;
    private int animalsStartEnergy;
    private int energyToBreed;
    private int breedEnergyCost;
    private int minNumOfMutations;
    private int maxNumOfMutations;
    private String mutationVariant;
    private int genotypeLength;
    private String animalsBehavior;


    /**
     *Bogusz Laszczyk
     */
    static public SettingsLoader loadPropFromFile() throws FileNotFoundException,IllegalArgumentException
    {
        Gson gson = new Gson();
        SettingsLoader settings = gson.fromJson(new FileReader("C:\\Users\\Bogusz\\IdeaProjects\\Projekt1\\src\\main\\java\\agh\\ics\\oop\\Settings.json"), SettingsLoader.class);
        settings.check();

        return settings;
    }

    /**
     *Karolina Klisz
     */
    public void check() throws IllegalArgumentException
    {
        if(this.mapWidth <= 0)
        {
            throw new IllegalArgumentException("Nieprawidlowa szerokosc mapy.");
        }
        if(this.mapHeight <= 0)
        {
            throw new IllegalArgumentException("Nieprawidlowa wysokosc mapy.");
        }
        if(!(this.mapVariant.equals("Globe") || this.mapVariant.equals("Hell Portal")))
        {
            throw new IllegalArgumentException("Wariantem mapy ma byc 'Globe' lub 'Hell Portal'.");
        }
        if(this.howManyGrassesDaily < 0)
        {
            throw new IllegalArgumentException("Dzienny przyrost trawy musi byc liczba nieujemna.");
        }
        if(this.grassEnergy< 0)
        {
            throw new IllegalArgumentException("Energia rosliny musi byc liczba nieujemna.");
        }
        if(!(this.grassGrowVariant.equals("Forested Equators") || this.grassGrowVariant.equals("Toxic Corpses")))
        {
            throw new IllegalArgumentException("Wiariantem wyrastania roslin ma byc 'Forested Equators' lub 'Toxic Corpses'.");
        }
        if(this.startingAnimals < 0)
        {
            throw new IllegalArgumentException("Liczba poczatkowych zwierzat musi byc liczba nieujemna.");
        }
        if(this.animalsStartEnergy < 0)
        {
            throw new IllegalArgumentException("Poczatkowa energia zwierzat musi byc liczba nieujemna.");
        }
        if(this.energyToBreed <= 0)
        {
            throw new IllegalArgumentException("Energia wymagana do rozmnazania musi byc liczba dodatnia.");
        }
        if(this.breedEnergyCost <= 0)
        {
            throw new IllegalArgumentException("Energia pobierana przy rozmnazaniu musi byc liczba dodatnia.");
        }
        if(this.minNumOfMutations < 0)
        {
            throw new IllegalArgumentException("Minimalna liczba mutacji musi byc liczba nieujemna.");
        }
        if(this.maxNumOfMutations > this.genotypeLength)
        {
            throw new IllegalArgumentException("Minimalna liczba mutacji musi byc niewieksza od dlugosci genotypu.");
        }
        if(!(this.mutationVariant.equals("Full Randomness") || this.mutationVariant.equals("Slight Correction")))
        {
            throw new IllegalArgumentException("Wariantem mutacji moze byc 'Full Randomness' lub 'Slight Correction'.");
        }
        if(this.genotypeLength <= 0)
        {
            throw new IllegalArgumentException("Dlugosc genotypu musi byc liczba dodatnia.");
        }
        if(!(this.animalsBehavior.equals("Full Predestination") || this.animalsBehavior.equals("Some Madness")))
        {
            throw new IllegalArgumentException("Wariantem zachowania zwierzat moze byc 'Full Predestination' lub 'Some Madness'.");
        }

    }

    public int getMapWidth() {
        return this.mapWidth;
    }

    public int getMapHeight() {
        return this.mapHeight;
    }

    public String getMapVariant()
    {
        return this.mapVariant;
    }

    public int getHowManyGrassesDaily()
    {
        return this.howManyGrassesDaily;
    }

    public int getGrassEnergy() {
        return this.grassEnergy;
    }

    public String getGrassGrowVariant()
    {
        return this.grassGrowVariant;
    }
    public int getAnimalsStartEnergy()
    {
        return this.animalsStartEnergy;
    }

    public int getStartingAnimals()
    {
        return this.startingAnimals;
    }

    public int getEnergyToBreed()
    {
        return this.energyToBreed;
    }

    public int getBreedEnergyCost()
    {
        return this.breedEnergyCost;
    }

    public int getMinNumOfMutations()
    {
        return this.minNumOfMutations;
    }

    public int getMaxNumOfMutations()
    {
        return this.maxNumOfMutations;
    }

    public String getMutationVariant()
    {
        return this.mutationVariant;
    }

    public int getGenotypeLength()
    {
        return this.genotypeLength;
    }

    public String getAnimalsBehavior()
    {
        return this.animalsBehavior;
    }


}