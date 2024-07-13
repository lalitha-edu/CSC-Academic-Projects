# CoffeeMakerFinalProject
This is the large team final project, duration at half a semester long with full functionality of CoffeeMaker Website


# CoffeeMaker - Individual
This portion of the CoffeeMaker project focuses on the individual deliverables for the onboarding project that took place during the first half of the semster. I display experience in both, design, backend and front end testing nd development.

Images Folder Within CoffeeMaker - Individual:
Copy of W3 Wirefram U4.drawio.png - Experince in developing a UX Design through a wireframe for the workflows of the use cases for the CoffeeMaker project

IndividualDynamicDesignFinal.png - Experience in developing dynamic design through Sequence Diagrams for a specific workflow for the CoffeeMaker project

IndividualStaticDesignFinal.png - Experience in developing static design through UML Diagrams for a specific use cases and changes in requirements for the CoffeeMaker project

Examples of writing API End Point Designs for a specific use case in the CoffeeMaker Project (UC4: Edit Recipe):

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
