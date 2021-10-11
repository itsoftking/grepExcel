/*    */ package com.foxdream.utils;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.security.GeneralSecurityException;
/*    */ import org.apache.poi.hssf.record.crypto.Biff8EncryptionKey;
/*    */ import org.apache.poi.openxml4j.util.ZipSecureFile;
/*    */ import org.apache.poi.poifs.crypt.Decryptor;
/*    */ import org.apache.poi.poifs.crypt.EncryptionInfo;
/*    */ import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
/*    */ import org.apache.poi.poifs.filesystem.POIFSFileSystem;
/*    */ import org.apache.poi.ss.usermodel.Workbook;
/*    */ import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/*    */ 
/*    */ public class Decryption
/*    */ {
/* 19 */   public static String password = "kanden";
/*    */ 
/* 21 */   public static Workbook getDecryption(String file) throws IOException, GeneralSecurityException, OfficeXmlFileException { XSSFWorkbook workbook = null;
/* 22 */     InputStream fs = new FileInputStream(new File(file));
/* 23 */     if (file.startsWith("xls")) {
/* 24 */       Biff8EncryptionKey.setCurrentUserPassword(password);
/*    */     }
/*    */     else
/*    */     {
/* 28 */       POIFSFileSystem pfs = new POIFSFileSystem(fs);
/* 29 */       EncryptionInfo encInfo = new EncryptionInfo(pfs);
/* 30 */       Decryptor decryptor = Decryptor.getInstance(encInfo);
/* 31 */       decryptor.verifyPassword(password);
/*    */ 
/* 34 */       ZipSecureFile.setMinInflateRatio(-1.0D);
/* 35 */       workbook = new XSSFWorkbook(decryptor.getDataStream(pfs));
/*    */     }
/*    */ 
/* 40 */     return workbook;
/*    */   }
/*    */ }

/* Location:           C:\Users\yunsheng.ma\Desktop\findKeyWordsFromExcels-1.0.0.jar
 * Qualified Name:     com.foxdream.utils.Decryption
 * JD-Core Version:    0.6.0
 */