package org.isma.dirmanager.main;
public class DirManagerLauncherTestRelease extends DirManagerLauncher {
    public DirManagerLauncherTestRelease() throws Exception {
    }


    @Override
    protected boolean showSplashScreen() {
        return false;
    }


    public static void main(String[] args) throws Exception {
        new DirManagerLauncherTestRelease().launch();
    }
}
