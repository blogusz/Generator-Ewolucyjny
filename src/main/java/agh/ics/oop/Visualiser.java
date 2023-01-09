package agh.ics.oop;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
Karolina Klisz
 */

public class Visualiser extends Application {

    private GridPane grid;


    //tu mamy nazwy

    private TextField mapWidthText;
    private TextField mapHeightText;

    private TextField howManyGrassesDailyText;
    private TextField grassEnergyText;
    private TextField grassGrowVariantText;
    private TextField startingAnimalsText;
    private TextField animalsStartEnergyText;
    private TextField energyToBreedText;
    private TextField breedEnergyCostText;
    private TextField minNumOfMutationsText;
    private TextField maxNumOfMutationsText;
    private TextField mutationVariantText;
    private TextField genotypeLengthText;
    private TextField animalsBehaviourText;

    //a tu labels
    private Label mapWidthLabel;
    private Label mapHeightLabel;
    private Label mapVariantLabel;
    private Label howManyGrassesDailyLabel;
    private Label grassEnergyLabel;
    private Label grassGrowVariantLabel;
    private Label startingAnimalsLabel;
    private Label animalsStartEnergyLabel;
    private Label energyToBreedLabel;
    private Label breedEnergyCostLabel;
    private Label minNumOfMutationsLabel;
    private Label maxNumOfMutationsLabel;
    private Label mutationVariantLabel;
    private Label genotypeLengthLabel;
    private Label animalsBehaviourLabel;

    private Label text;
    private Label text1;
    private Button start;





    public void start(Stage primaryStage){
        primaryStage.setTitle("Evolution Simulation");
        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(30,30,30,30));
        Scene scene = new Scene(grid, 400, 700);
        mapWidthLabel = new Label("Map Width");
        grid.add(mapWidthLabel, 0,0);
        mapWidthText = new TextField("30");
        grid.add(mapWidthText, 1,0);
        mapHeightLabel = new Label("Map Height");
        grid.add(mapHeightLabel, 0,1);
        mapHeightText = new TextField("30");
        grid.add(mapHeightText, 1, 1);
        mapVariantLabel = new Label("Map Variant");
        grid.add(mapVariantLabel, 0,2);
        ChoiceBox<String> mapVariantText = new ChoiceBox<>();
        mapVariantText.getItems().add("Globe");
        mapVariantText.getItems().add("Hell Portal");
        mapVariantText.setValue("Globe");
        grid.add(mapVariantText, 1,2);
        howManyGrassesDailyLabel = new Label("Daily Grass");
        grid.add(howManyGrassesDailyLabel,0,3);
        howManyGrassesDailyText = new TextField("1");
        grid.add(howManyGrassesDailyText, 1, 3);
        grassEnergyLabel = new Label("Grass energy");
        grid.add(grassEnergyLabel, 0, 4);
        grassEnergyText = new TextField("15");
        grid.add(grassEnergyText, 1,4);
        grassGrowVariantLabel = new Label("Grass Variant");
        grid.add(grassGrowVariantLabel,0,5);
        ChoiceBox<String> grassGrowVariantText = new ChoiceBox<>();
        grassGrowVariantText.getItems().add("Forested Equators");
        grassGrowVariantText.getItems().add("Toxic Corpses");
        grassGrowVariantText.setValue("Forested Equators");
        grid.add(grassGrowVariantText, 1,5);
        startingAnimalsLabel = new Label("Number of animals");
        grid.add(startingAnimalsLabel, 0, 6);
        startingAnimalsText = new TextField("15");
        grid.add(startingAnimalsText, 1, 6);
        animalsStartEnergyLabel = new Label("Animal energy");
        grid.add(animalsStartEnergyLabel, 0, 7);
        animalsStartEnergyText = new TextField("40");
        grid.add(animalsStartEnergyText,1,7);
        energyToBreedLabel = new Label("Energy to breed");
        grid.add(energyToBreedLabel, 0, 8);
        energyToBreedText = new TextField("10");
        grid.add(energyToBreedText,1,8);
        breedEnergyCostLabel = new Label("Breed Energy Cost");
        grid.add(breedEnergyCostLabel,0,9);
        breedEnergyCostText = new TextField("10");
        grid.add(breedEnergyCostText, 1, 9);
        minNumOfMutationsLabel = new Label("Minimum Mutations");
        grid.add(minNumOfMutationsLabel, 0, 10);
        minNumOfMutationsText = new TextField("1");
        grid.add(minNumOfMutationsText, 1, 10);
        maxNumOfMutationsLabel = new Label("Maximum Mutations");
        grid.add(maxNumOfMutationsLabel, 0, 11);
        maxNumOfMutationsText = new TextField("5");
        grid.add(maxNumOfMutationsText, 1, 11);
        mutationVariantLabel = new Label("Mutation Variant");
        grid.add(mutationVariantLabel, 0, 12);
        ChoiceBox<String> mutationVariantText = new ChoiceBox<>();
        mutationVariantText.getItems().add("Full Randomness");
        mutationVariantText.getItems().add("Slight Correction");
        mutationVariantText.setValue("Full Randomness");
        grid.add(mutationVariantText, 1, 12);
        genotypeLengthLabel = new Label("Genotype Length");
        grid.add(genotypeLengthLabel, 0, 13);
        genotypeLengthText = new TextField("5");
        grid.add(genotypeLengthText, 1,13);
        animalsBehaviourLabel = new Label("Animals Behavior");
        grid.add(animalsBehaviourLabel, 0, 14);
        ChoiceBox<String> animalsBehaviourText = new ChoiceBox<>();
        animalsBehaviourText.getItems().add("Full Predestination");
        animalsBehaviourText.getItems().add("Some Madness");
        animalsBehaviourText.setValue("Some Madness");
        grid.add(animalsBehaviourText,1,14);

        start = new Button("Uruchom symulacje");
        grid.add(start, 1,17);


        primaryStage.setScene(scene);
        primaryStage.show();




        start.setOnAction(event -> {
            Parent root;
            grid = new GridPane();
            Stage stage = new Stage();
            stage.setTitle("Simulation");

            text = new Label("Nie udalo nam sie zwizualizowac :c");
            text1 = new Label("Symulacja w terminalu! :)");
            Scene scene1 = new Scene(grid, 200,100);
            grid.setAlignment(Pos.BASELINE_CENTER);
            grid.add(text, 10,10,3,3);
            grid.add(text1, 10,15,3,3);
            stage.setScene(scene1);
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        });


    }

}

