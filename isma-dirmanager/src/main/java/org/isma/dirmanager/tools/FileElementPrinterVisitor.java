package org.isma.dirmanager.tools;

import org.isma.dirmanager.model.AbstractFileElement;
import org.isma.dirmanager.model.DirElement;
import org.isma.dirmanager.model.FileElementVisitor;
import org.isma.dirmanager.util.ILogger;

import static org.isma.dirmanager.util.GlobalProperties.getLineSeparator;

public class FileElementPrinterVisitor implements FileElementVisitor {
    private ILogger logger;


    public FileElementPrinterVisitor(ILogger logger) {
        this.logger = logger;
    }


    public void visit(AbstractFileElement fileElement) throws Exception {
        getLineSeparator();
        logger.log("%s%s", fileElement.getFile().getAbsolutePath(), getLineSeparator());
        if (fileElement instanceof DirElement) {
            for (AbstractFileElement subFileElement : ((DirElement)fileElement).getElements()) {
                visit(subFileElement);
            }
        }
    }
}
