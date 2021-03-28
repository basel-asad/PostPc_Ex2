package android.exercise.mini.calculator.app;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

  @VisibleForTesting
  public SimpleCalculator calculator;
  Serializable saved_state = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if (calculator == null) {
      calculator = new SimpleCalculatorImpl();
    }

    /*
    TODO:
    - find all views
    - initial update main text-view based on calculator's output
    - set click listeners on all buttons to operate on the calculator and refresh main text-view
     */
  }

  @Override
  protected void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    // todo: save calculator state into the bundle
    saved_state = calculator.saveState(); //todo: should this be added here?
  }

  @Override
  protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    // todo: restore calculator state from the bundle, refresh main text-view from calculator's output
  }
}