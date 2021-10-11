/*    */ package com.foxdream.utils;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ 
/*    */ public class GrepUtils
/*    */ {
/*    */   public static String getGrepReult(String content, String pattern)
/*    */   {
/*  9 */     Pattern p = Pattern.compile(pattern);
/* 10 */     Matcher match = p.matcher(content);
/*    */ 
/* 12 */     if (match.find())
/*    */     {
/* 14 */       return content;
/*    */     }
/* 16 */     return null;
/*    */   }
/*    */ 
/*    */   public static void main(String[] args)
/*    */   {
/* 22 */     System.out.println(getGrepReult("正式BILL_id我爱天", ".*((?i)\\Wbillid\\W|\\Wbill_id\\W)"));
/*    */   }
/*    */ }

/* Location:           C:\Users\yunsheng.ma\Desktop\findKeyWordsFromExcels-1.0.0.jar
 * Qualified Name:     com.foxdream.utils.GrepUtils
 * JD-Core Version:    0.6.0
 */