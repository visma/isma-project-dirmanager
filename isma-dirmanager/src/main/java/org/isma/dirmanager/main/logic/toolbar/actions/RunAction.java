package org.isma.dirmanager.main.logic.toolbar.actions;

import org.isma.dirmanager.main.logic.toolbar.IMainLogic;
public class RunAction extends AbstractToolBarActionListener {

    public RunAction(IMainLogic mainLogic) {
        super(mainLogic);
    }


    @Override
    protected void handleToolBarAction() throws Exception {
        mainLogic.getForm().getExpandButton().setEnabled(false);
        mainLogic.getForm().getCollapseButton().setEnabled(false);
        mainLogic.getForm().getRefreshButton().setEnabled(false);
        mainLogic.getForm().getRunButton().setEnabled(false);
        try {
            getCurrentSubLogic().onRun();
        }
        finally {
            mainLogic.getForm().getExpandButton().setEnabled(true);
            mainLogic.getForm().getCollapseButton().setEnabled(true);
            mainLogic.getForm().getRefreshButton().setEnabled(true);
            mainLogic.getForm().getRunButton().setEnabled(true);
        }
    }
}
