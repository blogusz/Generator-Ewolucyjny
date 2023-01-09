package agh.ics.oop;

import java.util.ArrayList;
import java.util.Random;

public class Genotype {
    public int[] genotype;
    public int numberOfGenes;
    public int nextGen=0;
    public String mutationVariant;
    public ArrayList<Integer> usedGenes=new ArrayList<>();
    public int minNumOfMutations;
    public int maxNumOfMutations;

    /**
    Karolina Klisz
     */
    public Genotype(int numberOfGenes, String mutationVariant)
    {
        this.numberOfGenes = numberOfGenes;
        this.genotype = new int[numberOfGenes];
        this.mutationVariant=mutationVariant;

        Random rand = new Random();

        for (int i = 0; i < this.genotype.length; i++) {
            this.genotype[i] = rand.nextInt(8);
        }
    }
    /**
    Karolina Klisz
     */
    public Genotype(Animal weakerGenotype, Animal strongerGenotype,int minNumOfMutations, int maxNumOfMutations) // konstruktor potomka
    {
        this.minNumOfMutations = minNumOfMutations;
        this.maxNumOfMutations = maxNumOfMutations;
        Random random = new Random();
        int allGenotypes = weakerGenotype.genotype.genotype.length;
        this.genotype = new int[allGenotypes];
        int allEnergy = weakerGenotype.getEnergy() + strongerGenotype.getEnergy();
        int weakEnergy;
        int strongEnergy;
        weakEnergy = (weakerGenotype.getEnergy() * 100) / allEnergy; //udział procentowy tych genów
        strongEnergy = (strongerGenotype.getEnergy() * 100) / allEnergy; //tak naprawdę chyba potrzebujemy tylko silniejszego, bo słabszy to dopełnienie do 100%

        int leftOrRight = random.nextInt(2);

        if (leftOrRight == 0) // lewa strona genotypu silniejszego rodzica
        {
            int dividePoint = ((allGenotypes * strongEnergy) / 100) - 1; // zaczynamy lewa strone
            for (int i = 0; i <= dividePoint; i++) // pierwsze geny
            {
                this.genotype[i] = strongerGenotype.genotype.genotype[i];
            }
            for (int i = dividePoint + 1; i < allGenotypes; i++) // drugie geny słabszego rodzica
            {
                this.genotype[i] = weakerGenotype.genotype.genotype[i];
            }
        } else // prawa strona genotypu silniejszego rodzica
        {
            int dividePoint = ((allGenotypes * weakEnergy) / 100) - 1; // zaczynamy lewa strone od 25% genow od slabszego rodzica
            for (int i = 0; i <= dividePoint; i++) // pierwsze 1/4 genow od slabszego rodzica
            {
                this.genotype[i] = weakerGenotype.genotype.genotype[i];
            }
            for (int i = dividePoint + 1; i < allGenotypes; i++) // drugie 3/4 genow od silniejszego rodzica
            {
                this.genotype[i] = strongerGenotype.genotype.genotype[i];
            }
        }


        int howManyMutations;

        Random r = new Random();
        howManyMutations = r.nextInt((this.maxNumOfMutations - this.minNumOfMutations) + 1) + this.minNumOfMutations;

        ArrayList<Integer> mutatedGenes = new ArrayList<>();

        for (int i = 0; i < howManyMutations; i++) //tyle ile chcemy mutacji
        {
            int randomGen; //wybieramy losowy gen do mutacji
            while (true) {
                randomGen = random.nextInt(this.genotype.length);
                if (!mutatedGenes.contains(randomGen)) //żebyśmy wielokrotnie nie mutowali tego samego genu
                {
                    mutatedGenes.add(randomGen);
                    break;
                }
            }

            int mutatedGen;
            if (this.mutationVariant.equals("fullRandom"))
                mutatedGen = fullRandom(randomGen);
            else
                mutatedGen = slightCorrection(randomGen);

            this.genotype[randomGen] = mutatedGen;


        }
    }

    /**
    Karolina Klisz
     */
    public int fullRandom(int gen) //pelna losowosc
    {
        Random random = new Random();
        while (true)
        {
            int randomGene = random.nextInt(8);
            if (gen != randomGene)
            {
                gen=randomGene;
                break;
            }
        }
        return gen;
    }

    /**
    Karolina Klisz
     */
    public int slightCorrection(int gen) //lekka korekta
    {
        Random random = new Random();
        int upDown = random.nextInt(2);
        if (upDown == 0) //cofamy sie o jeden
        {
            if(gen==0)
            {
                gen=7;
            }
            else
            {
                gen-=1;
            }

        }
        if (upDown == 1)
        {
            if(gen == 7)
            {
                gen = 0;
            }
            gen+=1;
        }
        return gen;
    }


    /**
     *Bogusz Laszczyk
     */
    public int nextGenFromGenotype() //zwraca gen (indeks), według którego zwierzak ma się ruszyć
    {
        checkUsedGenes();
        if(this.nextGen==this.numberOfGenes)
        {
            this.nextGen=0;
        }

        this.nextGen+=1;

        if(this.usedGenes.contains(this.nextGen))
        {
            do {
                this.nextGen+=1;
            }while (this.usedGenes.contains(this.nextGen));

        }
        this.usedGenes.add(this.nextGen);

        if(this.nextGen==0)
        {
            return nextGen;

        }
        else
        {
            return nextGen-1;
        }


    }

    /**
     *Bogusz Laszczyk
     */
    public int startFromRandomGen()
    {
        checkUsedGenes();

        Random random=new Random();
        int randomStart;
        do {
            randomStart = random.nextInt(this.numberOfGenes);
        }while (this.usedGenes.contains(randomStart));

        this.nextGen=randomStart;
        this.usedGenes.add(randomStart);

        return randomStart;

    }


    /**
     *Bogusz Laszczyk
     */
    public int getRandomGen()
    {
        checkUsedGenes();

        Random random=new Random();
        int randomGen;
        do {
            randomGen = random.nextInt(this.numberOfGenes);
        }while (this.usedGenes.contains(randomGen));

        this.nextGen= randomGen;
        this.usedGenes.add(randomGen);


        return randomGen;

    }


    /**
     *Bogusz Laszczyk
     */
    public void checkUsedGenes()
    {
        if(this.usedGenes.size()==this.numberOfGenes) //jesli wykorzystalismy juz kazdy gen
        {
            this.usedGenes.clear();
        }
    }


    /**
     *Bogusz Laszczyk
     */
    public String toString()
    {
        StringBuilder result = new StringBuilder();
        result.append("[");
        result.append(this.genotype[0]);
        for (int i = 1; i < this.genotype.length; i++)
        {
            result.append(", ");
            result.append(this.genotype[i]);
        }
        result.append("]");

        return result.toString();
    }

}
