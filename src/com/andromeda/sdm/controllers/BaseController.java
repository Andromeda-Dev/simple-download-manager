package com.andromeda.sdm.controllers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import com.andromeda.sdm.dmcore.Download;
import com.andromeda.sdm.poc.DemoDownload;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class BaseController implements Initializable, Observer 
{
	@FXML
	private TextField urlTextfield;
    
	@FXML
	private Button addDownload;

	@FXML
	private TableView<Download> downloadTable;
	
	@FXML private TableColumn<Download, String> urlColumn;
	@FXML private TableColumn<Download, String> sizeColumn;
	@FXML private TableColumn<Download, String> progressColumn;
	@FXML private TableColumn<Download, String> statusColumn;

	@FXML
	private Button pauseButton;

	@FXML
	private Button resumeButton;

	@FXML
	private Button cancelButton;

	@FXML
	private Button clearButton;

	ObservableList<Download> downloads = FXCollections.observableArrayList();
	
	@FXML
	void onClickAddButton(ActionEvent event)
	{
		/*System.out.println(urlTextfield.getText());*/
		String urlString = urlTextfield.getText();
		Download d;
		try
		{
			d = new Download(new URL(urlString));
			d.addObserver(this);
			downloads.add(d);
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		urlColumn.setCellValueFactory(new PropertyValueFactory<Download, String>("URL"));
		sizeColumn.setCellValueFactory(new PropertyValueFactory<Download, String>("size"));
		progressColumn.setCellValueFactory(new PropertyValueFactory<Download, String>("progress"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<Download, String>("status"));
		
		downloadTable.setItems(getPeople());
	}
	
	public ObservableList<Download> getPeople()
	{
		return downloads;
	}

	@Override
	public void update(Observable o, Object arg)
	{
		int index = downloads.indexOf(o);
		Download dchanged = (Download)downloads.get(index);
		downloads.set(index, dchanged);
//		System.out.println(((Download)o).getProgress());
	}
}
