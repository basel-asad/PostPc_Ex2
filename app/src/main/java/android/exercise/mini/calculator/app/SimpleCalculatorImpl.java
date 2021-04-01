package android.exercise.mini.calculator.app;

import android.content.Context;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SimpleCalculatorImpl implements SimpleCalculator {

  // todo: add fields as needed
  ArrayList<String> history = new ArrayList<String>(){{
//    add("0");
  }};
  String[] input_numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
  Set<String> input_orders = new HashSet<String>(){
    {
      add("+");
      add("-");
    }
  };

  @Override
  public String output() {
    // todo: return output based on the current state
    if (history.size() == 0){
      // if history is empty, return 0
      return "0";

    }
    else {
      String last_element = history.get(history.size() - 1);
      if(last_element.equals("=")) {
        // if ends with "=", evaluate then return new result
        //todo: clauclate the value of the expression and return it
//        return "still have not implemented the evaluate function";
        deleteLast(); // remove the "="
        int res = calculate();
        history.clear();
        if(res > 0){
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

  public int calculate() {
    int res = 0;
    int op_sign = 1;
    for (String s: history) {
      if(!input_orders.contains(s)){
        res += op_sign * Integer.parseInt(s);
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
    // todo: insert a digit
    if(0 <= digit && digit <= 9){
      history.add(String.valueOf(digit));
    }
    else{
      throw new IllegalArgumentException("Illegal input value");

    }
  }

  @Override
  public void insertPlus() {
    // todo: insert a plus
    if(is_order_addition_valid()) {
      history.add("+");
    }
  }

  @Override
  public void insertMinus() {
    // todo: insert a minus
    if(is_order_addition_valid()) {
      history.add("-");
    }
  }

  private boolean is_order_addition_valid() {
    if (history.size() > 0) {
      String last_element = history.get(history.size() - 1);
      if (input_orders.contains(last_element)) {
        // if the last element is + or -
        return false;
      }
    }
    return true;
  }

  @Override
  public void insertEquals() {
    // todo: calculate the equation. after calling `insertEquals()`, the output should be the result
    //  e.g. given input "14+3", calling `insertEquals()`, and calling `output()`, output should be "17"

    //todo: if last is order, remove it
    if(history.size() > 0){
      // remove last sign
      if(input_orders.contains(history.get(history.size() - 1))){
        deleteLast();
      }
    }
    history.add("=");
//    output();
  }

  @Override
  public void deleteLast() {
    // todo: delete the last input (digit, plus or minus)
    //  e.g.
    //  if input was "12+3" and called `deleteLast()`, then delete the "3"
    //  if input was "12+" and called `deleteLast()`, then delete the "+"
    //  if no input was given, then there is nothing to do here
    int arr_size = history.size();
    if(arr_size > 0){
      history.remove(arr_size - 1);
    }
//    output();
  }

  @Override
  public void clear() {
    // todo: clear everything (same as no-input was never given)
    history = new ArrayList<String>();
//    output();
  }

  @Override
  public Serializable saveState() {
    CalculatorState state = new CalculatorState();
    // todo: insert all data to the state, so in the future we can load from this state
    Collections.copy(state.history, history);
    return history;
  }

  @Override
  public void loadState(Serializable prevState) {
    if (!(prevState instanceof CalculatorState)) {
      return; // ignore
    }
    CalculatorState casted = (CalculatorState) prevState;
    // todo: use the CalculatorState to load
    history.clear();
    Collections.copy(history, casted.history);;

  }

  private static class CalculatorState implements Serializable {
    // just save history
    private ArrayList<String> history;

    /*
    TODO: add fields to this class that will store the calculator state
    all fields must only be from the types:
    - primitives (e.g. int, boolean, etc)
    - String
    - ArrayList<> where the type is a primitive or a String
    - HashMap<> where the types are primitives or a String
     */
  }
}
