package drzhark.guiapi;

import de.matthiasmann.twl.Color;
import de.matthiasmann.twl.EditField;
import de.matthiasmann.twl.TextWidget;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.renderer.AnimationState;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLFont;
import drzhark.guiapi.widget.WidgetText;

import java.text.AttributedString;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is designed to enable you to make clones of the GuiAPI font,
 * colour it and add options as you want, and then set that font to specific
 * kinds of widgets.
 *
 * @author ShaRose
 *
 */
public class GuiApiFontHelper {

    /**
     * These are the font states you can use for your settings. Most of the
     * time, the only ones you will use would be normal and hover.
     *
     * @author ShaRose
     *
     */
    public enum FontStates {
        disabled, error, hover, normal, textSelection, warning
    }

    private static Map<Widget, GuiApiFontHelper> customFontWidgets;
    private static Map<FontStates, AttributedString> stateTable;
    static {
        GuiApiFontHelper.customFontWidgets = new HashMap<Widget, GuiApiFontHelper>();
        try {
            GuiApiFontHelper.stateTable = new HashMap<GuiApiFontHelper.FontStates, AttributedString>();
            FontStates[] states = FontStates.values();
            for (int i = 0; i < states.length; i++) {
                GuiApiFontHelper.stateTable.put(states[i], new AttributedString(states[i].name()));
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used internally to resync the font references. This has to
     * be used each time the theme is applied (After you set the screen,
     * specifically), otherwise TWL will automatically replace it with the
     * default font.
     */
    public static void resyncCustomFonts() {
        for (Map.Entry<Widget, GuiApiFontHelper> entry : GuiApiFontHelper.customFontWidgets.entrySet()) {
            // probably going to want to optimize this I think
            GuiApiFontHelper font = entry.getValue();
            Widget widget = entry.getKey();
            if (widget instanceof TextWidget) {
                font.setFont((TextWidget) widget);
            }
            if (widget instanceof EditField) {
                font.setFont((EditField) widget);
            }
            if (widget instanceof WidgetText) {
                font.setFont((WidgetText) widget);
            }
        }
    }

    private LWJGLFont myFont;

    /**
     * This creates a new GuiApiFontHelper with it's own internal font
     * reference.
     */
    public GuiApiFontHelper() {
        GuiWidgetScreen widgetScreen = GuiWidgetScreen.getInstance();
        LWJGLFont baseFont = (LWJGLFont) widgetScreen.theme.getDefaultFont();
        this.myFont = baseFont;
    }


    /**
     * @param widget The EditField (Or derived class) you wish to set.
     */
    public void setFont(EditField widget) {
        try {
            //setFont(widget.textRenderer);
            GuiApiFontHelper.customFontWidgets.put(widget, this);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * @param widget The TextWidget (Or derived class) you wish to set.
     */
    public void setFont(TextWidget widget) {
        widget.setFont(this.myFont);
        GuiApiFontHelper.customFontWidgets.put(widget, this);
    }

    /**
     * @param widget The WidgetText (Or derived class) you wish to set. This
     *        will set the display label (if it has one) and the edit field.
     */
    public void setFont(WidgetText widget) {
        if (widget.displayLabel != null) {
            widget.displayLabel.setFont(this.myFont);
            GuiApiFontHelper.customFontWidgets.put(widget, this);
        }
        setFont(widget.editField);
        GuiApiFontHelper.customFontWidgets.put(widget, this);
    }


}
