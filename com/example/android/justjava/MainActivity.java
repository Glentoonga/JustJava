package com.example.android.justjava;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    final Context context = this;

    int numberOfCoffees = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Switch rushOrder = (Switch) findViewById(R.id.rushOrder);
        rushOrder.setChecked(false);
        rushOrder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    Toast.makeText(getApplicationContext(), "Your order will be made immediately", Toast.LENGTH_SHORT).show();
                } else {

                }
            }

            EditText specialRequests = (EditText) findViewById(R.id.notes_field);

        });


    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        // This changes the text of the Order Button to "update order"
        TextView confirmOrder = (TextView) findViewById(R.id.orderButton);
        confirmOrder.setText("Update Order");

        // this customers name
        EditText nameField = (EditText) findViewById(R.id.name_field);
        final String customerName = nameField.getText().toString();

        // this method determines if the CheckBox for whipped cream has been checked or not
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        //this method determines if the CheckBox for mini marshmallows has been checked or not
        CheckBox miniMarshmallowsCheckBox = (CheckBox) findViewById(R.id.mini_marshmellows_checkbox);
        boolean hasMiniMarshmallows = miniMarshmallowsCheckBox.isChecked();

        //this method determines if the CheckBox for chocolate sprinkles has been checked or not
        CheckBox chocolateSprinklesCheckBox = (CheckBox) findViewById(R.id.chocolate_sprinkles_checkbox);
        boolean hasChocolateSprinkles = chocolateSprinklesCheckBox.isChecked();

        //this method determines if the CheckBox for chocolate curls has been checked or not
        CheckBox chocolateCurlsCheckBox = (CheckBox) findViewById(R.id.chocolate_curls_checkbox);
        boolean hasChocolateCurls = chocolateCurlsCheckBox.isChecked();

        CheckBox smallCoffeeCheckbox = (CheckBox) findViewById(R.id.small_coffee_checkbox);
        boolean hasSmallCoffee = smallCoffeeCheckbox.isChecked();

        CheckBox regularCoffeeCheckbox = (CheckBox) findViewById(R.id.regular_coffee_checkbox);
        boolean hasRegularCoffee = regularCoffeeCheckbox.isChecked();

        CheckBox largeCoffeeCheckbox = (CheckBox) findViewById(R.id.large_coffee_checkbox);
        boolean hasLargeCoffee = largeCoffeeCheckbox.isChecked();

        //calculates the price for us and displays the order summary on screen
        int price = calculatePrice(hasMiniMarshmallows, hasWhippedCream, hasChocolateSprinkles, hasChocolateCurls);

        final String orderSummaryMessage = constructPriceMessage(customerName, hasLargeCoffee, hasSmallCoffee, hasRegularCoffee, hasWhippedCream, hasChocolateCurls, hasChocolateSprinkles, hasMiniMarshmallows, price);
        displayMessage(orderSummaryMessage);

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);

        // set title
        builder.setTitle("HAPPY WITH YOUR ORDER?");
        // Add the buttons
        builder.setMessage(orderSummaryMessage)
                .setPositiveButton(R.string.Confirmation, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee Order for +" + customerName);
                        intent.putExtra(Intent.EXTRA_TEXT, orderSummaryMessage);
                        intent.putExtra(Intent.EXTRA_EMAIL, "Glentoonga@hotmail.com");
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }
                    }


                })
                .setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });
        // Set other dialog properties
        //...
        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        // show it
        builder.show();

    }

    /**
     * this method updates the price so that it includes the cost of having whipped cream
     */

    public void whipped(View myButton) {

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox miniMarshmallowsCheckBox = (CheckBox) findViewById(R.id.mini_marshmellows_checkbox);
        boolean hasMiniMarshmallows = miniMarshmallowsCheckBox.isChecked();

        CheckBox chocolateSprinklesCheckBox = (CheckBox) findViewById(R.id.chocolate_sprinkles_checkbox);
        boolean hasChocolateSprinkles = chocolateSprinklesCheckBox.isChecked();

        CheckBox chocolateCurlsCheckBox = (CheckBox) findViewById(R.id.chocolate_curls_checkbox);
        boolean hasChocolateCurls = chocolateCurlsCheckBox.isChecked();


        int basePrice = 5;

        //marshmallows cost £1, whipped cream cost £2 and chocolate sprinkles cost £1 and chocolate curls are £1


        if (hasChocolateCurls) {
            basePrice = basePrice + 1;
        }
        if (hasMiniMarshmallows) {
            basePrice = basePrice + 1;
        }
        if (hasWhippedCream) {
            Toast.makeText(this, "You've just added whipped cream", Toast.LENGTH_SHORT).show();
            basePrice = basePrice + 2;
        }
        if (hasChocolateSprinkles) {
            basePrice = basePrice + 1;
        }

        display1(numberOfCoffees * basePrice);

    }

    /**
     * this method updates the price so that it includes the cost of having whipped cream
     */

    public void mellows(View myButton) {

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox miniMarshmallowsCheckBox = (CheckBox) findViewById(R.id.mini_marshmellows_checkbox);
        boolean hasMiniMarshmallows = miniMarshmallowsCheckBox.isChecked();

        CheckBox chocolateSprinklesCheckBox = (CheckBox) findViewById(R.id.chocolate_sprinkles_checkbox);
        boolean hasChocolateSprinkles = chocolateSprinklesCheckBox.isChecked();

        CheckBox chocolateCurlsCheckBox = (CheckBox) findViewById(R.id.chocolate_curls_checkbox);
        boolean hasChocolateCurls = chocolateCurlsCheckBox.isChecked();

        int basePrice = 5;

        //marshmallows cost £1, whipped cream cost £2 and chocolate sprinkles cost £1and chocolate curls are £1


        if (hasChocolateCurls) {
            basePrice = basePrice + 1;
        }

        if (hasMiniMarshmallows) {
            Toast.makeText(this, "You've just added Mini Marshmallows", Toast.LENGTH_SHORT).show();
            basePrice = basePrice + 1;
        }
        if (hasWhippedCream) {
            basePrice = basePrice + 2;
        }
        if (hasChocolateSprinkles) {
            basePrice = basePrice + 1;
        }

        display1(numberOfCoffees * basePrice);

    }

    public void chocoSprinkles(View myButton) {

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox miniMarshmallowsCheckBox = (CheckBox) findViewById(R.id.mini_marshmellows_checkbox);
        boolean hasMiniMarshmallows = miniMarshmallowsCheckBox.isChecked();

        CheckBox chocolateSprinklesCheckBox = (CheckBox) findViewById(R.id.chocolate_sprinkles_checkbox);
        boolean hasChocolateSprinkles = chocolateSprinklesCheckBox.isChecked();

        CheckBox chocolateCurlsCheckBox = (CheckBox) findViewById(R.id.chocolate_curls_checkbox);
        boolean hasChocolateCurls = chocolateCurlsCheckBox.isChecked();


        int basePrice = 5;

        //marshmallows cost £1, whipped cream cost £2 and chocolate sprinkles cost £1 and chocolate curls are £1


        if (hasChocolateCurls) {
            basePrice = basePrice + 1;
        }

        if (hasMiniMarshmallows) {
            basePrice = basePrice + 1;
        }
        if (hasWhippedCream) {
            basePrice = basePrice + 2;
        }
        if (hasChocolateSprinkles) {
            Toast.makeText(this, "You've just added Chocolate Sprinkles", Toast.LENGTH_SHORT).show();
            basePrice = basePrice + 1;
        }
        display1(numberOfCoffees * basePrice);

    }

    public void chocoCurls(View myButton) {

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox miniMarshmallowsCheckBox = (CheckBox) findViewById(R.id.mini_marshmellows_checkbox);
        boolean hasMiniMarshmallows = miniMarshmallowsCheckBox.isChecked();

        CheckBox chocolateSprinklesCheckBox = (CheckBox) findViewById(R.id.chocolate_sprinkles_checkbox);
        boolean hasChocolateSprinkles = chocolateSprinklesCheckBox.isChecked();

        CheckBox chocolateCurlsCheckBox = (CheckBox) findViewById(R.id.chocolate_curls_checkbox);
        boolean hasChocolateCurls = chocolateCurlsCheckBox.isChecked();

        int basePrice = 5;

        //marshmallows cost £1, whipped cream cost £2 and chocolate sprinkles cost £1 and chocolate curls are £1


        if (hasChocolateCurls) {
            Toast.makeText(this, "You've just added Chocolate Curls", Toast.LENGTH_SHORT).show();
            basePrice = basePrice + 1;
        }

        if (hasMiniMarshmallows) {
            basePrice = basePrice + 1;
        }
        if (hasWhippedCream) {
            basePrice = basePrice + 2;
        }
        if (hasChocolateSprinkles) {
            basePrice = basePrice + 1;
        }
        display1(numberOfCoffees * basePrice);

    }

    // This method is called when the plus button is clicked. It updates the number of coffess and the cost per cup
    public void increment(View myButton) {

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox miniMarshmallowsCheckBox = (CheckBox) findViewById(R.id.mini_marshmellows_checkbox);
        boolean hasMiniMarshmallows = miniMarshmallowsCheckBox.isChecked();

        CheckBox chocolateSprinklesCheckBox = (CheckBox) findViewById(R.id.chocolate_sprinkles_checkbox);
        boolean hasChocolateSprinkles = chocolateSprinklesCheckBox.isChecked();

        CheckBox chocolateCurlsCheckBox = (CheckBox) findViewById(R.id.chocolate_curls_checkbox);
        boolean hasChocolateCurls = chocolateCurlsCheckBox.isChecked();


        int basePrice = 5;

        //marshmallows cost £1, whipped cream cost £2 and chocolate sprinkles cost £1 and chocolate curls are £1


        if (hasChocolateCurls) {
            basePrice = basePrice + 1;
        }
        if (hasMiniMarshmallows) {
            basePrice = basePrice + 1;
        }
        if (hasWhippedCream) {
            basePrice = basePrice + 2;
        }
        if (hasChocolateSprinkles) {
            basePrice = basePrice + 1;
        }

        if (numberOfCoffees == 15) {
            // shows an error that user can not order more than 15 cups of coffee
            Toast.makeText(this, "You cannot order more than 15 cups of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        numberOfCoffees = numberOfCoffees + 1;
        display(numberOfCoffees);
        display1(numberOfCoffees * basePrice);

    }

    // This method is called when the minus button is clicked.It updates the number of coffess and the cost per cup
    public void decrement(View myButton) {

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox miniMarshmallowsCheckBox = (CheckBox) findViewById(R.id.mini_marshmellows_checkbox);
        boolean hasMiniMarshmallows = miniMarshmallowsCheckBox.isChecked();

        CheckBox chocolateSprinklesCheckBox = (CheckBox) findViewById(R.id.chocolate_sprinkles_checkbox);
        boolean hasChocolateSprinkles = chocolateSprinklesCheckBox.isChecked();

        CheckBox chocolateCurlsCheckBox = (CheckBox) findViewById(R.id.chocolate_curls_checkbox);
        boolean hasChocolateCurls = chocolateCurlsCheckBox.isChecked();

        int basePrice = 5;

        //marshmallows cost £1, whipped cream cost £2 and chocolate sprinkles cost £1 and chocolate curls are £1


        if (hasChocolateCurls) {
            basePrice = basePrice + 1;
        }

        if (hasMiniMarshmallows) {
            basePrice = basePrice + 1;
        }
        if (hasWhippedCream) {
            basePrice = basePrice + 2;
        }
        if (hasChocolateSprinkles) {
            basePrice = basePrice + 1;
        }

        if (numberOfCoffees == 1) {
            // shows an error that user can not order less than 1 cup of coffee
            Toast.makeText(this, "You cannot order less than 1 cup of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        numberOfCoffees = numberOfCoffees - 1;
        display(numberOfCoffees);
        display1(numberOfCoffees * basePrice);

    }


    public void onCheckboxClicked(View view) {

        CheckBox smallCoffeeCheckbox = (CheckBox) findViewById(R.id.small_coffee_checkbox);
        boolean hasSmallCoffee = smallCoffeeCheckbox.isChecked();

        CheckBox regularCoffeeCheckbox = (CheckBox) findViewById(R.id.regular_coffee_checkbox);
        boolean hasRegularCoffee = regularCoffeeCheckbox.isChecked();

        CheckBox largeCoffeeCheckbox = (CheckBox) findViewById(R.id.large_coffee_checkbox);
        boolean hasLargeCoffee = largeCoffeeCheckbox.isChecked();


        switch (view.getId()) {

            case R.id.small_coffee_checkbox:

                regularCoffeeCheckbox.setChecked(false);
                largeCoffeeCheckbox.setChecked(false);

                Toast.makeText(this, "Feeling brave, why not go Regular?", Toast.LENGTH_SHORT).show();

                break;

            case R.id.regular_coffee_checkbox:

                largeCoffeeCheckbox.setChecked(false);
                smallCoffeeCheckbox.setChecked(false);
                Toast.makeText(this, "Upgrade to Large? It's only an extra £1", Toast.LENGTH_SHORT).show();

                break;

            case R.id.large_coffee_checkbox:

                smallCoffeeCheckbox.setChecked(false);
                regularCoffeeCheckbox.setChecked(false);
                Toast.makeText(this, "Great choice! Would you like another?", Toast.LENGTH_SHORT).show();

                break;
        }
    }


    // Calculates the price of the order. return total price of order
    private int calculatePrice(boolean hasMiniMarshmallows, boolean hasWhippedCream, boolean hasChocolateSprinkles, boolean hasChocolateCurls) {

        int basePrice = 5;


        if (hasMiniMarshmallows) {
            basePrice = basePrice + 1;
        }
        if (hasWhippedCream) {
            basePrice = basePrice + 2;
        }
        if (hasChocolateSprinkles) {
            basePrice = basePrice + 1;
        }
        if (hasChocolateCurls) {
            basePrice = basePrice + 1;
        }

        display1(numberOfCoffees * basePrice);
        return numberOfCoffees * basePrice;

    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display1(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    public String constructPriceMessage(String customerName, boolean hasLargeCoffee, boolean hasSmallCoffee, boolean hasRegularCoffee, boolean hasWhippedCream, boolean hasChocolateCurls, boolean hasChocolateSprinkles, boolean hasMiniMarshmallows, int price) {

        String priceMessage = "ORDER SUMMARY:\n\nHey " + customerName + "," + " You've gone and ordered " + numberOfCoffees;
        if (hasLargeCoffee) {
            priceMessage += " Large";
        }
        if (hasSmallCoffee) {
            priceMessage += " Small";
        }
        if (hasRegularCoffee) {
            priceMessage += " Regular";
        }
        priceMessage += " cup";

        if (numberOfCoffees > 1) {
            priceMessage += "s";
        }
        priceMessage += " of Coffee ";
        if (hasWhippedCream) {
            priceMessage += " with Whipped Cream";
        }
        if (hasChocolateCurls) {
            priceMessage += " with Chocolate Curls";
        }
        if (hasChocolateSprinkles) {
            priceMessage += " with Chocolate Sprinkles";
        }
        if (hasMiniMarshmallows) {
            priceMessage += " with Mini Marshmallows";
        }
        priceMessage += "\n\nCosting you a total of £" + price;

        return priceMessage;
    }


}