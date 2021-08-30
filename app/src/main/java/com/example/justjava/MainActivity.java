package com.example.justjava;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    int quantity = 0,price=0;
    boolean hasWhippedCream=false;
    boolean hasChocolate=false;
    public void submitOrder(View view)
    {
        CheckBox whippedCreamCheckBox=(CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        hasWhippedCream=whippedCreamCheckBox.isChecked();
        hasChocolate = chocolateCheckBox.isChecked();
        calculatePrice(quantity,5);
        String priceMessage = createOrderSummary();
       // displayMessage(priceMessage);
        //displayPrice(quantity*5);
    }

    public void increment(View view) {
        if(quantity==100)
        {
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity+=1;
        displayQuantity(quantity);
    }

    public void decrement(View view)
    {
       if(quantity==0)
       {
           Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
           return;
       }
       quantity-=1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private void displayPrice(int number) {
       // TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        //priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    private void displayMessage(String message) {
       // TextView OrderTextView = (TextView) findViewById(R.id.order_summary_text_view);

        //OrderTextView.setText(message);
    }
    private int calculatePrice(int quantity,int pricePerCup) {
        price=quantity*pricePerCup;
        if(hasChocolate)
            price+=2*quantity;
        if(hasWhippedCream)
            price+=quantity;
        return price;
    }
    private String createOrderSummary()
    {
        EditText editText=(EditText) findViewById(R.id.plain_text_input);
        String Name = editText.getText().toString();
        String priceMessage="Name:"+Name+"\n";
        priceMessage=priceMessage+"Add whipped cream "+hasWhippedCream;
        priceMessage += "\nAdd chocolate " + hasChocolate;
        priceMessage=priceMessage+"\nQuantity:"+quantity;
        priceMessage=priceMessage+"\n Price:"+price+" $";
        priceMessage=priceMessage+"\n"+getString(R.string.thank_you);
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_SUBJECT, Name+" Sir Your Coffee Bill");
        email.putExtra(Intent.EXTRA_TEXT, priceMessage);

        //need this to prompts email client only
        email.setType("message/rfc822");

        startActivity(Intent.createChooser(email, "Choose an Email client :"));
        return priceMessage;
    }

}