package com.andromeda.sdm.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class BaseController
{
	@FXML
	private TextField urlTextfield;
    
	@FXML
	private Button addDownload;

	@FXML
	private TableView<?> downloadTable;

	@FXML
	private Button pauseButton;

	@FXML
	private Button resumeButton;

	@FXML
	private Button cancelButton;

	@FXML
	private Button clearButton;

	@FXML
	void onClickAddButton(ActionEvent event)
	{
		/*System.out.println(urlTextfield.getText());*/
	}

	@FXML
	void onClickCancelButton(ActionEvent event)
	{
		
	}

	@FXML
	void onClickClearButton(ActionEvent event)
	{

	}

	@FXML
	void onClickPuseButton(ActionEvent event)
	{

	}

	@FXML
	void onClickResumeButton(ActionEvent event)
	{

	}
}
