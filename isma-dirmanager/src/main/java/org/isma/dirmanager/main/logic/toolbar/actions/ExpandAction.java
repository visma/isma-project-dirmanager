package org.isma.dirmanager.main.logic.toolbar.actions;

import org.isma.dirmanager.main.logic.toolbar.IMainLogic;
public class ExpandAction extends AbstractToolBarActionListener {

    public ExpandAction(IMainLogic mainLogic) {
        super(mainLogic);
    }


    @Override
    protected void handleToolBarAction() {
        getCurrentSubLogic().onExpand();
    }
}
