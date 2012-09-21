package org.isma.dirmanager.main.logic.menu.actions;

import org.isma.core.ApplicationContext;
import org.isma.core.actions.AbstractDialogAction;
import org.isma.dirmanager.IDirConfiguration;
import org.isma.dirmanager.LogicListener;
import org.isma.dirmanager.gui.DirManagerIconEnum;
import org.isma.dirmanager.preferences.PreferencesLogic;
import org.isma.guitoolkit.error.ErrorHandler;

import java.awt.event.ActionEvent;
import java.util.List;

import static java.awt.event.KeyEvent.VK_P;

public class PreferencesAction extends AbstractDialogAction {
    private final ApplicationContext<IDirConfiguration> context;
    private final List<LogicListener> logicListeners;


    public PreferencesAction(final ApplicationContext<IDirConfiguration> context,
                             final List<LogicListener> logicListeners) {
        super(context.getMainFrame(), "Preferences", DirManagerIconEnum.PREFERENCES.getImageIcon(), VK_P);
        this.context = context;
        this.logicListeners = logicListeners;
    }


    public void actionPerformed(ActionEvent e) {
        try {
            displayDialog(new PreferencesLogic(context.getConfiguration(), logicListeners).getGui(),
                          getLabel(), getIcon());
        }
        catch (Exception e1) {
            ErrorHandler.handleException(e1);
        }
    }
}
