# CoffeeMaker - Individual
This portion of the CoffeeMaker project is also team based, but this focuses on the specific individual deliverables for the onboarding project that took place during the first half of the semster. I display experience in both, design, backend and front end testing nd development. Specific files or classes I have worked with are listed below.

**Images Folder Within CoffeeMaker - Individual:**
-Copy of W3 Wirefram U4.drawio.png - Experince in developing a UX Design through a wireframe for the workflows of the use cases for the CoffeeMaker project

-IndividualDynamicDesignFinal.png - Experience in developing dynamic design through Sequence Diagrams for a specific workflow for the CoffeeMaker project

-IndividualStaticDesignFinal.png - Experience in developing static design through UML Diagrams for a specific use cases and changes in requirements for the CoffeeMaker project

-Examples of writing API End Point Designs for a specific use case in the CoffeeMaker Project (UC4: Edit Recipe):

ENDPOINT #1:

Name: editRecipe(String recipeName, String ingredientName, int inventory)

Route: DELETE/recipes/{recipeName}/deleteIngredient/{ingredientName}

Description: This endpoint provides access to a specific Coffeemaker Recipe and a specific ingredient within the list of ingredients for the recipe so that the specific ingredient can be deleted from the recipe.

Data Details: The user provides the program with the name of the recipe to get and the name of the ingredient that is to be deleted so that it can be deleted from the specific Recipe. This method has no return statement. If we have an recipe named Latte with ingredients named 'sugar', 'chocolate', 'vanilla', we would call the endpoint as DELETE/recipes/{Latte}/deleteIngredient/{vanilla} and expect an 200/OK response from the API. The JSON data would look like this:
```
  {
  'name': Latte
  'id': 6,
  'sugar': 1,
  'chocolate': 8
  }
```
If we instead call the endpoint as DELETE/recipes/{Latte}/deleteIngredient/{error}, we would expect an 404/Not Found and no ingredient JSON data.

ENDPOINT #2:
Name: editRecipe(String recipeName, String ingredientName, int inventory)

Route: PUT/recipes/{name}/editIngredient/{name}/{inventory}

Description: This endpoint provides access to a specific Coffeemaker Recipe and a specific ingredient and the ingredient's inventory within the list of ingredients for the recipe so that the specific ingredient's inventory amount can be changed for the recipe.

Data Details: The user provides the program with the name of the recipe to get and the name of the ingredient and the amount that is to be changed from the specific Recipe. This method has no return statement. If we have an recipe named Latte with ingredients named 'sugar', 'chocolate', 'vanilla', and a inventory amount of 6 to the initial inventory of 8 we want to change for vanilla we would call the endpoint as PUT/recipes/{Latte}/deleteIngredient/{vanilla}/{6} and expect an 200/OK response from the API. The JSON data would look like this:
```
  {
  'name': Latte
  'id': 6,
  'sugar': 1,
  'chocolate': 8
  'vanilla': 14
  }
```
If we instead call the endpoint as PUT/recipes/{Latte}/deleteIngredient/{vanilla}/{@}, we would expect an 404/Not Found and no ingredient JSON data.

ENDPOINT #3:
Name: editRecipe(String recipeName, String ingredientName, int inventory)

Route: POST/recipes/{recipeName}/addIngredient/{ingredientName}/{4}

Description: This endpoint provides access to a specific Coffeemaker Recipe and a specific ingredient within the list of ingredients in the ingredients list for the recipe so that the specific ingredient can be added to the recipe.

Data Details: The user provides the program with the name of the recipe to get and the name of the ingredient that is to be added so that it can be found in the ingredients list and to the specific Recipe. This method returns the Recipe with the recipe name. If we have a recipe named Latte with ingredients named 'sugar', 'chocolate', 'vanilla', and we want to add the ingredient 'milk' we would call the endpoint as POST/recipes/{Latte}/addIngredient/{milk}/{4} and expect an 200/OK response from the API. The JSON data would look like this:
```
  {
  'name': Latte
  'id': 6,
  'sugar': 1,
  'chocolate': 8
  'milk: 4
  }
```
If we instead call the endpoint as POST/recipes/{Latte}/addIngredient/{milk}/{error}, we would expect an 404/Not Found and no ingredient JSON data.

