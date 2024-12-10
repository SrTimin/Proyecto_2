package Proyecto_2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class main {

    public static ArrayList<String> process(String input) {
        ArrayList<String> result = new ArrayList<>();
        String[] splitInput = input.split(" ");
        result.addAll(Arrays.asList(splitInput));
        return result;
    }

    public static void main(String[] args) {
        System.out.println("Por favor, ingrese el autómata:");
        Scanner scanner = new Scanner(System.in);
        boolean errorState = false;
        int errorStage = 0;

        Character[] bannedCharsForStates = {'#', '\"', '\'', ',', '.', '_', '+', '-'};
        Character[] bannedNumsForStates = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
        Character[] bannedChars = {'#', '\"', '\'', ',', '.'};

        // Input de la primera línea
        String statesInput = scanner.nextLine();
        ArrayList<String> states = process(statesInput);

        for (String state : states) {
            Character firstChar = state.charAt(0);
            for (Character bannedChar : bannedCharsForStates) {
                if (bannedChar == firstChar) {
                    errorState = true;
                    errorStage = 1;
                    break;
                }
            }

            if (state.length() == 1) {
                Character numToTest = state.charAt(0);
                for (Character bannedNum : bannedNumsForStates) {
                    if (bannedNum.equals(numToTest)) {
                        errorState = true;
                        errorStage = 1;
                        break;
                    }
                }
            }
        }
        for (String state : states) {
            int stateCount = 0;
            for (String otherState : states) {
                if (otherState.equals(state)) {
                    stateCount++;
                }
            }
            if (stateCount > 1) {
                errorState = true;
                errorStage = 1;
                break;
            }
        }

        // Input de la segunda línea
        String symbolsAlphInput = scanner.nextLine();
        ArrayList<String> symbolsAlph = process(symbolsAlphInput);

        if (!errorState) {
            for (String symbol : symbolsAlph) {
                if (symbol.length() > 1) {
                    errorState = true;
                    errorStage = 2;
                    break;
                }

                for (Character bannedChar : bannedChars) {
                    Character symToTest = symbol.charAt(0);
                    if (bannedChar == symToTest) {
                        errorState = true;
                        errorStage = 2;
                        break;
                    }
                }
            }
            for (String symbol : symbolsAlph) {
                int symbolCount = 0;
                for (String otherSymbol : symbolsAlph) {
                    if (otherSymbol.equals(symbol)) {
                        symbolCount++;
                    }
                }
                if (symbolCount > 1) {
                    errorState = true;
                    errorStage = 2;
                    break;
                }
            }
        }

        // Input de la tercera línea
        String symbolsPileInput = scanner.nextLine();
        ArrayList<String> symbolsPile = process(symbolsPileInput);

        if (!errorState) {
            for (String symbol : symbolsPile) {
                if (symbol.length() > 1) {
                    errorState = true;
                    errorStage = 3;
                    break;
                }

                for (Character bannedChar : bannedChars) {
                    Character symToTest = symbol.charAt(0);
                    if (bannedChar == symToTest) {
                        errorState = true;
                        errorStage = 3;
                        break;
                    }
                }
            }
            for (String symbol : symbolsPile) {
                int symbolCount = 0;
                for (String otherSymbol : symbolsPile) {
                    if (otherSymbol.equals(symbol)) {
                        symbolCount++;
                    }
                }
                if (symbolCount > 1) {
                    errorState = true;
                    errorStage = 3;
                    break;
                }
            }
        }

        // Input de la cuarta línea
        String initialStateInput = scanner.nextLine();
        ArrayList<String> initialState = process(initialStateInput);

        if (!errorState) {
            if (initialState.size() > 1) {
                errorState = true;
                errorStage = 4;
            }

            if (!states.contains(initialState.get(0))) {
                errorState = true;
                errorStage = 4;
            }
        }

        // Input de la quinta línea
        String finalStatesInput = scanner.nextLine();
        ArrayList<String> finalStates = process(finalStatesInput);

        if (!errorState) {
            for (String state : finalStates) {
                if (!states.contains(state)) {
                    errorState = true;
                    errorStage = 5;
                    break;
                }
            }
        }

        // Input de la sexta línea
        String pileBottomInput = scanner.nextLine();
        ArrayList<String> pileBottom = process(pileBottomInput);

        if (!errorState) {
            if (pileBottom.size() > 1) {
                errorState = true;
                errorStage = 6;
            }

            if (!symbolsPile.contains(pileBottom.get(0))) {
                errorState = true;
                errorStage = 6;
            }
        }
        symbolsPile.add("#");

        // Input de la séptima línea
        String transitionsInput = scanner.nextLine();
        ArrayList<String> transitions = process(transitionsInput);
        String nextInput = "(";
        String aux = nextInput.replaceAll("\\s+", "");
        while (nextInput.charAt(0) == '(') {
            nextInput = scanner.nextLine();
            aux = nextInput.replaceAll("\\s+", "");
            if (aux.charAt(0) == '(') {
                ArrayList<String> additionalTransitions = process(nextInput);
                for (String transition : additionalTransitions) {
                    transitions.add(transition);
                }
            }
        }

        ArrayList<String> stateTransitions = new ArrayList<>();
        ArrayList<String> symbolsAlphTransitions = new ArrayList<>();
        ArrayList<String> symbolPileStateTransitions = new ArrayList<>();
        ArrayList<String> wordPileTransitions = new ArrayList<>();

        boolean invalidTransitionFormat = false;
        for (String transition : transitions) {
            int commaCount = 0;
            for (int i = 0; i < transition.length(); i++) {
                if (transition.charAt(i) == ',') {
                    commaCount++;
                }
            }
            if (commaCount == 3) {
                String[] transitionParts = transition.split(",");
                stateTransitions.add(transitionParts[0].replaceAll("([(,)])", ""));
                symbolsAlphTransitions.add(transitionParts[1]);
                symbolPileStateTransitions.add(transitionParts[2]);
                wordPileTransitions.add(transitionParts[3].replaceAll("([(,)])", ""));
            } else {
                invalidTransitionFormat = true;
            }
        }
        int transitionCount = transitions.size();
        if (!invalidTransitionFormat) {
            boolean invalidTransition = false;
            for (int i = 0; i < transitionCount; i++) {
                if (wordPileTransitions.get(i).length() > 1) {
                    wordPileTransitions.set(i, wordPileTransitions.get(i).replaceAll("#", ""));
                    if (wordPileTransitions.get(i).length() == 0) {
                        wordPileTransitions.set(i, "#");
                    }
                }

                if (stateTransitions.get(i).equals("") || symbolsAlphTransitions.get(i).equals("") || symbolPileStateTransitions.get(i).equals("") || wordPileTransitions.get(i).equals("")) {
                    invalidTransition = true;
                }

                if (!states.contains(stateTransitions.get(i))) {
                    invalidTransition = true;
                }

                if (!symbolsAlph.contains(symbolsAlphTransitions.get(i))) {
                    invalidTransition = true;
                }

                if (!symbolsPile.contains(String.valueOf(symbolPileStateTransitions.get(i).charAt(0))) || symbolPileStateTransitions.get(i).charAt(1) != '=') {
                    invalidTransition = true;
                }

                if (!states.contains(symbolPileStateTransitions.get(i).substring(2))) {
                    invalidTransition = true;
                }

                int pileLength = wordPileTransitions.get(i).length();
                for (int j = 0; j < pileLength; j++) {
                    if (!symbolsPile.contains(String.valueOf(wordPileTransitions.get(i).charAt(j)))) {
                        invalidTransition = true;
                    }
                }

                if (transitions.get(i).charAt(0) != '(' || transitions.get(i).charAt(transitions.get(i).length() - 1) != ')') {
                    invalidTransition = true;
                }

                if (symbolPileStateTransitions.get(i).charAt(0) == '#') {
                    invalidTransition = true;
                }

                boolean left = false;
                boolean right = false;
                if (String.valueOf(symbolPileStateTransitions.get(i).charAt(0)).equals(pileBottom.get(0))) {
                    left = true;
                }
                if (String.valueOf(wordPileTransitions.get(i).charAt(wordPileTransitions.get(i).length() - 1)).equals(pileBottom.get(0))) {
                    right = true;
                }
                if (right ^ left) {
                    invalidTransition = true;
                }

                int pileSymbolCount = wordPileTransitions.get(i).length();
                int pileBottomCount = 0;
                for (int j = 0; j < pileSymbolCount; j++) {
                    if (String.valueOf(wordPileTransitions.get(i).charAt(j)).equals(pileBottom.get(0))) {
                        pileBottomCount++;
                    }
                }
                if (pileBottomCount > 1) {
                    invalidTransition = true;
                }

                int transitionMatchCount = 0;
                for (int j = 0; j < transitionCount; j++) {
                    if (stateTransitions.get(i).equals(stateTransitions.get(j)) && symbolsAlphTransitions.get(i).equals(symbolsAlphTransitions.get(j)) && symbolPileStateTransitions.get(i).charAt(0) == symbolPileStateTransitions.get(j).charAt(0)) {
                        transitionMatchCount++;
                    }
                    if (transitionMatchCount > 1) {
                        invalidTransition = true;
                        break;
                    }
                }

                if (invalidTransition) {
                    if (!errorState) {
                        errorState = true;
                        errorStage = 7;
                        break;
                    }
                }
            }
        } else {
            errorState = true;
            errorStage = 7;
        }

        // Input de la octava línea
        if (aux.charAt(0) == '(') {
            nextInput = scanner.nextLine();
        }
        ArrayList<String> wordInput = process(nextInput.replaceAll("#", ""));

        String word = wordInput.get(0);
        int wordLength = word.length();
        for (int i = 0; i < wordLength; i++) {
            if (!symbolsAlph.contains(String.valueOf(word.charAt(i)))) {
                if (!errorState) {
                    errorState = true;
                    errorStage = 8;
                    break;
                }
            }
        }

        if (!errorState) {
            Pila pila = new Pila();
            String pileBottomSymbol = pileBottom.get(0);
            pila.push(pileBottomSymbol);
            boolean read = false;
            boolean control = false;
            String currentState = initialState.get(0);
            boolean transitionFound = true;
            System.out.println("Procesando cadena: " + word);
            System.out.println("." + word + " " + currentState + " " + pileBottomSymbol);
            for (int i = 0; i < wordLength; i++) {
                for (int j = 0; j < transitionCount; j++) {
                    transitionFound = true;
                    if (currentState.equals(stateTransitions.get(j)) && symbolsAlphTransitions.get(j).equals(String.valueOf(word.charAt(i))) && pila.peek().equals(String.valueOf(symbolPileStateTransitions.get(j).charAt(0)))) {
                        currentState = symbolPileStateTransitions.get(j).substring(2);
                        if (wordPileTransitions.get(j).equals("#")) {
                            pila.pop();
                        } else {
                            int pileLength = wordPileTransitions.get(j).length();
                            pila.pop();
                            for (int k = pileLength - 1; k >= 0; k--) {
                                pila.push(String.valueOf(wordPileTransitions.get(j).charAt(k)));
                            }
                        }
                        transitionFound = false;
                        break;
                    }
                }
                if (transitionFound) {
                    control = true;
                    System.out.println("Rechazado");
                    break;
                }

                if (pila.isEmpty()) {
                    System.out.println("Error encontrado en 7");
                    pila.push(" ");
                    control = true;
                    break;
                }
                String processedWord = "";
                for (int z = 0; z < wordLength; z++) {
                    if (z == i + 1) {
                        processedWord = processedWord + ".";
                    }

                    processedWord = processedWord + word.charAt(z);
                }
                if (i == wordLength - 1) {
                    processedWord = word + ".";
                    read = true;
                }
                processedWord = processedWord + " " + currentState;
                System.out.println(processedWord + " " + pila.toString());
            }
            if (!control) {
                if (finalStates.contains(currentState) && pila.peek().equals(pileBottomSymbol) && read) {
                    System.out.println("Aceptado");
                } else {
                    System.out.println("Rechazado");
                }
            }
        }
        if (errorState) {
            System.out.println("Error encontrado en " + errorStage);
        }
    }
}