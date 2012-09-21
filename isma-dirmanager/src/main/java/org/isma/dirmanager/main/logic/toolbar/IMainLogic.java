package org.isma.dirmanager.main.logic.toolbar;

import org.isma.core.logic.IMenuBarLogic;
import org.isma.dirmanager.AbstractDirManagerLogic;
import org.isma.dirmanager.main.gui.MainForm;

import javax.swing.*;

public interface IMainLogic extends IMenuBarLogic {
    public AbstractDirManagerLogic getCurrentSubLogic();


    public MainForm getForm();


    public JFrame getParentFrame();
}
