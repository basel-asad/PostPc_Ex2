package test.android.exercise.mini.calculator.app;

import android.exercise.mini.calculator.app.MainActivity;
import android.exercise.mini.calculator.app.R;
import android.view.View;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {28})
public class AppFlowTest {

  private ActivityController<MainActivity> activityController;
  private MainActivity activityUnderTest;
  private View button0;
  private View button1;
  private View button2;
  private View button3;
  private View button4;
  private View button5;
  private View button6;
  private View button7;
  private View button8;
  private View button9;
  private View buttonBackspace;
  private View buttonClear;
  private View buttonPlus;
  private View buttonMinus;
  private View buttonEquals;
  private TextView textViewOutput;

  /** initialize main activity with a real calculator */
  @Before
  public void setup(){
    activityController = Robolectric.buildActivity(MainActivity.class);
    activityUnderTest = activityController.get();
    activityController.create().start().resume();
    button0 = activityUnderTest.findViewById(R.id.button0);
    button1 = activityUnderTest.findViewById(R.id.button1);
    button2 = activityUnderTest.findViewById(R.id.button2);
    button3 = activityUnderTest.findViewById(R.id.button3);
    button4 = activityUnderTest.findViewById(R.id.button4);
    button5 = activityUnderTest.findViewById(R.id.button5);
    button6 = activityUnderTest.findViewById(R.id.button6);
    button7 = activityUnderTest.findViewById(R.id.button7);
    button8 = activityUnderTest.findViewById(R.id.button8);
    button9 = activityUnderTest.findViewById(R.id.button9);
    buttonBackspace = activityUnderTest.findViewById(R.id.buttonBackSpace);
    buttonClear = activityUnderTest.findViewById(R.id.buttonClear);
    buttonPlus = activityUnderTest.findViewById(R.id.buttonPlus);
    buttonMinus = activityUnderTest.findViewById(R.id.buttonMinus);
    buttonEquals = activityUnderTest.findViewById(R.id.buttonEquals);
    textViewOutput = activityUnderTest.findViewById(R.id.textViewCalculatorOutput);
  }

  @Test
  public void flowTest1(){
    // run clicks on "13+5"
    for (View button: Arrays.asList(
      button1, button3, buttonPlus, button5
    )) {
      button.performClick();
    }

    assertEquals("13+5", textViewOutput.getText().toString());
  }


  @Test
  public void flowTest2(){
    // run clicks on "7+5<backspace>4="
    for (View button: Arrays.asList(
      button7, buttonPlus, button5, buttonBackspace, button4, buttonEquals
    )) {
      button.performClick();
    }

    assertEquals("11", textViewOutput.getText().toString());
  }

  @Test
  public void flowTest3(){
    // run clicks on "7<clear>-5+4="
    for (View button: Arrays.asList(
            button7, buttonClear, buttonMinus, button5, buttonPlus, button4, buttonEquals
    )) {
      button.performClick();
    }

    assertEquals("-1", textViewOutput.getText().toString());
  }

  @Test
  public void flowTest4(){
    // run clicks on "4=<clear>"
    for (View button: Arrays.asList(
            button4, buttonEquals, buttonClear
    )) {
      button.performClick();
    }

    assertEquals("0", textViewOutput.getText().toString());
  }

  @Test
  public void flowTest5(){
    // run clicks on "-5+4=7"
    for (View button: Arrays.asList(
            buttonMinus, button5, buttonPlus, button4, buttonEquals, button7
    )) {
      button.performClick();
    }

    assertEquals("-17", textViewOutput.getText().toString());
  }

  @Test
  public void flowTest6(){
    // run clicks on "-5+4=87<deleteLast>"
    for (View button: Arrays.asList(
            buttonMinus, button5, buttonPlus, button4, buttonEquals, button8 ,button7, buttonBackspace
    )) {
      button.performClick();
    }

    assertEquals("-18", textViewOutput.getText().toString());
  }

  @Test
  public void flowTest7(){
    // input is empty, delete twice
    // run clicks on "<deleteLast><deleteLast>"
    assertEquals("0", textViewOutput.getText().toString());
    for (View button: Arrays.asList(
            buttonBackspace, buttonBackspace
    )) {
      button.performClick();
    }

    assertEquals("0", textViewOutput.getText().toString());
  }

  @Test
  public void flowTest8(){
    // input is **not** empty, delete twice
    // run clicks on "673<deleteLast><deleteLast>"
    assertEquals("0", textViewOutput.getText().toString());
    for (View button: Arrays.asList(
            button6 ,button7, button3 ,buttonBackspace, buttonBackspace
    )) {
      button.performClick();
    }

    assertEquals("6", textViewOutput.getText().toString());
  }

  @Test
  public void flowTest9(){
    // input is **not** empty, delete twice
    // run clicks on "673<clear>"
    assertEquals("0", textViewOutput.getText().toString());
    for (View button: Arrays.asList(
            button6 ,button7, button3 ,buttonClear
    )) {
      button.performClick();
    }

    assertEquals("0", textViewOutput.getText().toString());
  }

  @Test
  public void flowTest10(){
    // input is empty, delete twice
    // run clicks on "<clear>"
    buttonClear.performClick();
    assertEquals("0", textViewOutput.getText().toString());
  }

  @Test
  public void flowTest11(){
    // inserting 2 zeroes without equals results in 2 zeroes being in history
    for (View button: Arrays.asList(
            button0 , button0
    )) {
      button.performClick();
    }

    assertEquals("00", textViewOutput.getText().toString());
  }

  @Test
  public void flowTest12(){
    // inserting 2 zeroes with equals results in  1 zero being in history
    for (View button: Arrays.asList(
            button0 , button0, buttonEquals
    )) {
      button.performClick();
    }

    assertEquals("0", textViewOutput.getText().toString());
  }

}
