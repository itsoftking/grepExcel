/*    */ package com.foxdream;
/*    */ 
/*    */ import com.foxdream.utils.SXSSFWORKBookUtils;
/*    */ import java.io.File;
/*    */ import javax.swing.JFileChooser;
/*    */ import javax.swing.JOptionPane;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class App
/*    */ {
/* 14 */   private static final Logger LOGGER = LogManager.getLogger(App.class.getName());
/*    */ 
/*    */   public static void main(String[] args) {
/* 17 */     JFileChooser fileChooser = new JFileChooser("C:\\");
/* 18 */     fileChooser.setDialogTitle("Grep対象フォルダー選択");
/* 19 */     fileChooser.setFileSelectionMode(1);
/* 20 */     fileChooser.setMultiSelectionEnabled(false);
/* 21 */     fileChooser.setFileHidingEnabled(false);
/* 22 */     int value = fileChooser.showOpenDialog(null);
/* 23 */     String path = null;
/* 24 */     if (value == 0) {
/* 25 */       File getPath = fileChooser.getSelectedFile();
/* 26 */       path = getPath.getAbsolutePath();
/*    */     }
/* 28 */     if (path != null)
/*    */     {
/* 30 */       JFileChooser fileChooserOut = new JFileChooser("C:\\");
/* 31 */       fileChooserOut.setDialogTitle("Grep結果出力フォルダー選択");
/* 32 */       fileChooserOut.setFileSelectionMode(1);
/* 33 */       fileChooserOut.setMultiSelectionEnabled(false);
/* 34 */       fileChooserOut.setFileHidingEnabled(false);
/* 35 */       int valueOut = fileChooserOut.showOpenDialog(null);
/* 36 */       String pathOut = null;
/* 37 */       if (valueOut == 0) {
/* 38 */         File getPathOut = fileChooserOut.getSelectedFile();
/* 39 */         pathOut = getPathOut.getAbsolutePath();
/*    */       }
/* 41 */       if (pathOut != null) {
/* 42 */         JOptionPane.showMessageDialog(null, "仕様書Grep開始しました", "Grep開始", 1);
/* 43 */         long startTime = System.currentTimeMillis();
/*    */ 
/* 45 */         LOGGER.info("仕様書Grep開始しました。");
/* 46 */         SXSSFWORKBookUtils searchExcel = new SXSSFWORKBookUtils();
/* 47 */         boolean sucess = searchExcel.mainProcess(path, pathOut);
/* 48 */         long endTime = System.currentTimeMillis();
/* 49 */         LOGGER.info("実行時間：" + (endTime - startTime) / 1000L + "秒");
/* 50 */         if (sucess) {
/* 51 */           LOGGER.info("仕様書Grep終了しました。");
/* 52 */           JOptionPane.showMessageDialog(null, "仕様書Grep終了しました", "Grep終了", 1);
/*    */         } else {
/* 54 */           LOGGER.info("仕様書Grep終了しましたがエラーがあります。");
/* 55 */           JOptionPane.showMessageDialog(null, "仕様書Grep終了しましたが、エラーがあります。", "Grep終了", 1);
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\yunsheng.ma\Desktop\findKeyWordsFromExcels-1.0.0.jar
 * Qualified Name:     com.foxdream.App
 * JD-Core Version:    0.6.0
 */