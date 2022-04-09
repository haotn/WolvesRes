package com.wolvesres.helper;

import javax.swing.JDialog;
import javax.swing.JFrame;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class AnimationShowWindow {

    private static boolean show;
    private static Animator animator;

	private static void animationShowWindow(final JFrame frame, final JDialog dialog) {

        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                if (show) {
                    if (frame != null) {
                        frame.setOpacity(fraction);
                    }
                    if (dialog != null) {
                        dialog.setOpacity(fraction);
                    }
                } else {
                    if (frame != null) {
                        frame.setOpacity(1f - fraction);
                    }
                    if (dialog != null) {
                        dialog.setOpacity(1f - fraction);
                    }
                }
            }

            @Override
            public void end() {
                if (!show) {
                    if (frame != null) {
                        frame.setVisible(false);
                    }
                    if (dialog != null) {
                        dialog.setVisible(false);
                    }
                }
            }
        };
//        if (animator.isRunning()) {
//            animator.stop();
//        }
        animator = new Animator(500, target);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        animator.setResolution(0);
    }

//    private static void animationCloseWindow() {
//        if (animator.isRunning()) {
//            animator.stop();
//        }
//        show = false;
//        animator.start();
//    }
    public static void showFrame(JFrame frame) {
        show = true;
        animationShowWindow(frame, null);
        animator.start();
    }

    public static void showDialog(JDialog dialog) {
        show = true;
        animationShowWindow(null, dialog);
        animator.start();
    }

    public static void closeFrame(JFrame frame) {
        if (animator.isRunning()) {
            animator.stop();
        }
        show = false;
        animationShowWindow(frame, null);
        animator.start();
    }

    public static void closeDialog(JDialog dialog) {
        if (animator.isRunning()) {
            animator.stop();
        }
        show = false;
        animationShowWindow(null, dialog);
        animator.start();
    }

}
