package main.java.console;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import java.awt.*;

/**
 * A script módban használt dokumentum osztály.
 * Kezeli a script szerkesztés speciális megjelenítését.
 */
public class ScriptModeDocument extends DefaultStyledDocument {

    private int lineNumber = 1;
    private final Style promptStyle;
    private final Style numberStyle;
    private final Style textStyle;
    private final Style scriptModeStyle;
    private final Style systemStyle;

    public ScriptModeDocument() {
        promptStyle = addStyle("prompt", null);
        StyleConstants.setForeground(promptStyle, Color.GREEN);
        StyleConstants.setFontFamily(promptStyle, "Consolas");
        StyleConstants.setFontSize(promptStyle, 14);

        numberStyle = addStyle("number", null);
        StyleConstants.setForeground(numberStyle, new Color(150, 150, 150));
        StyleConstants.setFontFamily(numberStyle, "Consolas");
        StyleConstants.setFontSize(numberStyle, 14);

        textStyle = addStyle("text", null);
        StyleConstants.setForeground(textStyle, Color.WHITE);
        StyleConstants.setFontFamily(textStyle, "Consolas");
        StyleConstants.setFontSize(textStyle, 14);

        scriptModeStyle = addStyle("scriptMode", null);
        StyleConstants.setForeground(scriptModeStyle, new Color(135, 206, 235));
        StyleConstants.setFontFamily(scriptModeStyle, "Consolas");
        StyleConstants.setFontSize(scriptModeStyle, 14);

        systemStyle = addStyle("system", null);
        StyleConstants.setForeground(systemStyle, new Color(200, 200, 200));
        StyleConstants.setFontFamily(systemStyle, "Consolas");
        StyleConstants.setFontSize(systemStyle, 14);
    }

    /**
     * Hozzáad egy prompt karaktert a dokumentumhoz.
     */
    public void appendPrompt() {
        try {
            insertString(getLength(), "> ", promptStyle);
        } catch (BadLocationException e) {

        }
    }

    /**
     * Hozzáad egy felhasználói bevitelt a dokumentumhoz.
     * @param text a bevitt szöveg
     * @param isScriptMode jelzi, hogy script módban vagyunk-e
     */
    public void appendUserInput(String text, boolean isScriptMode) {
        try {
            if (isScriptMode) {
                insertString(getLength(), lineNumber + ". ", numberStyle);
                insertString(getLength(), text + "\n", scriptModeStyle);
                lineNumber++;
            } else {
                insertString(getLength(), text + "\n", textStyle);
            }
        } catch (BadLocationException e) {

        }
    }

    public void appendSystemMessage(String text) {
        try {
            insertString(getLength(), text + "\n", systemStyle);
        } catch (BadLocationException e) {

        }
    }


    public void resetLineNumber() {
        lineNumber = 1;
    }
}