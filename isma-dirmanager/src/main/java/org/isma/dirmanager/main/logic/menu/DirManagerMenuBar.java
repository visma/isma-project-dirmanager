package org.isma.dirmanager.main.logic.menu;
import org.isma.core.ApplicationContext;
import org.isma.core.components.AbstractApplicationMenuBar;
import org.isma.dirmanager.IDirConfiguration;
import org.isma.dirmanager.LogicListener;
import org.isma.dirmanager.main.logic.menu.actions.PreferencesAction;

import java.util.List;

public class DirManagerMenuBar extends AbstractApplicationMenuBar<IDirConfiguration> {
    private List<LogicListener> logicListeners;


    public DirManagerMenuBar(ApplicationContext<IDirConfiguration> context,
                             List<LogicListener> logicListeners) {
        super(context);
        this.logicListeners = logicListeners;
    }


    @Override
    protected void fillFileMenu() {
        fileMenu.add(menuBuilder.buildMenuItem(new PreferencesAction(context, logicListeners)));
    }
}
