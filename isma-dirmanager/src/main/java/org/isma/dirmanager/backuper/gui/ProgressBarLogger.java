package org.isma.dirmanager.backuper.gui;

import org.isma.dirmanager.util.IStepLogger;

import javax.swing.*;
import java.awt.*;

//TODO problemes de refresh a fixer (?)
public class ProgressBarLogger extends JPanel implements IStepLogger {
    private JLabel stepLabel;
    private JLabel noStepLabel;
    private JProgressBar progressBar;


    public ProgressBarLogger() {
        setLayout(new BorderLayout());
        progressBar = new JProgressBar(0, 0);
        stepLabel = new JLabel();
        noStepLabel = new JLabel();
        setVisible(false);
        setPreferredSize(new Dimension(800, 80));
        add(progressBar, BorderLayout.PAGE_START);
        add(stepLabel, BorderLayout.CENTER);
        add(noStepLabel, BorderLayout.PAGE_END);
    }


    public void reset(int max) {
        stepLabel.setText(null);
        noStepLabel.setText(null);
        if (max != -1) {
            progressBar.setMaximum(max);
        }
        progressBar.setValue(0);
    }


    public void logStep(String log, Object... params) {
        if (!isVisible()) {
            setVisible(true);
        }
        String step = String.format("(%s/%s) : ", progressBar.getValue() + 1, progressBar.getMaximum());
        stepLabel.setText(String.format(step + log, params));
        progressBar.setValue(progressBar.getValue() + 1);
    }


    public void log(String log, Object... params) throws Exception {
        System.out.printf(log, params);
        if (!isVisible()) {
            setVisible(true);
        }
        noStepLabel.setText(String.format(log, params));
    }


    public void warn(String log, Object... params) throws Exception {
        log(log, params);
    }


    public void finish(String log, Object... params) throws Exception {
        log(log, params);
        progressBar.setMaximum(1);
        progressBar.setValue(1);
    }


    public JProgressBar getProgressBar() {
        return progressBar;
    }
}
