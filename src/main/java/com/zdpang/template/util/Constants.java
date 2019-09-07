package com.zdpang.template.util;

public class Constants {
  public enum MessageType{
    SINGLE_TARGET(0, "单个目标"),
    BROAD_CAST(1, "广播");

    private int val;
    private String text;
    MessageType(int val,String text){this.val=val;this.text=text;}
    public int getVal(){return val;}
    public String getText(){return text;}

    public static String getText(int val){
      MessageType[] values = MessageType.values();
      for (MessageType type : values) {
        if(type.val == val){
          return type.text;
        }
      }
      return null;
    }
  }

  public enum MessageStatus{
    UNREAD(0, "未读"),
    READ(1, "已读"),
    CLIENT_DELETE(2, "用户删除"),
    ADMIN_DELETE(3, "管理员删除");

    private int val;
    private String text;
    MessageStatus(int val,String text){this.val=val;this.text=text;}
    public int getVal(){return val;}
    public String getText(){return text;}

    public static String getText(int val){
      MessageStatus[] values = MessageStatus.values();
      for (MessageStatus type : values) {
        if(type.val == val){
          return type.text;
        }
      }
      return null;
    }

    public static boolean containts(int value){
      MessageStatus[] values = MessageStatus.values();
      for (MessageStatus type : values) {
        if(type.val == value){
          return true;
        }
      }
      return false;
    }
  }
}
