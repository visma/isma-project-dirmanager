package org.isma.dirmanager.main.logic.toolbar;

import org.isma.core.logic.IMenuBarLogic;
import org.isma.dirmanager.AbstractDirManagerLogic;
import org.isma.dirmanager.main.gui.MainForm;

import javax.swing.*;

public interface IMainLogic extends IMenuBarLogic {
    AbstractDirManagerLogic getCurrentSubLogic();


    MainForm getForm();


    JFrame getParentFrame();
}
