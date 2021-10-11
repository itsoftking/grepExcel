/*    */ package com.foxdream.utils;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ import java.nio.file.FileVisitResult;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.LinkOption;
/*    */ import java.nio.file.Path;
/*    */ import java.nio.file.Paths;
/*    */ import java.nio.file.SimpleFileVisitor;
/*    */ import java.nio.file.attribute.BasicFileAttributes;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class FillePaths extends SimpleFileVisitor<Path>
/*    */ {
/* 15 */   private List<Path> files = new ArrayList();
/*    */ 
/*    */   private void find(Path path) {
/* 18 */     if (!Files.isDirectory(path, new LinkOption[0])) {
/* 19 */       String fileName = path.getFileName().toString();
/*    */ 
/* 21 */       if ((!fileName.matches(".*?(~\\$).*?\\.xlsx")) && ((fileName.endsWith(".xls")) || (fileName.endsWith(".xlsx"))))
/*    */       {
/* 23 */         this.files.add(path);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
/*    */   {
/* 30 */     find(file);
/* 31 */     return FileVisitResult.CONTINUE;
/*    */   }
/*    */ 
/*    */   public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
/*    */   {
/* 36 */     find(dir);
/* 37 */     return FileVisitResult.CONTINUE;
/*    */   }
/*    */ 
/*    */   public FileVisitResult visitFileFailed(Path file, IOException e)
/*    */   {
/* 42 */     System.out.println(e);
/* 43 */     return FileVisitResult.CONTINUE;
/*    */   }
/*    */ 
/*    */   public List<Path> getFiles() {
/* 47 */     return this.files;
/*    */   }
/*    */ 
/*    */   public static void main(String[] args) {
/*    */     try {
/* 52 */       FillePaths filePath = new FillePaths();
/* 53 */       Files.walkFileTree(Paths.get("C:\\work\\TG", new String[0]), filePath);
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 60 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\yunsheng.ma\Desktop\findKeyWordsFromExcels-1.0.0.jar
 * Qualified Name:     com.foxdream.utils.FillePaths
 * JD-Core Version:    0.6.0
 */