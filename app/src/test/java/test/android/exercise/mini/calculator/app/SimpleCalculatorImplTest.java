package test.android.exercise.mini.calculator.app;

import android.exercise.mini.calculator.app.R;
import android.exercise.mini.calculator.app.SimpleCalculatorImpl;
import android.view.View;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.statements.Fail;

import java.io.Serializable;

import static org.junit.Assert.*;

public class SimpleCalculatorImplTest {

  @Test
  public void when_noInputGiven_then_outputShouldBe0(){
    SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
    assertEquals("0", calculatorUnderTest.output());
  }

  @Test
  public void when_inputIsPlus_then_outputShouldBe0Plus(){
    SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
    calculatorUnderTest.insertPlus();
    assertEquals("0+", calculatorUnderTest.output());
  }


  @Test
  public void when_inputIsMinus_then_outputShouldBeCorrect(){
    SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
    calculatorUnderTest.insertMinus();
    String expected = "0-";
    assertEquals(expected, calculatorUnderTest.output());
  }

  @Test
  public void when_callingInsertDigitWithIllegalNumber_then_exceptionShouldBeThrown(){
    SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
    try {
      calculatorUnderTest.insertDigit(357);
      fail("should throw an exception and not reach this line");
    } catch (RuntimeException e) {
      // good :)
    }
  }


  @Test
  public void when_callingDeleteLast_then_lastOutputShouldBeDeleted(){
    // implement test
    SimpleCalculatorImpl calc = new SimpleCalculatorImpl();
    calc.insertDigit(9);
    calc.insertDigit(7);
    calc.insertDigit(6);
    // going once
    assertEquals("976", calc.output());
    calc.deleteLast();
    assertEquals("97", calc.output());
    // going twice
    calc.deleteLast();
    assertEquals("9", calc.output());
    //going thrice
    calc.deleteLast();
    assertEquals("0", calc.output());
    // calling delete last when nothing exists does nothing
    calc.deleteLast();
    assertEquals("0", calc.output());

  }

  @Test
  public void when_callingClear_then_outputShouldBeCleared(){
    // implement test
    SimpleCalculatorImpl calc = new SimpleCalculatorImpl();
    calc.insertDigit(9);
    calc.insertDigit(7);
    calc.insertDigit(6);
    assertEquals("976", calc.output());
    calc.clear();
    assertEquals("0", calc.output());
    // test 2
    calc.insertPlus();
    assertEquals("0+", calc.output());
    calc.clear();
    assertEquals("0", calc.output());
  }

  @Test
  public void when_savingState_should_loadThatStateCorrectly(){
    SimpleCalculatorImpl calculatorUnderTest = new SimpleCalculatorImpl();
    // give some input
    calculatorUnderTest.insertDigit(5);
    calculatorUnderTest.insertPlus();
    calculatorUnderTest.insertDigit(7);

    // save current state
    Serializable savedState = calculatorUnderTest.saveState();
    assertNotNull(savedState);

    // call `clear` and make sure calculator cleared
    calculatorUnderTest.clear();
    assertEquals("0", calculatorUnderTest.output());

    // load the saved state and make sure state was loaded correctly
    calculatorUnderTest.loadState(savedState);
    assertEquals("5+7", calculatorUnderTest.output());
  }

  @Test
  public void when_savingStateFromFirstCalculator_should_loadStateCorrectlyFromSecondCalculator(){
    /**
     *   with 2 calculators, give them different inputs, then save state on first calculator
     *   and load the state into second calculator, make sure state loaded well
     */
    SimpleCalculatorImpl firstCalculator = new SimpleCalculatorImpl();
    SimpleCalculatorImpl secondCalculator = new SimpleCalculatorImpl();
    firstCalculator.insertDigit(5);
    firstCalculator.insertPlus();
    firstCalculator.insertDigit(7);
    // save 1
    Serializable firstCalculatorsStateSaved = firstCalculator.saveState();
    assertNotNull(firstCalculatorsStateSaved);
    // call `clear` and make sure calculator cleared
    firstCalculator.clear();
    secondCalculator.clear();
    // load 1 into 2
    secondCalculator.loadState(firstCalculatorsStateSaved);
    assertEquals("5+7", secondCalculator.output());
  }

