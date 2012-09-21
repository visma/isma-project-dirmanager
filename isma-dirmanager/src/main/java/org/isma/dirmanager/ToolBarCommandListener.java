package org.isma.dirmanager;

public interface ToolBarCommandListener {

    public boolean canExpand();


    public void onExpand();


    public boolean canCollapse();


    public void onCollapse();


    public boolean canLoad();


    public void onLoad() throws Exception;


    public boolean canRun();


    public void onRun() throws Exception;
}
