package org.isma.dirmanager.main.logic.toolbar.actions;
import org.isma.dirmanager.main.logic.toolbar.IMainLogic;

public class CollapseAction extends AbstractToolBarActionListener {

    public CollapseAction(IMainLogic mainLogic) {
        super(mainLogic);
    }


    @Override
    protected void handleToolBarAction() {
        getCurrentSubLogic().onCollapse();
    }
}
