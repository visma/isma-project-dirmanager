package org.isma.dirmanager.main;

import org.isma.core.main.AbstractApplicationLauncher;
import org.isma.dirmanager.DirConfigurationFactory;
import org.isma.dirmanager.IDirConfiguration;
import org.isma.dirmanager.main.logic.DirManagerLogic;

import javax.swing.*;

public class DirManagerLauncher extends AbstractApplicationLauncher<IDirConfiguration, DirManagerLogic> {

    protected DirManagerLauncher() throws Exception {
    }


    @Override
    protected IDirConfiguration buildConfiguration() throws Exception {
        return new DirConfigurationFactory().buildDirConfiguration();
    }


    @Override
    protected DirManagerLogic buildMainLogic(JFrame frame) throws Exception {
        return new DirManagerLogic(this, frame, conf);
    }


    public static void main(String[] args) throws Exception {
        new DirManagerLauncher().launch();
    }
}
