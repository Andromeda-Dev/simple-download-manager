package com.andromeda.sdm.dmfx;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application
{

	private Stage primaryStage;
	private AnchorPane rootPane;

	@Override
	public void start(Stage primaryStage) throws IOException
	{
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Test FXMLLoader");
		showMainView();
	}

	private void showMainView() throws IOException
	{
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("../view/MainView.fxml"));
		rootPane = loader.load();

		Scene scene = new Scene(rootPane);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
