package frontend;

import backend.CanvasState;
import backend.SelectionManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppLauncher extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		CanvasState canvasState = new CanvasState();
		SelectionManager selectionManager = new SelectionManager();

		MainFrame frame = new MainFrame(canvasState, selectionManager);

		Scene scene = new Scene(frame);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest(event -> System.exit(0));
	}
}
