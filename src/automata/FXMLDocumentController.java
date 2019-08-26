/*
 * Lingwistyka Matematyczna Zadanie 2 - Automat Niedterministyczny - Sprawdzanie ciągów.
 * Szymon Zawadzki 221515.
 */

package automata;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Szaman
 */

public class FXMLDocumentController implements Initializable 
{
    //Pictures Stuff
    Image exit = new Image("automata/pictures/Exit.png");
    ImageView exitImage = new ImageView(exit);
    
    //FXML Stuff   
    @FXML
    private Label pathIdLabel;
    @FXML
    private Label pathLabel;
    @FXML
    private Label successInfoLabel;
    @FXML
    private Label alphabeticalPairsLabel;
    @FXML
    private Label digitalPairsLabel;
    @FXML
    private TextArea pathHistoryTextArea;
    @FXML
    Button loadFileButton = new Button();
    @FXML
    private Button exitButton;
    @FXML
    private TableView<SinglePath> pathesTable;
    @FXML
    private TableColumn<SinglePath, String> pathColumn;
    @FXML
    private TableColumn<SinglePath, String> isSuccessColumn;
    
    private final ObservableList<SinglePath> dataGatherer = FXCollections.observableArrayList();
    
    //Other Stuff
    private Main mainApp; 
    Stage primaryStage = new Stage();
    FileChooser fileChooser = new FileChooser();
    
    private final Desktop desktop = Desktop.getDesktop();
        
    ArrayList<String> statesList = new ArrayList<>();
    
    ArrayList<ArrayList<String>> mainList = new ArrayList<>(); //List with all states.
    ArrayList<ArrayList<String>> successfulList = new ArrayList<>(); //List with successful states only.
    
    ArrayList<String> currentStateList = new ArrayList<>();
    ArrayList<String> alphabetList = new ArrayList<>();
    
    ArrayList<String> q0List = new ArrayList<>();
    ArrayList<String> q1List = new ArrayList<>();
    ArrayList<String> q2List = new ArrayList<>();
    ArrayList<String> q3List = new ArrayList<>();
    ArrayList<String> q4List = new ArrayList<>();
    ArrayList<String> q5List = new ArrayList<>();
    ArrayList<String> q6List = new ArrayList<>();
    ArrayList<String> q7List = new ArrayList<>();
    ArrayList<String> q8List = new ArrayList<>();
    ArrayList<String> q9List = new ArrayList<>();
    ArrayList<String> q10List = new ArrayList<>();
    ArrayList<String> q11List = new ArrayList<>();
    ArrayList<String> q12List = new ArrayList<>();
    ArrayList<String> q13List = new ArrayList<>();
    ArrayList<String> q14List = new ArrayList<>();
    ArrayList<String> q15List = new ArrayList<>();
    ArrayList<String> q16List = new ArrayList<>();
    ArrayList<String> q17List = new ArrayList<>();
    ArrayList<String> q18List = new ArrayList<>();
    ArrayList<String> q19List = new ArrayList<>();
    ArrayList<String> q20List = new ArrayList<>();
       
    /********************************************/
    
    public void setMainApp(Main mainApp)
    {
        this.mainApp = mainApp;
              
    }
    
    //Config for fileChooser.
    private static void configureFileChooser(FileChooser fileChooser) 
    {      
        fileChooser.setTitle("Wybierz plik tekstowy");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Desktop"));                 
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
    }
    
