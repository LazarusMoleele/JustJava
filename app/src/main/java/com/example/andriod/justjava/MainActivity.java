package com.example.andriod.justjava;

import android.content.Intent;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.name;


public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view) {
        if (quantity == 100){
            //Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            //Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    public void decrement(View view) {
        if (quantity == 1){
        //Show an error message as a toast
        Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
        //Exit this method early because there's nothing left to do

            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //Find the user's name
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        //Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        //Figure out if the user wants whipped cream topping
        CheckBox ChocolateBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = ChocolateBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculate the price of the order
     *@param  addWhippedCream is whether or not the user wants whipped cream topping
     *@param  addChocolate is whether or not the user wants chocolate topping
     * @return total price
     */
    private int calculatePrice( boolean addWhippedCream, boolean addChocolate) {
        //Price id 1 cup of coffee
        int basePrice = 5 ;
        //Add R1 if the user wants whipped cream
        if (addWhippedCream){
            basePrice = basePrice + 1;
        }
        //Add R2 if the user wants chocolate
        if (addChocolate){
            basePrice = basePrice + 2;
        }
        //Calculate the total order price by multiplying by quantity
        return quantity * basePrice;
    }

    /**
     * Create summary of the order.
     *
     *
     * @param name
     * @param price of the order
     * @return text summary
     * @ addWhippedCream is whether or not the user wants whipped cream topping
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = "Name: " + name;
        priceMessage = priceMessage + "\nAdd Whipped Cream? " + addWhippedCream;
        priceMessage = priceMessage + "\nAdd Chocolate? " + addChocolate;
        priceMessage = priceMessage + "\nQuantity: " + quantity;
        priceMessage = priceMessage + "\nTotal: R" + price;
        priceMessage = priceMessage + "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    private void display(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

}