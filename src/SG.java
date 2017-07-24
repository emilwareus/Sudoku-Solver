import java.util.Optional;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;

import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;


public class SG extends Application implements EventHandler<ActionEvent> {
	private Button Cbutton,Sbutton, Ubutton, CLbutton;
	private TilePane tilePane;
	private TextArea TimerText;
	private boolean isOk;
	private Matrix matrix;
	private Stage stage;
	public void start(Stage stage) { 
		this.stage=stage;
		
		matrix = new Matrix();
		tilePane = new TilePane(); 
		tilePane.setPrefColumns(9); 
		tilePane.setPrefRows(9);
		final int SIZE = 40;  
		clear();
		stage.show();
			}
	

	public static void main(String[] args) { launch(args); }
	@Override
	public void handle(ActionEvent event) {
		if(event.getSource()==Cbutton){				// Clear knappen
			System.exit(0);
			
		}else if(event.getSource()==Sbutton){		// Slove knappen		
			isOk=true;								// Is ok kollar om den är okej att skicka till Solve
			int counter=0;
			int NewMatrix[][]= new int[9][9];
			for(int k=0;k<9;k++){					// Kopierar över matrisen
				for(int i=0;i<9;i++){
					TextField t=(TextField) tilePane.getChildren().get(counter);
					counter++;
					char c;
					String s=t.getText();
					int InP=5;
					if(s.length()==1){
					c = s.charAt(0);
					InP=(int)c-48;					// Gör om string till int
					}
					if(s.length()==1 && InP<10 && InP>0){	// Kollar storlek och längt på inmatningen (Bokstäver inräknat där)
						
						int in=Integer.parseInt(s);				// Här blir det fel om man försöker mata in annat än 1-9
						if(in>=1 && in<=9){
							NewMatrix[k][i]=in;
						
				
						}
					}else if(s.length() != 0 && s.length()!=1){		// om inte föregående if-sats stämmer är den inte ok att solva pga längd
						isOk=false;
					}else if(InP>9 || InP<1){						// om inte föregående if-sats stämmer är den inte ok att solva pga storlek på InP
						isOk=false;
					}
				}
			}
			
			if(isOk==true){											// Solvar om det är rätt inmatning
				
			double startTime = System.currentTimeMillis();			// Startar tid
			matrix.MatrixAppend(NewMatrix);
			matrix.solve();
			double endTime   = System.currentTimeMillis();			// Stoppar tid	
			double totalTime = endTime - startTime;					// Kollar skillnaden
			String time=Double.toString(totalTime);					// Gör om till string
			TimerText.setText(time + " ms");
			
			if(matrix.isSolve()){									// Kollar om det löstes 
			int NewCounter=0;
			for(int k=0;k<9;k++){									// För in nya värden
				for(int i=0;i<9;i++){
					TextField t=(TextField) tilePane.getChildren().get(NewCounter);
					NewCounter++;
					String s=Integer.toString(matrix.getValue(k, i));
					t.setText(s);
					
				}		
				
			}
			}
			}
			if(isOk==false || matrix.isSolve()==false){			 // Felet hanters och användaren får ett val. OK= Gör inget, Clear=Clear matrix, GGWP = Ger upp 
				
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Error");
				alert.setHeaderText("Oops! Något gick fel. Du kanske matade in fel");
				alert.setContentText("Välj ett alternativ:");

				ButtonType buttonTypeOne = new ButtonType("Ok");
				ButtonType buttonTypeTwo = new ButtonType("Clear All");
				ButtonType buttonTypeCancel = new ButtonType("Avsluta", ButtonData.CANCEL_CLOSE);

				alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == buttonTypeOne){
				  
				} else if (result.get() == buttonTypeTwo) {
					 clear();
				} else {
				   System.exit(0);
				}
			}
			

		
		}else if(event.getSource()==CLbutton){				// Clearar matrisen
			clear();
		}else if(event.getSource()==Ubutton){				// Solvar Ones, denna är framförallt till för att testa solvern 
			int counter=0;
			int NewMatrix[][]= new int[9][9];
			for(int k=0;k<9;k++){
				for(int i=0;i<9;i++){
					TextField t=(TextField) tilePane.getChildren().get(counter);
					counter++;
					String s=t.getText();
					if(s.length()==1){
						int in=Integer.parseInt(s);				// Här blir det fel om man försöker mata in annat än 1-9
						if(in>=1 && in<=9){
							NewMatrix[k][i]=in;
						
				
						}else{
							
						}
					}
				}
			}
			matrix.MatrixAppend(NewMatrix);
			
			matrix.fillOne();
			int NewCounter=0;
			for(int k=0;k<9;k++){
				for(int i=0;i<9;i++){
					TextField t=(TextField) tilePane.getChildren().get(NewCounter);
					NewCounter++;
					if(matrix.getValue(k, i)!=0){
					String s=Integer.toString(matrix.getValue(k, i));
					t.setText(s);
					}
					
				}
			}
		}
		}
	private void clear(){								// Clear metoden. Ganska osmidig. Det Byggerom hela interfacet. 
		tilePane.getChildren().clear();
		for (int i = 1; i <= 9; i++) { 
			for (int k = 1; k <= 9; k++) { 
				Label label = new Label(); 
				int SIZE=40;
				label.setPrefSize(SIZE, SIZE); 
			
				if (i % 2 != 0 && k % 2 != 0 || i % 2 == 0 && k % 2 == 0) { 
					label.setStyle("-fx-background-color: #300000;");
				}
				TextField textf=new TextField();
				textf.setMaxSize(55, 55);
				textf.setMinSize(55, 55);
				tilePane.getChildren().add( textf); 
				if((i<4 && k<4)|| (i>6 && k>6)||(i<4 && k>6)|| (i>6 && k<4)||(i>3 && i<7) && (k>3 && k<7)){
					textf.setStyle("-fx-background-color: Red;"
							+"-fx-font-size: 24; -fx-label-padding: 10;"
							+"-fx-border-color: black;");
				}else{
				textf.setStyle("-fx-background-color: LightGrey;"
						+"-fx-font-size: 24; -fx-label-padding: 10;"
						+"-fx-border-color: black;");
				}
				}
		} 
		Cbutton= new Button("CLOSE");
		tilePane.getChildren().add( Cbutton);
		Cbutton.setOnAction(this);
		Sbutton= new Button("SOLVE");
		tilePane.getChildren().add( Sbutton);
		Sbutton.setOnAction(this);
		Ubutton= new Button("Ones");
		tilePane.getChildren().add( Ubutton);
		Ubutton.setOnAction(this);
		CLbutton= new Button("Clear");
		tilePane.getChildren().add( CLbutton);
		CLbutton.setOnAction(this);
		TimerText = new TextArea();
		TimerText.setText("");
		TimerText.setEditable(false);
		TimerText.setWrapText(true);
		TimerText.setPrefColumnCount(0);
		TimerText.setPrefRowCount(0);
		tilePane.getChildren().add(TimerText);
		Group root = new Group(tilePane); // fix storlek
		Scene scene = new Scene(root, 600, 600);
		stage.setScene(scene); 
		stage.setTitle("Sudoku"); 
	}
}


