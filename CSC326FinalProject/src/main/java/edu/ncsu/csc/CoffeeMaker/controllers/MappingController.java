package edu.ncsu.csc.CoffeeMaker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class for the URL mappings for CoffeeMaker. The controller returns
 * the approprate HTML page in the /src/main/resources/templates folder. For a
 * larger application, this should be split across multiple controllers.
 *
 * @author Kai Presler-Marshall
 */
@Controller
public class MappingController {

    /**
     * On a GET request to /index, the IndexController will return
     * /src/main/resources/templates/index.html.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( { "/index", "/" } )
    public String index ( final Model model ) {
        return "index";
    }

    /**
     * On a GET request to /loginsignup
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( { "/staffcreation", "/staffcreation.html" } )
    public String staffCreation ( final Model model ) {
        return "staffcreation";
    }

    /**
     * On a GET request to /recipe, the RecipeController will return
     * /src/main/resources/templates/recipe.html.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( { "/recipe", "/recipe.html" } )
    public String recipePage ( final Model model ) {
        return "recipe";
    }

    /**
     * On a GET request to /deleterecipe, the DeleteRecipeController will return
     * /src/main/resources/templates/deleterecipe.html.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( { "/deleterecipe", "/deleterecipe.html" } )
    public String deleteRecipeForm ( final Model model ) {
        return "deleterecipe";
    }

    /**
     * On a GET request to /editrecipe, the EditRecipeController will return
     * /src/main/resources/templates/editrecipe.html.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( { "/editrecipe", "/editrecipe.html" } )
    public String editRecipeForm ( final Model model ) {
        return "editrecipe";
    }

    /**
     * Handles a GET request for inventory. The GET request provides a view to
     * the client that includes the list of the current ingredients in the
     * inventory and a form where the client can enter more ingredients to add
     * to the inventory.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( { "/addinventory", "/addinventory.html" } )
    public String inventoryForm ( final Model model ) {
        return "addinventory";
    }

    /**
     * On a GET request to /makecoffee, the MakeCoffeeController will return
     * /src/main/resources/templates/makecoffee.html.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( { "/makecoffee", "/makecoffee.html" } )
    public String makeCoffeeForm ( final Model model ) {
        return "makecoffee";
    }

    /**
     * On a GET request to /fulfillorder, the FulfillOrderController will return
     * /src/main/resources/templates/fulfillorder.html.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( { "/fulfillorder", "/fulfillorder.html" } )
    public String fulfillOrderForm ( final Model model ) {
        return "fulfillorder";
    }

    /**
     * On a GET request to /addrecipe, the AddRecipeController will return
     * /src/main/resources/templates/addrecipe.html.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( { "/addrecipe", "/addrecipe.html" } )
    public String addRecipeForm ( final Model model ) {
        return "addrecipe";
    }

    /**
     * On a GET request to /addingredients, the AddIngredientController will
     * return /src/main/resources/templates/addingredients.html.
     *
     * @param model
     *            underlying UI model
     * @return contents of the page
     */
    @GetMapping ( { "/addingredients", "/addingredients.html" } )
    public String addIngredientsForm ( final Model model ) {
        return "addingredients";
    }

    /**
     * On a GET request to /customerHome, the CustomerHomeController will return
     * /src/man/resources/templates/customerhome.html.
     *
     * @param model
     *            underlying UI Model
     * @return contents of page
     */
    @GetMapping ( { "/customerhome", "/customerhome.html" } )
    public String customerHomeForm ( final Model model ) {
        return "customerhome";
    }

    /**
     * On a GET request to /staffHome, the StaffHomeController will return
     * /src/man/resources/templates/staffhome.html.
     *
     * @param model
     *            underlying UI Model
     * @return contents of page
     */
    @GetMapping ( { "/staffhome", "/staffhome.html" } )
    public String staffHomeForm ( final Model model ) {
        return "staffhome";
    }

    /**
     * On a GET request to /orderHistory, the OrderHistoryController will return
     * /src/man/resources/templates/orderhistory.html.
     *
     * @param model
     *            underlying UI Model
     * @return contents of page
     */
    @GetMapping ( { "/orderhistory", "orderhistory.html" } )
    public String orderHistoryForm ( final Model model ) {
        return "orderhistory";
    }

    /**
     * On a GET request to /orderHistory, the OrderHistoryController will return
     * /src/man/resources/templates/orderhistory.html.
     *
     * @param model
     *            underlying UI Model
     * @return contents of page
     */
    @GetMapping ( { "/orderhistorystaff", "orderhistorystaff.html" } )
    public String orderHistoryStaffForm ( final Model model ) {
        return "orderhistorystaff";
    }

    /**
     * On a GET request to /customerorder, the CustomerOrderController will
     * return /src/man/resources/templates/customerorder.html.
     *
     * @param model
     *            underlying UI Model
     * @return contents of page
     */
    @GetMapping ( { "/customerorder", "/customerorder.html" } )
    public String customerOrderForm ( final Model model ) {
        return "customerorder";
    }

    /**
     * On a GET request to /customerorder, the CustomerOrderController will
     * return /src/man/resources/templates/customerorder.html.
     *
     * @param model
     *            underlying UI Model
     * @return contents of page
     */
    @GetMapping ( { "/pickuporder", "/pickuporder.html" } )
    public String pickUpOrderForm ( final Model model ) {
        return "pickuporder";
    }

    /**
     * On a GET request to /payment, the PaymentController will return
     * /src/man/resources/templates/payment.html.
     *
     * @param model
     *            underlying UI Model
     * @return contents of page
     */
    @GetMapping ( { "/payment", "/payment.html" } )
    public String paymentForm ( final Model model ) {
        return "payment";
    }

    /**
     * On a GET request to /privacy, the PrivacyController will return
     * /src/man/resources/templates/privacy.html.
     *
     * @param model
     *            underlying UI Model
     * @return contents of page
     */
    @GetMapping ( { "/privacy", "/privacy.html" } )
    public String privacyForm ( final Model model ) {
        return "privacy";
    }

}
