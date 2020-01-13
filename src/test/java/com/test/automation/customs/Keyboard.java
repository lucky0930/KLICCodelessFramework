package com.test.automation.customs;

import static java.awt.event.KeyEvent.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.ArrayList;

public class Keyboard {

    private Robot robot;
    private ArrayList<Integer> activeKeys = new ArrayList<>();

    public Keyboard() throws AWTException {
        this.robot = new Robot();
    }

    public Keyboard(Robot robot) {
        this.robot = robot;
    }

    public void type(CharSequence characters) {
        int length = characters.length();
        for (int i = 0; i < length; i++) {
            char character = characters.charAt(i);
            type(character);
        }
    }

    public int type(char character) {
        switch (character) {
        case 'a': return doType(VK_A);
        case 'b': return doType(VK_B);
        case 'c': return doType(VK_C);
        case 'd': return doType(VK_D);
        case 'e': return doType(VK_E);
        case 'f': return doType(VK_F);
        case 'g': return doType(VK_G);
        case 'h': return doType(VK_H);
        case 'i': return doType(VK_I);
        case 'j': return doType(VK_J);
        case 'k': return doType(VK_K);
        case 'l': return doType(VK_L);
        case 'm': return doType(VK_M);
        case 'n': return doType(VK_N);
        case 'o': return doType(VK_O);
        case 'p': return doType(VK_P);
        case 'q': return doType(VK_Q);
        case 'r': return doType(VK_R);
        case 's': return doType(VK_S);
        case 't': return doType(VK_T);
        case 'u': return doType(VK_U);
        case 'v': return doType(VK_V);
        case 'w': return doType(VK_W);
        case 'x': return doType(VK_X);
        case 'y': return doType(VK_Y);
        case 'z': return doType(VK_Z);
        case 'A': return doType(VK_SHIFT, VK_A);
        case 'B': return doType(VK_SHIFT, VK_B);
        case 'C': return doType(VK_SHIFT, VK_C);
        case 'D': return doType(VK_SHIFT, VK_D);
        case 'E': return doType(VK_SHIFT, VK_E);
        case 'F': return doType(VK_SHIFT, VK_F);
        case 'G': return doType(VK_SHIFT, VK_G);
        case 'H': return doType(VK_SHIFT, VK_H);
        case 'I': return doType(VK_SHIFT, VK_I);
        case 'J': return doType(VK_SHIFT, VK_J);
        case 'K': return doType(VK_SHIFT, VK_K);
        case 'L': return doType(VK_SHIFT, VK_L);
        case 'M': return doType(VK_SHIFT, VK_M);
        case 'N': return doType(VK_SHIFT, VK_N);
        case 'O': return doType(VK_SHIFT, VK_O);
        case 'P': return doType(VK_SHIFT, VK_P);
        case 'Q': return doType(VK_SHIFT, VK_Q);
        case 'R': return doType(VK_SHIFT, VK_R);
        case 'S': return doType(VK_SHIFT, VK_S);
        case 'T': return doType(VK_SHIFT, VK_T);
        case 'U': return doType(VK_SHIFT, VK_U);
        case 'V': return doType(VK_SHIFT, VK_V);
        case 'W': return doType(VK_SHIFT, VK_W);
        case 'X': return doType(VK_SHIFT, VK_X);
        case 'Y': return doType(VK_SHIFT, VK_Y);
        case 'Z': return doType(VK_SHIFT, VK_Z);
        case '`': return doType(VK_BACK_QUOTE);
        case '0': return doType(VK_0);
        case '1': return doType(VK_1);
        case '2': return doType(VK_2);
        case '3': return doType(VK_3);
        case '4': return doType(VK_4);
        case '5': return doType(VK_5);
        case '6': return doType(VK_6);
        case '7': return doType(VK_7);
        case '8': return doType(VK_8);
        case '9': return doType(VK_9);
        case '-': return doType(VK_MINUS);
        case '=': return doType(VK_EQUALS);
        case '~': return doType(VK_SHIFT, VK_BACK_QUOTE);
        case '!': return doType(VK_EXCLAMATION_MARK);
        case '@': return doType(VK_AT);
        case '#': return doType(VK_NUMBER_SIGN);
        case '$': return doType(VK_DOLLAR);
        case '%': return doType(VK_SHIFT, VK_5);
        case '^': return doType(VK_CIRCUMFLEX);
        case '&': return doType(VK_AMPERSAND);
        case '*': return doType(VK_ASTERISK);
        case '(': return doType(VK_LEFT_PARENTHESIS);
        case ')': return doType(VK_RIGHT_PARENTHESIS);
        case '_': return doType(VK_UNDERSCORE);
        case '+': return doType(VK_PLUS);
        case '\t': return doType(VK_TAB);
        case '\n': return doType(VK_ENTER);
        case '[': return doType(VK_OPEN_BRACKET);
        case ']': return doType(VK_CLOSE_BRACKET);
        case '\\': return doType(VK_BACK_SLASH);
        case '{': return doType(VK_SHIFT, VK_OPEN_BRACKET);
        case '}': return doType(VK_SHIFT, VK_CLOSE_BRACKET);
        case '|': return doType(VK_SHIFT, VK_BACK_SLASH);
        case ';': return doType(VK_SEMICOLON);
        case ':': return doType(VK_COLON);
        case '\'': return doType(VK_QUOTE);
        case '"': return doType(VK_QUOTEDBL);
        case ',': return doType(VK_COMMA);
        case '<': return doType(VK_SHIFT, VK_COMMA);
        case '.': return doType(VK_PERIOD);
        case '>': return doType(VK_SHIFT, VK_PERIOD);
        case '/': return doType(VK_SLASH);
        case '?': return doType(VK_SHIFT, VK_SLASH);
        case ' ': return doType(VK_SPACE);
        default:
            throw new IllegalArgumentException("Cannot type character " + character);
        }
    }
    
