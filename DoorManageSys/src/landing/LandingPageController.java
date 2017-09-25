package landing;

import java.net.URL;
import java.util.ResourceBundle;

import application.*;

/**
 * The LandingPageController class defines a controllers class
 * for the landing page of this application. The Landing Page is the 
 * main page of this application.
 */

import javafx.application.Platform;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LandingPageController implements Initializable {
	
	@FXML private Label inventory;
	
	@FXML private Label orders;
	
	@FXML private Label quote;
	
	@FXML private Label users;
	
	@FXML private Label logout;

	@FXML private Label userIdentifierLabel;
	
	/**
	 * The search button
	 */
	@FXML private Button searchButton;
	
	/**
	 * The textfield where the term to be searched is inputted
	 */
	@FXML private TextField searchTextField;
	
	/**
	 * Initializes a LandingPageController object.
	 */
	public LandingPageController() {
		
	}
	
	/**
	 * Handles the user interaction with the the menu labels in the
	 * Landing page.
	 * @param ae An ActionEvent
	 */
	@FXML private void handleMenu(MouseEvent me) {
		Object source = me.getSource();
		
		if(source == logout) {
			MasterController.getInstance().logoutPressed();
			MasterController.getInstance().changeView(PageTypes.LOGIN_PAGE);
		}else if(source == inventory) {
			this.applyEffectOnMenuLabel(inventory);
			this.changeViewOnLabelClick(inventory, PageTypes.INVENTORY_LIST_PAGE);
		}else if(source == orders) {
			this.applyEffectOnMenuLabel(orders);
		}else if(source == quote) {
			this.applyEffectOnMenuLabel(quote);
		}else if(source == users) {
			this.applyEffectOnMenuLabel(users);
			this.changeViewOnLabelClick(users, PageTypes.VIEW_USERS_PAGE);
		}
	}
	
	@FXML private void handleSearch(ActionEvent ae) {
		
	}

	public void initialize(URL location, ResourceBundle resources) {
		this.userIdentifierLabel.setText("Welcome " + MasterController.getInstance().getUser().getName());
	}
	
	private void applyEffectOnMenuLabel(Label label) {
		label.setOnMouseEntered(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent e) {
		        label.setScaleX(1.5);
		        label.setScaleY(1.5);
		    }
		});
		
		label.setOnMouseExited(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent e) {
		        label.setScaleX(1.0);
		        label.setScaleY(1.0);
		    }
		});
	}
	
	private void changeViewOnLabelClick(Label label, PageTypes pageType) {
		label.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				MasterController.getInstance().changeView(pageType);
			}
		});
	}
}
