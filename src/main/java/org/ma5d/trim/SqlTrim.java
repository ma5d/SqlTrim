package org.ma5d.trim;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class SqlTrim extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {

        try {
            Editor editor = event.getData(CommonDataKeys.EDITOR);
            Project project = event.getProject();
            String oldValue = Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor).toString();
            String newValue = oldValue.replaceAll("[\\+\"]", "");

            // 将插入操作放到 Write Command Action 中执行
            WriteCommandAction.runWriteCommandAction(project, () -> {
                Caret caret = editor.getCaretModel().getCurrentCaret();
                editor.getDocument().insertString(caret.getOffset(), newValue);
            });

        } catch (UnsupportedFlavorException | IOException ex) {
            throw new RuntimeException(ex);
        }

    }
}
