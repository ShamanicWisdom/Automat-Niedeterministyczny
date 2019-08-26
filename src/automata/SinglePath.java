/*
 * Lingwistyka Matematyczna Zadanie 2 - Automat Niedterministyczny - Sprawdzanie ciągów.
 * Klasa - encja przechowujaca dane o sciezce.
 * Szymon Zawadzki 221515.
 */

package automata;

// Laczenie z reszta wlasnych klas //

// Reszta klas //

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Szaman
 */

public class SinglePath implements java.io.Serializable 
{
    private Integer pathId;
    private String path;
    private String pathHistory;
    private String successInfo;
    private Integer alphabeticalPairs;
    private Integer digitalPairs;

    private IntegerProperty pathIdProperty = null;
    private StringProperty pathProperty = null;
    private StringProperty pathHistoryProperty = null;
    private StringProperty successInfoProperty = null;
    private IntegerProperty alphabeticalPairsProperty = null;
    private IntegerProperty digitalPairsProperty = null;
    
    public SinglePath() 
    {
        
    }
    
    public SinglePath(int pathId, String path, String pathHistory, String successInfo, int alphabeticalPairs, int digitalPairs) 
    {
        this.pathId = pathId;
        this.path = path;
        this.pathHistory = pathHistory;
        this.successInfo = successInfo;
        this.alphabeticalPairs = alphabeticalPairs;
        this.digitalPairs = digitalPairs;
    }
       
    public int getPathId() 
    {
        return this.pathId;
    }
    
    public void setPathId(int pathId) 
    {
        this.pathId = pathId;
    }
    
    public String getPath() 
    {
        return this.path;
    }
    
    public void setPath(String path) 
    {
        this.path = path;
    }
    
    public String getPathHistory() 
    {
        return this.pathHistory;
    }
    
    public void setPathHistory(String pathHistory) 
    {
        this.pathHistory = pathHistory;
    }
    
    public String getSuccessInfo() 
    {
        return this.successInfo;
    }
    
    public void setSuccessInfo(String successInfo) 
    {
        this.successInfo = successInfo;
    }
    
    public int getDigitalPairs() 
    {
        return this.digitalPairs;
    }
    
    public void setDigitalPairs(int digitalPairs) 
    {
        this.digitalPairs = digitalPairs;
    }
    
    public int getAlphabeticalPairs() 
    {
        return this.alphabeticalPairs;
    }
    
    public void setAlphabeticalPairs(int alphabeticalPairs) 
    {
        this.alphabeticalPairs = alphabeticalPairs;
    }
    
    /*Properties*/
    
    public IntegerProperty pathIdProperty() 
    {
        pathIdProperty  = new SimpleIntegerProperty(pathId);
        return pathIdProperty;
    }
    
    public StringProperty pathProperty() 
    {
        pathProperty  = new SimpleStringProperty(path);
        return pathProperty;
    }
    
    public StringProperty pathHistoryProperty() 
    {
        pathHistoryProperty  = new SimpleStringProperty(pathHistory);
        return pathHistoryProperty;
    }
    
    public StringProperty successInfoProperty() 
    {
        successInfoProperty  = new SimpleStringProperty(successInfo);
        return successInfoProperty;
    }
    
    public IntegerProperty alphabeticalPairsProperty() 
    {
        alphabeticalPairsProperty  = new SimpleIntegerProperty(alphabeticalPairs);
        return alphabeticalPairsProperty;
    }
    
    public IntegerProperty digitalPairsProperty() 
    {
        digitalPairsProperty  = new SimpleIntegerProperty(digitalPairs);
        return digitalPairsProperty;
    }
}


