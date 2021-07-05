# "Vending Machine Software"

## The Challenge
The application will receive a JSON file at any given time and will update the functionality of the machine. When a consumer enters a selection, it will report the selected item.

## The Solution

On application launch, the machine prompts the user to make a selection, update the machine, or to exit the process. If the user chooses to purchase an item before updating the items list, the machine will display that it is empty and request another selection.

###Step 1: Update the machine

The machine will prompt for an **absolute** file path location to the JSON file containing the rows/columns/items (matching the JSON format provided). It will then load the JSON contents and display the number of items added to the machine.

###Step 2: Making a purchase

The machine will display all slots with rows having alphabetical letters, and columns having numbers. If an item has been assigned the slot, the machine will display the slot, the item name, and the price of the item. Display will look similar to:

```
A1: Snickers $1.35
A2: Hersheys $2.25
A3: Hersheys Almond $1.80
```

The machine will also prompt the consumer for a slot selection. After an item has been selected, the machine will verify the consumer wants the specified item.

**Note:** Even though it is not displayed, the machine also handles item quantity. Should a user choose to purchase an item with a quantity of zero, the machine will display that the item is out of stock and prompt the user to select another item.

###Step 3: Inserting coins

The machine will prompt the consumer to insert dollar bills, quarter, dimes, or nickels to make the purchase. It will provide a running total for each selection. If the consumer puts a larger value into the machine than the purchase price, the machine will refund the overage. The consumer also has the option to refund their purchase at any time during the insertion process. Once the purchase is complete, the user is redirected back to the main screen to purchase again, update, or exit.
