/*    */ package com.foxdream.utils;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
/*    */ import java.nio.file.Paths;
/*    */ import java.util.List;
/*    */ 
/*    */ public class KeyWordsRead
/*    */ {
/*    */   public static List<String> getKeyWords()
/*    */   {
/* 12 */     String prjectPath = System.getProperty("user.dir");
/* 13 */     Path dbPropertypath = Paths.get(prjectPath, new String[] { "keywords.txt" });
/*    */     try {
/* 15 */       return Files.readAllLines(dbPropertypath, StandardCharsets.UTF_8);
/*    */     }
/*    */     catch (IOException e) {
/* 18 */       e.printStackTrace();
/*    */     }
/* 20 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\yunsheng.ma\Desktop\findKeyWordsFromExcels-1.0.0.jar
 * Qualified Name:     com.foxdream.utils.KeyWordsRead
 * JD-Core Version:    0.6.0
 */