  @Test
  public void test_1(){
    //  given input "5+7-13<DeleteLast>25", expected output is "5+17-125"
    SimpleCalculatorImpl calc = new SimpleCalculatorImpl();
    calc.insertDigit(5);
    calc.insertPlus();
    calc.insertDigit(7);
    calc.insertMinus();
    // insert 13
    calc.insertDigit(1);
    calc.insertDigit(3);

    calc.deleteLast();

    calc.insertDigit(2);
    calc.insertDigit(5);
    assertEquals("5+7-125", calc.output());

  }

  @Test
  public void test_2(){
    //  given input "9<Clear>12<Clear>8-7=", expected output is "1"
    SimpleCalculatorImpl calc = new SimpleCalculatorImpl();
    calc.insertDigit(9);
    calc.clear();
    calc.insertDigit(1);
    calc.insertDigit(2);
    calc.clear();
    calc.insertDigit(8);
    calc.insertMinus();
    calc.insertDigit(7);
    calc.insertEquals();
    assertEquals("1", calc.output());
  }

  @Test
  public void test_3(){
    //  given input "999-888-222=-333", expected output is "-111-333"
    SimpleCalculatorImpl calc = new SimpleCalculatorImpl();
    calc.insertDigit(9);
    calc.insertDigit(9);
    calc.insertDigit(9);

    calc.insertMinus();

    calc.insertDigit(8);
    calc.insertDigit(8);
    calc.insertDigit(8);

    calc.insertMinus();

    calc.insertDigit(2);
    calc.insertDigit(2);
    calc.insertDigit(2);

    calc.insertEquals();
    calc.output();

    calc.insertMinus();
    calc.insertDigit(3);
    calc.insertDigit(3);
    calc.insertDigit(3);

    assertEquals("-111-333", calc.output());
  }

  @Test
  public void test_4(){
    //  When there is no input yet, output is "0"
    SimpleCalculatorImpl calc = new SimpleCalculatorImpl();
    assertEquals("0", calc.output());
  }

  @Test
  public void test_5(){
    //      When there is an equals "=" input, calculate the equation and the the output should only be the value (e.g. for input "15-7=" output should be "8")
    SimpleCalculatorImpl calc = new SimpleCalculatorImpl();
    calc.insertDigit(1);
    calc.insertDigit(5);
    calc.insertMinus();
    calc.insertDigit(7);
    calc.insertEquals();
    assertEquals("8", calc.output());
  }

  @Test
  public void test_6(){
    //      When input has multiple orders (such as "++", "--", "+-+--") only the first order is seen in the output (e.g. for input "15--7" the output should be "15-7")
    SimpleCalculatorImpl calc = new SimpleCalculatorImpl();
    calc.insertDigit(1);
    calc.insertDigit(5);
    // insert minus twice
    calc.insertMinus();
    calc.insertMinus();
    calc.insertDigit(7);
    assertEquals("15-7", calc.output());
  }

  @Test
  public void test_7(){
    //     When calling insertDigit() with a value outside the range [0, 9] an exception is thrown
    SimpleCalculatorImpl calc = new SimpleCalculatorImpl();
    try {
      calc.insertDigit(10);
      fail();
    }catch (Exception e){
      // test passed
    }
  }

  @Test
  public void test_8(){
    //          When calling deleteLast() and there is no input yet, the output should be "0" and no exception should be thrown.
    SimpleCalculatorImpl calc = new SimpleCalculatorImpl();
    try {
      calc.deleteLast();
      assertEquals("0", calc.output());
    }catch (Exception e){
      fail();
    }
  }

  @Test
  public void test_9(){
    //  Test it when calling enough times to deleteLast() that all the input is deleted
    SimpleCalculatorImpl calc = new SimpleCalculatorImpl();
    calc.insertDigit(5);
    try {
      assertEquals("5", calc.output());
      calc.deleteLast();
      assertEquals("0", calc.output());
      calc.deleteLast();
      assertEquals("0", calc.output());
    }catch (Exception e){
      fail();
    }
  }

  @Test
  public void test_10(){
    //      When calling clear() the output should be cleared to "0"
    SimpleCalculatorImpl calc = new SimpleCalculatorImpl();
    calc.insertDigit(5);
    calc.insertDigit(7);
    calc.clear();
    assertEquals("0", calc.output());
  }


}