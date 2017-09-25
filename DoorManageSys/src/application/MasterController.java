package application;

/**
 * The MasterController class defines a controller that acts as a
 * global access point for all of the classes in this Application.
 * It is single controller that handle the changing of screens, views,
 * or pages.
 * 
 * @author Team No Name Yet
 * @version 1.0
 */

import user.*;
import landing.*;
import inventory.*;
import login.*;
import product.*;

import java.io.IOException;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class MasterController {

	/**
	 * An instance of this class
	 */
	private static MasterController instance = null;
	
	/**
	 * The main pane of this application
	 */
	private BorderPane mainPane;
	
	/**
	 * A reference to the Stage of this application
	 */
	private Stage stage;
	
	/**
	 * The current logged in user of this application
	 */
	private DPMUser user;
	
	/**
	 * The Gateway to the User table in the DB
	 */
	private UsersGateway usersGateway;
	
	/**
	 * The Gateway to the Inventory table in the Database
	 */
	private InventoryGateway inventoryGateway;
	
	private ProductGateway productGateway;
	
	/**
	 * The page that the user is trying to view
	 */
	private PageTypes desiredPage;
	
	/**
	 * Boolean whether user logged out
	 */
	private boolean loggedOut;
	
	/**
	 * A Object holder for an object the user wishes to edit
	 * Mainly used when passing an object from a DetailController into a 
	 * EditController
	 */
	private Object editObj;
	
	/**
	 * Initialize a MasterController object.
	 */
	private MasterController() {
		try {
			this.usersGateway = new UsersGateway();
			this.inventoryGateway = new InventoryGateway();
			productGateway = new ProductGateway ();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		loggedOut = false;
		
		//Test
		//Inventory testInventory = new Inventory (0, "Stanley", "ND-384n", "???", "4.5 x 9 x 2", "default", "Heavy Weight", "US32D", 4.5, 9, 100, "hinge");
		//inventoryGateway.addInventory(testInventory);
		//System.out.print(inventoryGateway.searchInventory("hinge"));

	}
	
	/**
	 * This is the main function that is called when the user
	 * wishes to change the current page.
	 * @param pageType The type of page
	 * @return
	 */
	public boolean changeView(PageTypes pageType) {
		desiredPage = pageType;
		Parent view = null;
		
		view = this.loadView();
		
		if(desiredPage == PageTypes.LANDING_PAGE || desiredPage == PageTypes.LOGIN_PAGE) {
			this.setMainPane((BorderPane) view);
			Scene scene = new Scene(view);
			this.stage.setScene(scene);
			this.stage.show();
		} else if(desiredPage == PageTypes.VIEW_USERS_PAGE) {
			mainPane.setCenter(view);
			
		} else if(desiredPage == PageTypes.INVENTORY_DETAIL_PAGE) {
			mainPane.setRight(view);
		} else if(desiredPage == PageTypes.INVENTORY_LIST_PAGE) {
			FXMLLoader loader = null;
			Parent viewOne = null;
			
			loader = new FXMLLoader(getClass().getResource("/inventory/InventoryList_Page.fxml"));
			loader.setController(new InventoryListController());
			try {
				viewOne = loader.load();
				mainPane.setCenter(viewOne);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			loader = new FXMLLoader(getClass().getResource("/inventory/InventoryDetail_Page.fxml"));
			loader.setController(new InventoryDetailController(new Inventory()));
			try {
				viewOne = loader.load();
				mainPane.setRight(viewOne);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	public void displayAllInventory() {
		
	}
	
	/**
	 * Loads the view by loading the requested page and assigning
	 * a Controller for it.
	 * @return A Parent object specifying a base class for all scene nodes
	 */
	public Parent loadView() {
		Parent view = null;
		FXMLLoader loader = null;
		
		if(desiredPage == PageTypes.LANDING_PAGE) {
			loader = new FXMLLoader(getClass().getResource("/landing/Landing_Page.fxml"));
			loader.setController(new LandingPageController());
			
		} else if(desiredPage == PageTypes.VIEW_USERS_PAGE) {
			List<DPMUser> users = this.usersGateway.getUsers();
			loader = new FXMLLoader(getClass().getResource("/user/ViewUsers_Page.fxml"));
			loader.setController(new ViewUsersController(users));
			
		} else if(desiredPage == PageTypes.LOGIN_PAGE) {
			loader = new FXMLLoader(getClass().getResource("/login/Login_Page.fxml"));			
			loader.setController(new LoginController(new DPMUser()));
		
		} else if(desiredPage == PageTypes.INVENTORY_LIST_PAGE) {
			loader = new FXMLLoader(getClass().getResource("/inventory/InventoryList_Page.fxml"));
			loader.setController(new InventoryListController());
		
		} else if(desiredPage == PageTypes.INVENTORY_DETAIL_PAGE) {
			loader = new FXMLLoader(getClass().getResource("/inventory/InventoryDetail_Page.fxml"));
			loader.setController(new InventoryDetailController((Inventory)this.editObj));
		}
		
		try {
			view = loader.load();
		} catch(IOException io) {
			io.printStackTrace();
		}
		
		return view;
	}
	
	public boolean updateRightMenu(PageTypes pageType) {
		
		
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	public static MasterController getInstance() {
		if(instance == null) {
			instance = new MasterController();
		}
		
		return instance;
	}
	
	public BorderPane getMainPane() {
		return this.mainPane;
	}
	
	public void setMainPane(BorderPane mainPane) {
		this.mainPane = mainPane;
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public UsersGateway getUsersGateway() {
		return usersGateway;
	}
	
	public DPMUser getUser() {
		return this.user;
	}
	
	public void setUser(DPMUser user) {
		this.user = user;
	}
	
	public void logoutPressed() {
		if(this.loggedOut == false) {
			this.loggedOut = true;
		}
	}
	
	public boolean isLogoutPressed() {
		return this.loggedOut;
	}

	public InventoryGateway getInventoryGateway() {
		return inventoryGateway;
	}

	public void setEditObject(Object obj) {
		this.editObj = obj;
	}
}