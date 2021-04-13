package android.exercise.mini.calculator.app;

import android.content.Context;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SimpleCalculatorImpl implements SimpleCalculator {

  ArrayList<String> history = new ArrayList<String>();
  String[] input_numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
  Set<String> input_orders = new HashSet<String>(){
    {
      add("+");
      add("-");
    }
  };

  @Override
  public String output() {
    // return output based on the current state
    if (history.size() == 0){
      // if history is empty, return 0
      return "0";

    }
    else {
      String last_element = history.get(history.size() - 1);
      if(last_element.equals("=")) {
        // if ends with "=", evaluate then return new result
        deleteLast(); // remove the "="
        long res = calculate();
        history.clear();
        if(res >= 0){
          history.add(String.valueOf(res));
        }
        else{
          history.add("-");
          history.add(String.valueOf(-1 * res));
        }
        return String.valueOf(res);
      }
      else{
        // assuming legal values of history

        // if ends with number or order,  return representation
        StringBuilder sb = new StringBuilder();
        for (String s : history) {
          sb.append(s);
        }
        return sb.toString();

      }

    }
  }

  public Long calculate() {
    long res = 0;
    int op_sign = 1;
    for (String s: history) {
      if(!input_orders.contains(s)){
        res += op_sign * Long.parseLong(s);
      }
      else{
        if(s.equals("+")){
          op_sign = 1;
        }
        else if(s.equals("-")){
          op_sign = -1;
        }
//        else{
//          return s;
//        }

      }
    }

//    return String.valueOf(res);
    return res;
  }

  @Override
  public void insertDigit(int digit) {
    // insert a digit
    if(0 <= digit && digit <= 9){
      if(history.size() > 0 && !input_orders.contains(history.get(history.size() - 1))){
        String last_elm = history.get(history.size() - 1);
//        deleteLast(); // remove 6
        history.remove(history.size() - 1);
        history.add(last_elm + String.valueOf(digit));  // add 67

      }
      else {
        // if a sign (or nothing) was inserted before the didigt
        history.add(String.valueOf(digit));
      }
    }
    else{
      throw new IllegalArgumentException("Illegal input value");

    }
  }

  @Override
  public void insertPlus() {
    // insert a plus
    if(prep_and_decide_if_order_addition_valid()) {
      history.add("+");
    }
  }

  @Override
  public void insertMinus() {
    // insert a minus
    if(prep_and_decide_if_order_addition_valid()) {
      history.add("-");
    }
  }

  private boolean prep_and_decide_if_order_addition_valid() {
    /**
     * return true if order addition is possible, if the history is empty insert a zero before returning True
     */
    if (history.size() > 0) {
      String last_element = history.get(history.size() - 1);
      if (input_orders.contains(last_element)) {
        // if the last element is + or -
        return false;
      }
    }
    else{
      // if the history is empty, insert a zero before the sign
      insertDigit(0);
    }
    return true;
  }

  @Override
  public void insertEquals() {
    /*  calculate the equation. after calling `insertEquals()`, the output should be the result
        e.g. given input "14+3", calling `insertEquals()`, and calling `output()`, output should be "17"
    */

    //if last is order, remove it
    if(history.size() > 0){
      // remove last sign
      if(input_orders.contains(history.get(history.size() - 1))){
        deleteLast();
      }
    }
    history.add("=");
    output();
  }

  @Override
  public void deleteLast() {
    /* delete the last input (digit, plus or minus)
      e.g.
      if input was "12+3" and called `deleteLast()`, then delete the "3"
      if input was "12+" and called `deleteLast()`, then delete the "+"
      if no input was given, then there is nothing to do here
     */
    int arr_size = history.size();
    if(arr_size > 0){
      String last_element = history.get(arr_size - 1);
      if(!input_orders.contains(last_element)){
        // the last element is a number
        if(last_element.length() > 1){
          // the last element is "12345"
          history.remove(arr_size - 1);
          // remove the 5
          last_element = last_element.substring(0, last_element.length()-1);
          history.add(last_element);
        }
        else{
          // delete the last element
          history.remove(arr_size - 1);
        }
      }
      else {
      history.remove(arr_size - 1);
      }
    }
  }

  @Override
  public void clear() {
    // clear everything (same as no-input was never given)
    history = new ArrayList<String>();
  }

  @Override
  public Serializable saveState() {
    CalculatorState state = new CalculatorState();
    state.history = new ArrayList<String>(history);
    return state;
  }

  @Override
  public void loadState(Serializable prevState) {
    if (!(prevState instanceof CalculatorState)) {
      return; // ignore
    }
    CalculatorState casted = (CalculatorState) prevState;
    history.clear();
    history.addAll(casted.history);

  }

  private static class CalculatorState implements Serializable {
    // just save history
    private ArrayList<String> history;
  }
}
