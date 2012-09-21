package org.isma.dirmanager.main.logic;

import org.isma.core.ApplicationContext;
import org.isma.core.components.AbstractApplicationMenuBar;
import org.isma.dirmanager.AbstractDirManagerLogic;
import org.isma.dirmanager.IDirConfiguration;
import org.isma.dirmanager.LogicListener;
import org.isma.dirmanager.backuper.drainer.logic.DrainerLogic;
import org.isma.dirmanager.backuper.synchronizer.logic.SynchroLogic;
import org.isma.dirmanager.clonefinder.logic.CloneFinderLogic;
import org.isma.dirmanager.main.gui.MainForm;
import org.isma.dirmanager.main.logic.menu.DirManagerMenuBar;
import org.isma.dirmanager.main.logic.toolbar.IMainLogic;
import org.isma.dirmanager.main.logic.toolbar.actions.CollapseAction;
import org.isma.dirmanager.main.logic.toolbar.actions.ExpandAction;
import org.isma.dirmanager.main.logic.toolbar.actions.RefreshAction;
import org.isma.dirmanager.main.logic.toolbar.actions.RunAction;
import org.isma.dirmanager.refactor.logic.RefactorLogic;
import org.isma.dirmanager.tools.logic.ToolsLogic;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirManagerLogic extends AbstractDirManagerLogic<MainForm> implements IMainLogic {
    //Tab titles
    static final String SYNCHRONIZE_TITLE_TAB = "Synchronize";
    static final String DRAINER_TITLE_TAB = "Drainer";
    static final String CLONES_FINDER_TITLE_TAB = "ClonesFinder";
    static final String REFACTOR_TITLE_TAB = "Refactor";
    static final String TOOLS_TITLE_TAB = "Tools";

    private ApplicationContext<IDirConfiguration> context;
    private JFrame parentFrame;

    private JTabbedPane tabbedPane;
    private final DirManagerMenuBar menubar;

    //Logics
    private Map<String, AbstractDirManagerLogic> logicMap;

    //Listeners
    private List<LogicListener> logicListeners = new ArrayList<LogicListener>();


    public DirManagerLogic(ApplicationContext<IDirConfiguration> context, JFrame parentFrame, IDirConfiguration conf) {
        super(conf);
        this.context = context;
        this.parentFrame = parentFrame;

        this.logicMap = buildLogicMap();
        this.tabbedPane = buildTabbedPane();

        menubar = new DirManagerMenuBar(context, logicListeners);
        menubar.build();
        parentFrame.setJMenuBar(menubar);

        initForm();
        addListeners();
    }


    private void addListeners() {
        tabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                AbstractDirManagerLogic logic = getCurrentSubLogic();
                form.getCollapseButton().setEnabled(logic.canCollapse());
                form.getExpandButton().setEnabled(logic.canExpand());
                form.getRefreshButton().setEnabled(logic.canLoad());
                form.getRunButton().setEnabled(logic.canRun());
            }
        });
        form.getRefreshButton().addActionListener(new RefreshAction(this));
        form.getRunButton().addActionListener(new RunAction(this));
        form.getExpandButton().addActionListener(new ExpandAction(this));
        form.getCollapseButton().addActionListener(new CollapseAction(this));
    }


    private Map<String, AbstractDirManagerLogic> buildLogicMap() {
        Map<String, AbstractDirManagerLogic> map = new HashMap<String, AbstractDirManagerLogic>();
        map.put(SYNCHRONIZE_TITLE_TAB, new SynchroLogic(context));
        map.put(DRAINER_TITLE_TAB, new DrainerLogic(context));
        map.put(CLONES_FINDER_TITLE_TAB, new CloneFinderLogic(conf));
        map.put(REFACTOR_TITLE_TAB, new RefactorLogic(conf));
        map.put(TOOLS_TITLE_TAB, new ToolsLogic(conf));
        return map;
    }


    private JTabbedPane buildTabbedPane() {
        this.tabbedPane = new JTabbedPane();
        this.tabbedPane.setName("dirManagerTabbedPane");
        addLogic(SYNCHRONIZE_TITLE_TAB, logicMap.get(SYNCHRONIZE_TITLE_TAB));
        addLogic(DRAINER_TITLE_TAB, logicMap.get(DRAINER_TITLE_TAB));
        addLogic(CLONES_FINDER_TITLE_TAB, logicMap.get(CLONES_FINDER_TITLE_TAB));
        addLogic(REFACTOR_TITLE_TAB, logicMap.get(REFACTOR_TITLE_TAB));
        addLogic(TOOLS_TITLE_TAB, logicMap.get(TOOLS_TITLE_TAB));
        return tabbedPane;
    }


    private void addLogic(String tabLabel, AbstractDirManagerLogic logic) {
        tabbedPane.addTab(tabLabel, logic.getGui());
        logicListeners.add(logic);
    }


    private void initForm() {
        form.getMainContent().add(tabbedPane);
    }


    public AbstractDirManagerLogic getCurrentSubLogic() {
        String logicKey = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
        return logicMap.get(logicKey);
    }


    public MainForm getForm() {
        return form;
    }


    public JFrame getParentFrame() {
        return parentFrame;
    }


    public AbstractApplicationMenuBar getMenuBar() {
        return menubar;
    }


    @Override
    protected MainForm buildForm() {
        return new MainForm();
    }


    public JComponent getGui() {
        return form.getMainPanel();
    }
}
