package com.example.sport_project_with_db.controllers;
import com.example.sport_project_with_db.classes_for_cntrollers.Sportsmen;
import com.example.sport_project_with_db.db_actions.ageCategoryDb;
import com.example.sport_project_with_db.db_actions.sportClubDb;
import com.example.sport_project_with_db.db_actions.sportsmenDb;
import com.example.sport_project_with_db.db_actions.weightCategoryDb;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.time.LocalDate;

import java.util.ResourceBundle;

public class AddSportsmen implements Initializable {
    @FXML
    Button confirm_btn;
    @FXML
    TextField name_field;

    @FXML
    private ToggleGroup action;

    @FXML
    private RadioButton action_yes;

    @FXML
    private RadioButton action_no;

    @FXML
    private ComboBox<String> age_choice;

    @FXML
    private DatePicker birthDate_choice;

    @FXML
    private TextField draw_field;

    @FXML
    private ComboBox<String> genderChoice;

    @FXML
    private ComboBox<String> sportClub_choice;

    @FXML
    private ComboBox<String> weight_choice;


    public void update_sportsmen(Sportsmen sportsmen){
        name_field.setText(sportsmen.getName());
        birthDate_choice.setValue(LocalDate.parse(sportsmen.getAge()));
        genderChoice.setValue(sportsmen.getGender());
        age_choice.setValue(sportsmen.getAge_category());
        weight_choice.setValue(sportsmen.getWeight());
        sportClub_choice.setValue(sportsmen.getSport_club());
        draw_field.setText(String.valueOf(sportsmen.getDraw_num()));
        if(sportsmen.getAct()=="ДА"){
            action_yes.setSelected(true);
        }
        else {
            action_no.setSelected(true);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<String> genders = FXCollections.observableArrayList("Мужчина", "Женщина");
        genderChoice.setItems(genders);
        genderChoice.setValue("Мужчина");

        sportClub_choice.setItems(sportClubDb.getSportClub());
        sportClub_choice.setValue(sportClubDb.getFirst());

        age_choice.setItems(ageCategoryDb.getAge());
        age_choice.setValue(ageCategoryDb.getFirst());

        weight_choice.setItems(weightCategoryDb.getWeight());
        weight_choice.setValue(weightCategoryDb.getFirst());



        confirm_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {

                    boolean action_ = true;
                    if(action_no.isSelected()){
                        action_ = false;
                    }
                    String sport_club = sportClub_choice.getValue();
                    LocalDate birthDate = birthDate_choice.getValue();
                    int draw_num = Integer.parseInt(draw_field.getText());
                    String name = name_field.getText();
                    String weight_cat = weight_choice.getValue();
                    String age_cat = age_choice.getValue();
                    String gender = genderChoice.getValue();
                    if(sportsmenDb.getNames().contains(name)){
                        System.out.println(true);
                        sportsmenDb.updateSportsmen(name, draw_num, birthDate.toString(), sport_club, gender, action_, weight_cat, age_cat);
                    }
                    else{
                        sportsmenDb.write(name, draw_num, birthDate.toString(), sport_club, gender, action_, weight_cat, age_cat);
                    }


                    Stage stage = (Stage) confirm_btn.getScene().getWindow();
                    stage.close();

                }catch (Exception e){
                    System.out.println(e.getMessage());
                }

            }
        });
    }
}