    public void keyCombo(String[] comboKeys) {
    	for (String input : comboKeys) {
	    	if (input.length() <= 1) {
	    		addToKeyCombo(type(input.toCharArray()[0]));
	    	} else { 
		    	switch (input.toUpperCase()) {
		    	case "CONTROL": addToKeyCombo(VK_CONTROL); break;
		    	case "ALT": addToKeyCombo(VK_ALT); break;
		    	case "SHIFT": addToKeyCombo(VK_SHIFT); break;
		    	case "WINDOWS": addToKeyCombo(VK_WINDOWS); break;
		    	case "TAB": addToKeyCombo(VK_TAB); break;
		    	case "ENTER": addToKeyCombo(VK_ENTER); break;
		    	case "ESCAPE":
		    	case "ESC": addToKeyCombo(VK_ESCAPE); break;
		    	case "PRINTSCREEN": addToKeyCombo(VK_PRINTSCREEN); break;
		    	}		    	
	    	}
    	}
    	System.out.println("WARNING: Simulated keyboard cannot perform browser layer or higher commands (\"ctrl+t\", \"alt+tab\", etc.)");
    }
    
    private void addToKeyCombo(int keyCode) {
    	activeKeys.add(keyCode);
    }
    
    public void runCombo() {
    	for (Integer keyCode : activeKeys) {
    		robot.keyPress(keyCode);
    	}
    	for (Integer key : activeKeys) {
    		robot.keyRelease(key);
    	}
    	activeKeys.clear();
    }

    private int doType(int... keyCodes) {
        return doType(keyCodes, 0, keyCodes.length);
    }

    private int doType(int[] keyCodes, int offset, int length) {
        if (length == 1) {
        	robot.keyPress(keyCodes[offset]);
        	robot.keyRelease(keyCodes[offset]);
            return keyCodes[offset];
        }

        robot.keyPress(keyCodes[offset]);
        int key = doType(keyCodes, offset + 1, length - 1);
        robot.keyRelease(keyCodes[offset]);
        return key;
    }

}