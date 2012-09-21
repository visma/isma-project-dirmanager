package org.isma.dirmanager;

import org.isma.core.logic.ILogic;
import org.isma.guitoolkit.IForm;

import javax.swing.*;
import java.io.File;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static org.isma.dirmanager.util.FileHelper.isSubDirectory;

public abstract class AbstractDirManagerLogic<F extends IForm> implements ILogic, LogicListener {
    protected IDirConfiguration conf;
    protected F form;

    //TODO gérer ces etats proprements pour griser le bouton run de la toolbar si pas de load prealable par exemple
    protected boolean loaded;
    protected boolean running;


    protected AbstractDirManagerLogic(IDirConfiguration configuration) {
        this.conf = configuration;
        form = buildForm();
        onConfigurationChange();
    }


    protected abstract F buildForm();


    public void onConfigurationChange() {
    }


    public void onExpand() {
    }


    public void onCollapse() {
    }


    public void onLoad() throws Exception {
    }


    public void onRun() throws Exception {
    }


    public boolean canExpand() {
        return false;
    }


    public boolean canCollapse() {
        return false;
    }


    public boolean canLoad() {
        return false;
    }


    public boolean canRun() {
        return false;
    }


    protected boolean checkDirectory(String path) {
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            return true;
        }
        JOptionPane.showMessageDialog(form.getMainPanel(),
                                      path + " is not a valid directory",
                                      "Invalid directory",
                                      ERROR_MESSAGE);
        return false;
    }


    protected boolean checkNoSubDirectory(String path1, String path2) {
        final File file1 = new File(path1);
        final File file2 = new File(path2);
        if (isSubDirectory(file1, file2)) {
            JOptionPane.showMessageDialog(form.getMainPanel(),
                                          path2 + " is a subdirectory",
                                          "Sub directory forbidden",
                                          ERROR_MESSAGE);
            return false;
        }
        if (isSubDirectory(file2, file1)) {
            JOptionPane.showMessageDialog(form.getMainPanel(),
                                          path1 + " is a subdirectory",
                                          "Sub directory forbidden",
                                          ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
