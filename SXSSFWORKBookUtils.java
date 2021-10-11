/*     */ package com.foxdream.utils;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.OpenOption;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.nio.file.StandardOpenOption;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.poi.ss.usermodel.Cell;
/*     */ import org.apache.poi.ss.usermodel.Row;
/*     */ import org.apache.poi.ss.usermodel.Sheet;
/*     */ import org.apache.poi.ss.usermodel.Workbook;
/*     */ import org.apache.poi.xssf.usermodel.XSSFCellStyle;
/*     */ import org.apache.poi.xssf.usermodel.XSSFFont;
/*     */ 
/*     */ public class SXSSFWORKBookUtils
/*     */ {
/*  23 */   private static final Logger LOGGER = LogManager.getLogger(SXSSFWORKBookUtils.class.getName());
/*  24 */   private static String SHEET_NAME = "\tシート名:";
/*     */ 
/*     */   private List<String> grepKeywords() {
/*  27 */     List keyWords = KeyWordsRead.getKeyWords();
/*  28 */     List keys = new ArrayList();
/*  29 */     for (String keyword : keyWords) {
/*  30 */       StringBuffer grepContent = new StringBuffer();
/*  31 */       grepContent.append(".*((?i)");
/*  32 */       if (keyword.matches("\\w*")) {
/*  33 */         grepContent.append("\\W");
/*  34 */         grepContent.append(keyword);
/*  35 */         grepContent.append("\\W");
/*     */       } else {
/*  37 */         grepContent.append(keyword);
/*     */       }
/*  39 */       grepContent.append(")");
/*  40 */       keys.add(grepContent.toString());
/*  41 */       grepContent = null;
/*     */     }
/*  43 */     return keys;
/*     */   }
/*     */ 
/*     */   public boolean mainProcess(String folderPath, String pathOut) {
/*  47 */     boolean sucess = true;
/*  48 */     List keys = grepKeywords();
/*  49 */     String outPutPath = pathOut;
/*  50 */     String resultPath = Paths.get(outPutPath, new String[] { System.currentTimeMillis() + "-result.txt" }).toString();
/*  51 */     List contents = new ArrayList();
/*  52 */     FillePaths filePath = new FillePaths();
/*     */     try {
/*  54 */       Files.walkFileTree(Paths.get(folderPath, new String[0]), filePath);
/*     */     } catch (IOException e1) {
/*  56 */       e1.printStackTrace();
/*     */     }
/*  58 */     for (Path path : filePath.getFiles()) {
/*  59 */       LOGGER.info("ファイル：" + path.toString() + "処理開始します。");
/*  60 */       boolean found = false;
/*     */       try
/*     */       {
/*  63 */         Workbook workbook = Decryption.getDecryption(path.toString());
/*  64 */         int countOfSheets = workbook.getNumberOfSheets();
/*  65 */         for (int index = 0; index < countOfSheets; index++) {
/*  66 */           Sheet sheet = null;
/*  67 */           if (workbook.isSheetHidden(index)) {
/*     */             continue;
/*     */           }
/*  70 */           sheet = workbook.getSheetAt(index);
/*     */ 
/*  72 */           int countOfRows = sheet.getLastRowNum();
/*  73 */           for (int rowIndex = 0; rowIndex < countOfRows; rowIndex++) {
/*  74 */             Row row = sheet.getRow(rowIndex);
/*  75 */             if ((row == null) || (row.getZeroHeight())) {
/*     */               continue;
/*     */             }
/*  78 */             int countOfCells = row.getLastCellNum();
/*  79 */             for (int cellIndex = 0; cellIndex < countOfCells; cellIndex++) {
/*  80 */               Cell cell = row.getCell(cellIndex);
/*  81 */               if (cell == null) {
/*     */                 continue;
/*     */               }
/*  84 */               XSSFCellStyle cellStyle = (XSSFCellStyle)cell.getCellStyle();
/*  85 */               if ((cellStyle.getHidden()) || (cellStyle.getFont().getStrikeout())) {
/*     */                 continue;
/*     */               }
/*  88 */               StringBuffer rowContents = new StringBuffer();
/*  89 */               switch ($SWITCH_TABLE$org$apache$poi$ss$usermodel$CellType()[cell.getCellType().ordinal()]) {
/*     */               case 3:
/*  91 */                 rowContents.append("\"");
/*  92 */                 rowContents.append(cell.getStringCellValue().replaceAll("\"", "'"));
/*     */ 
/*  94 */                 rowContents.append("\"");
/*  95 */                 break;
/*     */               }
/*     */ 
/*  99 */               for (String key : keys) {
/* 100 */                 String result = GrepUtils.getGrepReult(rowContents.toString(), key);
/* 101 */                 if (result != null) {
/* 102 */                   contents.add(key + "\t" + path.toString() + SHEET_NAME + sheet.getSheetName() + 
/* 103 */                     "(行" + (rowIndex + 1) + "," + "列" + (cellIndex + 1) + ")\t" + 
/* 104 */                     rowContents.toString());
/* 105 */                   found = true;
/*     */                 }
/* 107 */                 result = null;
/*     */               }
/* 109 */               rowContents = null;
/*     */             }
/*     */           }
/*     */         }
/*     */       } catch (Exception e) {
/* 114 */         LOGGER.error("対象ファイル：「" + path.toString() + "」を開く時にエラーがありGrepされていません。");
/* 115 */         sucess = false;
/* 116 */         continue;
/*     */       }
/*     */       Workbook workbook;
/* 118 */       if (!found)
/*     */       {
/* 120 */         contents.add(path.toString() + "\t：0件");
/*     */       }
/* 122 */       else found = false;
/*     */ 
/* 124 */       writeSmallTextFile(contents, resultPath);
/* 125 */       contents.clear();
/* 126 */       LOGGER.info("ファイル：" + path.toString() + "処理完了しました。");
/*     */     }
/* 128 */     contents = null;
/* 129 */     return sucess;
/*     */   }
/*     */   private void writeSmallTextFile(List<String> aLines, String aFileName) {
/* 132 */     Path path = Paths.get(aFileName, new String[0]);
/*     */     try {
/* 134 */       System.out.println(path);
/* 135 */       Files.write(path, aLines, StandardCharsets.UTF_8, new OpenOption[] { StandardOpenOption.CREATE, StandardOpenOption.APPEND });
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 139 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\yunsheng.ma\Desktop\findKeyWordsFromExcels-1.0.0.jar
 * Qualified Name:     com.foxdream.utils.SXSSFWORKBookUtils
 * JD-Core Version:    0.6.0
 */