FRONT-END Implementation:
inventory.html -- Developed the front end portion for the inventory page (that controls the use case to add and remove ingredients from the inventory for the CoffeeMaker)
editRecipe.html -- Developed the front end portion for the editRecipe page (that controls the use case to edit a Recipe, by adding or deleting ingredients, updating name and/or price)

BACK-END Implementation:
API Implementation:
  APIInventoryController.java -- Developed the REST API Calls used to fulfill the update inventory use case, such as add ingredients, delete ingredients and update ingredients in inventory

Persistence Development:
  Ingredient.java -- Persistence class development, basic functionality of an ingredient object class
  Inventory.java -- Persistence class development functinality of back end to check ingredients in inventory, edit ingredients, add ingredients, and remove ingredients. 


# CoffeeMakerFinalProject
This is the large team final project, that occurred during the second half of the semester. This is a fully functional of CoffeeMaker Website as per requirements provided. Specific files or classes I have worked with are listed below.

For the larger team project, our team decides to split of into sub-teams since we had all gotten experience in design, front end and back end impelmetnation and testing during the CoffeeMaker Onboarding Project. I was on the persistence team.

OrderTicket.java -- Persistence Class written that focuses on the ticket object, handeling all details a order ticket should hold, along with methods that assist in the pick up order and order fulfilled functionality.

orderhistorystaff.html -- Front End HTML implementation for the order history for a staff login, functionalities for total money earned on a particualr day, a list of all orders. 

Other assistence was mainly focused in on design aspects, such as creating the dynamic designs and wireframes to support the front end team. Adding any necessary changes to the model classes depending on design changes between the back end (api calls and front end users). Facilitated these changes and communicated between the teams to support. 

Design: 
images/StaffFulfillsOrder.png -- Developed the wireframe for the fulfill order workflow for the order use case. 

Developer's Guide - Developed this guide to support a developer who may download or try to create this project.

# Developer's Guide
**Import CoffeeMaker into Eclipse Workspace**
- To clone the repository through eclipse workspace
  1. Select Clone Git Repository
  2. Select Clone URL
  3. The GitHub clone url should autofill into the fields

- To import the your project within the Eclipse Workspace:
  1. Right- click in the Package Explorer and select **Import...>Maven>Existing Maven Projects**
  2. Browse and select your local repository location
  3. Click Finish

Provide your workspace time to build, close eclipse and restart. If restart does not assist, delete the CoffeeMaker project, along with cloned github repository and restart steps with a fresh Maven Setup.

# CoffeeMaker Properties

CoffeeMaker contains a properties file that must be set up for proper interaction to occur between the database and our code base. 

To ensure this, copy the ```CoffeeMaker/src/main/resources/application.yml.template``` into ```CoffeeMaker/src/main/resources/application.yml.```

Add in your SQL credentials (password) in the 'password:' field. Make sure the file is named exactly ```application.yml``` or else the gitignore setup will not work.

# Run JUnit Tests

- To run all the unit tests associated with the project, right click on the CoffeeMaker project, select **RunAs>JUnit**.

- To run individual or package specific unit tests, select the package, right click on the test package select RunAs>JUnit.
    - Example: To run persistence class unit tests, select edu.ncsu.csc.CoffeeMaker.unit and select **RunAs>JUnit**

- To run individual test classes, select the specific test class right click and select RunAs>JUnit.
    - Example: To run IngredientTest, select IngredientTest, right click and select **RunAs>JUnit**

# Run Checkstyle 

CoffeeMaker has been set up with the CSC Checkstyle configuration in ```src/test/resources/reporting/csc_checkstyle.xml```. You can update your local Checkstyle to use the updated configuration file in your CoffeeMaker project.

# Creating a new User

With this version of CoffeeMaker, the different types of users are simply placed within an enum and given upon construction.  The individual functions within ```User.java``` do all the checks for if each user is able to perform specific actions.  API to retrieve User objects merely return their types while the rest of the User information is kept within the application.  With this, the frontend pages can designate the desired flow for what this user can access (Adding to ```MappingController.java``` and the respective controllers for actions on specific objects)
