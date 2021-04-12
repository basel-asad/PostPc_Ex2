package android.exercise.mini.calculator.app;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

  @VisibleForTesting
  public SimpleCalculator calculator;
  private TextView screen;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if (calculator == null) {
      calculator = new SimpleCalculatorImpl();
    }

    screen = findViewById(R.id.textViewCalculatorOutput);
    define_buttons(screen);


    /*
    TODO:
    - find all views
    - initial update main text-view based on calculator's output
    - set click listeners on all buttons to operate on the calculator and refresh main text-view
     */
  }

  private void define_buttons(TextView screen){
    screen.setText("0");

    // deal with number buttons
    findViewById(R.id.button0).setOnClickListener(v -> {
      calculator.insertDigit(0);
      screen.setText(calculator.output());
    });

    findViewById(R.id.button1).setOnClickListener(v -> {
      calculator.insertDigit(1);
      screen.setText(calculator.output());
    });

    findViewById(R.id.button2).setOnClickListener(v -> {
      calculator.insertDigit(2);
      screen.setText(calculator.output());
    });

    findViewById(R.id.button3).setOnClickListener(v -> {
      calculator.insertDigit(3);
      screen.setText(calculator.output());
    });

    findViewById(R.id.button4).setOnClickListener(v -> {
      calculator.insertDigit(4);
      screen.setText(calculator.output());
    });
    findViewById(R.id.button5).setOnClickListener(v -> {
      calculator.insertDigit(5);
      screen.setText(calculator.output());
    });
    findViewById(R.id.button6).setOnClickListener(v -> {
      calculator.insertDigit(6);
      screen.setText(calculator.output());
    });
    findViewById(R.id.button7).setOnClickListener(v -> {
      calculator.insertDigit(7);
      screen.setText(calculator.output());
    });
    findViewById(R.id.button8).setOnClickListener(v -> {
      calculator.insertDigit(8);
      screen.setText(calculator.output());
    });
    findViewById(R.id.button9).setOnClickListener(v -> {
      calculator.insertDigit(9);
      screen.setText(calculator.output());
    });

    // deal with other buttons
    findViewById(R.id.buttonPlus).setOnClickListener(v -> {
      calculator.insertPlus();
      screen.setText(calculator.output());
    });
    findViewById(R.id.buttonMinus).setOnClickListener(v -> {
      calculator.insertMinus();
      screen.setText(calculator.output());
    });
    findViewById(R.id.buttonEquals).setOnClickListener(v -> {
      calculator.insertEquals();
      screen.setText(calculator.output());
    });
    findViewById(R.id.buttonClear).setOnClickListener(v -> {
      calculator.clear();
      screen.setText(calculator.output());
    });
    findViewById(R.id.buttonBackSpace).setOnClickListener(v -> {
      calculator.deleteLast();
      screen.setText(calculator.output());
    });

//    findViewById(R.id.textViewCalculatorOutput).setOnClickListener(v -> {
//
//
//    });

  }

  @Override
  protected void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    // save calculator state into the bundle
    outState.putSerializable("history", calculator.saveState());
  }

  @Override
  protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    // restore calculator state from the bundle, refresh main text-view from calculator's output
    calculator.loadState(savedInstanceState.getSerializable("history"));
    screen.setText(calculator.output());

  }
}