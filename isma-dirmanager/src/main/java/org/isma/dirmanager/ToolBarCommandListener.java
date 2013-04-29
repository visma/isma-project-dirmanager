package org.isma.dirmanager;

public interface ToolBarCommandListener {

    boolean canExpand();


    void onExpand();


    boolean canCollapse();


    void onCollapse();


    boolean canLoad();


    void onLoad() throws Exception;


    boolean canRun();


    void onRun() throws Exception;
}
