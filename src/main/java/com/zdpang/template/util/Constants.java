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

}
