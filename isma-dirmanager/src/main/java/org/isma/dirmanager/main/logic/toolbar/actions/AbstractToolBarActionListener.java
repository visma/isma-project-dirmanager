package org.isma.dirmanager.main.logic.toolbar.actions;

import org.isma.dirmanager.AbstractDirManagerLogic;
import org.isma.dirmanager.main.gui.MainForm;
import org.isma.dirmanager.main.logic.toolbar.IMainLogic;
import org.isma.guitoolkit.LogicActionListener;

import java.awt.*;

public abstract class AbstractToolBarActionListener extends LogicActionListener {
    protected IMainLogic mainLogic;


    protected AbstractToolBarActionListener(IMainLogic mainLogic) {
        this.mainLogic = mainLogic;
    }


    @Override
    protected void doAction() throws Exception {
        doPreAction();
        handleToolBarAction();
        doPostAction();
    }


    private void doPostAction() {
        mainLogic.getParentFrame().setCursor(Cursor.getDefaultCursor());
    }


    private void doPreAction() {
        mainLogic.getParentFrame().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    }


    protected abstract void handleToolBarAction() throws Exception;


    protected MainForm getForm() {
        return mainLogic.getForm();
    }


    protected AbstractDirManagerLogic getCurrentSubLogic() {
        return mainLogic.getCurrentSubLogic();
    }
}