    //Load event
    private void loadFile(File file) 
    {
        try 
        {
            try (FileReader fileReader = new FileReader(file); BufferedReader bufferedReader = new BufferedReader(fileReader)) 
            {
                statesList.clear();
                String singleStatePath = "";
                int singleStateASCII;
                while((singleStateASCII = bufferedReader.read()) != -1)
                {
                    char singleStateConverted = (char)singleStateASCII; //convert ASCII number to char.
                    //Check if opened file has correct characters.
                    if(singleStateConverted != '0' && singleStateConverted != '1' && singleStateConverted != '2' && singleStateConverted != '3' && 
                       singleStateConverted != '4' && singleStateConverted != 'a' && singleStateConverted != 'b' && singleStateConverted != 'c' && 
                       singleStateConverted != 'e' && singleStateConverted != 'f' && singleStateConverted != '#')
                    {
                        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                        Alert alert = new Alert(Alert.AlertType.ERROR, "", okButton);
                        alert.setTitle("Błędny plik");
                        alert.setHeaderText("Plik zawiera znaki,\nktóre nie należą do alfabetu!");
                        alert.setContentText("Proszę poprawić zawartość pliku lub wybrać inny.");

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == okButton)
                        {
                            alert.close();
                        } 
                        break;
                    }
                    
                    if(singleStateConverted != '#')
                    {
                        singleStatePath += String.valueOf(singleStateConverted);
                    }
                    else
                    {
                        statesList.add(singleStatePath);
                        singleStatePath = "";
                    }
                }
            }
            //Run automata for every sequence from generated list.
            for(int i = 0; i < statesList.size(); i++)
            {
                runAutomata(statesList.get(i), i);
            }
            //Populate table.
            pathesTable.setItems(dataGatherer);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Automata Script
    public void runAutomata(String statePath, int pathNumber)
    {
        List<String> currentStatesHistory = new ArrayList<>();
        List<String> currentStatesList = new ArrayList<>();
        SinglePath singlePath = new SinglePath();
                
        //Saving path credentials to object (needed for proper GUI table's proper presentation).
        singlePath.setPathId(pathNumber + 1); 
        singlePath.setPath(statePath);
        
        //Splitting string into single states.
        List<String> singleStatesList = new ArrayList<>();
        for(int i = 0; i < statePath.length(); i++) 
        {
            singleStatesList.add("" + statePath.charAt(i));
        }
        
        //Working on singleStatesList.
        for(int i = 0; i < singleStatesList.size(); i++)
        {
            String currentState = singleStatesList.get(i);
            int currentAlphabetIndex = alphabetList.indexOf(currentState);
            
            //Zero State splitter.
            currentStateList.clear(); //clear temporary list for new use.
            currentStateList.addAll(mainList.get(0)); //Copy possible current states pathes list.
            String secondState = currentStateList.get(currentAlphabetIndex).substring(3); //Save second state from list.
            
            //Add to history.
            currentStatesHistory.add("Q0 -> " + secondState);
            currentStatesList.add(secondState);
            
            //-1, newest state doesn't need upgrade now.
            for(int j = 0; j < currentStatesHistory.size() - 1; j++)
            {
                //if next state exists.
                if(!((currentStatesList.get(j)).equals("X")))
                {
                    int currentStateIndex = Integer.parseInt(currentStatesList.get(j).replaceAll("\\D+","")); //rip new main index out of current state;
                    currentStateList.clear(); //clear temporary list for new use.
                    currentStateList.addAll(mainList.get(currentStateIndex)); //Copy possible current states pathes list.
                    if(currentStateList.get(currentAlphabetIndex).equals("X"))
                    {
                        currentStatesHistory.set(j, currentStatesHistory.get(j) + " -> X");
                        currentStatesList.set(j, "X");
                    }
                    else
                    {
                        currentStatesHistory.set(j, currentStatesHistory.get(j) + " -> " + currentStateList.get(currentAlphabetIndex));
                        currentStatesList.set(j, currentStateList.get(currentAlphabetIndex));
                    }
                        
                }
                else
                {
                    currentStatesHistory.set(j, currentStatesHistory.get(j) + " -> X");
                }
                
                
            }
            System.out.println("Indexes: " + currentStatesList);
            System.out.println("-----------------------------------------------");
        }
        singlePath.setPathHistory(currentStatesHistory.toString());
        
        String successInfo = "Niepowodzenie";
        int alphabeticalPairs = 0;
        int digitalPairs = 0;
        
        //Check final states for additional data.
        for(int i = 0; i < currentStatesList.size(); i++)
        {
            for(int j = 0; j < successfulList.size(); j++)
            {
                if(currentStatesList.get(i).equals(successfulList.get(j).get(0))) //Successful list: first get - exact list, second get - exact index of chosen list.
                {
                    successInfo = "Sukces";
                    if(successfulList.get(j).get(10).equals("0") || successfulList.get(j).get(10).equals("1") || successfulList.get(j).get(10).equals("2") ||
                       successfulList.get(j).get(10).equals("3") || successfulList.get(j).get(10).equals("4"))
                    {
                        digitalPairs++;
                    }
                    else
                    {
                        alphabeticalPairs++;
                    }
                }
            }
        }
        
        singlePath.setSuccessInfo(successInfo); 
        singlePath.setAlphabeticalPairs(alphabeticalPairs);
        singlePath.setDigitalPairs(digitalPairs);
        
        dataGatherer.add(singlePath); //Add object to observer.
    }
    
    //Chosen path details:
    private void showPathDetails(SinglePath singlePath) 
    {
        if (singlePath != null) 
        {
            pathIdLabel.setText(String.valueOf(singlePath.getPathId()));
            pathLabel.setText(singlePath.getPath());
            successInfoLabel.setText(singlePath.getSuccessInfo());
            alphabeticalPairsLabel.setText(String.valueOf(singlePath.getAlphabeticalPairs()));
            digitalPairsLabel.setText(String.valueOf(singlePath.getDigitalPairs()));
            pathHistoryTextArea.setText(singlePath.getPathHistory());
        }
        else
        {
            pathIdLabel.setText("");
            pathLabel.setText("");
            successInfoLabel.setText("");
            alphabeticalPairsLabel.setText("");
            digitalPairsLabel.setText("");
            pathHistoryTextArea.setText("");
        }
    }
    
    //Exit Script.
    @FXML
    public void handleExit()
    {        
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Anuluj", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", okButton, cancelButton);
        alert.setTitle("Wyjście");
        alert.setHeaderText(null);
        alert.setContentText("Na pewno?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == okButton)
        {
            System.exit(0);
        } 
        else 
        {
            alert.close();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        //loadFile Initialization
        loadFileButton.setOnAction((final ActionEvent e) -> 
        {
            pathesTable.getItems().clear(); //Clear table.
            configureFileChooser(fileChooser);
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) 
            {
                loadFile(file);
                try 
                {
                    desktop.open(file);
                } 
                catch (IOException ex) 
                {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        //Exit initialization
        exitImage.setFitHeight(75);
        exitImage.setFitWidth(75);
        exitButton.setGraphic(exitImage);
        exitButton.setMaxHeight(75);
        exitButton.setMaxWidth(75);
        
        //Populate alphabetList
        alphabetList.addAll(Arrays.asList("0", "1", "2", "3", "4", "a", "b", "c", "e", "f"));
        
        //Populate lists
        q0List.addAll(Arrays.asList("Q0,Q1", "Q0,Q2", "Q0,Q3", "Q0,Q4", "Q0,Q5", "Q0,Q6", "Q0,Q7", "Q0,Q8", "Q0,Q9", "Q0,Q10"));
        q1List.addAll(Arrays.asList("Q11", "X", "X", "X", "X", "X", "X", "X", "X", "X"));
        q2List.addAll(Arrays.asList("X", "Q12", "X", "X", "X", "X", "X", "X", "X", "X"));
        q3List.addAll(Arrays.asList("X", "X", "Q13", "X", "X", "X", "X", "X", "X", "X"));
        q4List.addAll(Arrays.asList("X", "X", "X", "Q14", "X", "X", "X", "X", "X", "X"));
        q5List.addAll(Arrays.asList("X", "X", "X", "X", "Q15", "X", "X", "X", "X", "X"));
        q6List.addAll(Arrays.asList("X", "X", "X", "X", "X", "Q16", "X", "X", "X", "X"));
        q7List.addAll(Arrays.asList("X", "X", "X", "X", "X", "X", "Q17", "X", "X", "X"));
        q8List.addAll(Arrays.asList("X", "X", "X", "X", "X", "X", "X", "Q18", "X", "X"));
        q9List.addAll(Arrays.asList("X", "X", "X", "X", "X", "X", "X", "X", "Q19", "X"));
        q10List.addAll(Arrays.asList("X", "X", "X", "X", "X", "X", "X", "X", "X", "Q20"));
        q11List.addAll(Arrays.asList("Q11", "Q11", "Q11", "Q11", "Q11", "Q11", "Q11", "Q11", "Q11", "Q11", "0"));
        q12List.addAll(Arrays.asList("Q12", "Q12", "Q12", "Q12", "Q12", "Q12", "Q12", "Q12", "Q12", "Q12", "1"));
        q13List.addAll(Arrays.asList("Q13", "Q13", "Q13", "Q13", "Q13", "Q13", "Q13", "Q13", "Q13", "Q13", "2"));
        q14List.addAll(Arrays.asList("Q14", "Q14", "Q14", "Q14", "Q14", "Q14", "Q14", "Q14", "Q14", "Q14", "3"));
        q15List.addAll(Arrays.asList("Q15", "Q15", "Q15", "Q15", "Q15", "Q15", "Q15", "Q15", "Q15", "Q15", "4"));
        q16List.addAll(Arrays.asList("Q16", "Q16", "Q16", "Q16", "Q16", "Q16", "Q16", "Q16", "Q16", "Q16", "a"));
        q17List.addAll(Arrays.asList("Q17", "Q17", "Q17", "Q17", "Q17", "Q17", "Q17", "Q17", "Q17", "Q17", "b"));
        q18List.addAll(Arrays.asList("Q18", "Q18", "Q18", "Q18", "Q18", "Q18", "Q18", "Q18", "Q18", "Q18", "c"));
        q19List.addAll(Arrays.asList("Q19", "Q19", "Q19", "Q19", "Q19", "Q19", "Q19", "Q19", "Q19", "Q19", "e"));
        q20List.addAll(Arrays.asList("Q20", "Q20", "Q20", "Q20", "Q20", "Q20", "Q20", "Q20", "Q20", "Q20", "f"));
        
        //Add states lists to main list.
        mainList.add(q0List);
        mainList.add(q1List);
        mainList.add(q2List);
        mainList.add(q3List);
        mainList.add(q4List);
        mainList.add(q5List);
        mainList.add(q6List);
        mainList.add(q7List);
        mainList.add(q8List);
        mainList.add(q9List);
        mainList.add(q10List);
        mainList.add(q11List);
        mainList.add(q12List);
        mainList.add(q13List);
        mainList.add(q14List);
        mainList.add(q15List);
        mainList.add(q16List);
        mainList.add(q17List);
        mainList.add(q18List);
        mainList.add(q19List);
        mainList.add(q20List);
        
        //Add states lists to successful list.
        successfulList.add(q11List);
        successfulList.add(q12List);
        successfulList.add(q13List);
        successfulList.add(q14List);
        successfulList.add(q15List);
        successfulList.add(q16List);
        successfulList.add(q17List);
        successfulList.add(q18List);
        successfulList.add(q19List);
        successfulList.add(q20List);
        
        //Initialize table.
        pathColumn.setCellValueFactory(cellData -> cellData.getValue().pathProperty());
        isSuccessColumn.setCellValueFactory(cellData -> cellData.getValue().successInfoProperty()); 
        pathesTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showPathDetails(newValue));
        
    }    
}
