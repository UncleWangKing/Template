package com.gson;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;

public class gsonTest {

  public static void main(String[] args) {
    parseNoHeaderJArray();
    System.out.println("-----------------");
    parseHaveHeaderJArray();
  }

  private static void parseNoHeaderJArray() {
    //拿到本地JSON 并转成String
    String strByJson = "[\n"
        + "  {\n"
        + "    \"name\": \"zhangsan\",\n"
        + "    \"age\": \"10\",\n"
        + "    \"phone\": \"11111\",\n"
        + "    \"email\": \"11111@11.com\"\n"
        + "  },\n"
        + "  {\n"
        + "    \"name\": \"lisi\",\n"
        + "    \"age\": \"20\",\n"
        + "    \"phone\": \"22222\",\n"
        + "    \"email\": \"22222@22.com\"\n"
        + "  }]";

    //Json的解析类对象
    JsonParser parser = new JsonParser();
    //将JSON的String 转成一个JsonArray对象
    JsonArray jsonArray = parser.parse(strByJson).getAsJsonArray();

    Gson gson = new Gson();
    ArrayList<UserBean> userBeanList = new ArrayList<>();

    //加强for循环遍历JsonArray
    for (JsonElement user : jsonArray) {
      //使用GSON，直接转成Bean对象
      UserBean userBean = gson.fromJson(user, UserBean.class);
      userBeanList.add(userBean);
    }

    System.out.println(userBeanList);
  }

  private static void parseHaveHeaderJArray() {
    //拿到本地JSON 并转成String
    String strByJson = "{\n"
        + "  \"muser\": [\n"
        + "    {\n"
        + "      \"name\": \"zhangsan\",\n"
        + "      \"age\": \"10\",\n"
        + "      \"phone\": \"11111\",\n"
        + "      \"email\": \"11111@11.com\"\n"
        + "    },\n"
        + "    {\n"
        + "      \"name\": \"lisi\",\n"
        + "      \"age\": \"20\",\n"
        + "      \"phone\": \"22222\",\n"
        + "      \"email\": \"22222@22.com\"\n"
        + "    }\n"
        + "  ]\n"
        + "}";

    //先转JsonObject
    JsonObject jsonObject = new JsonParser().parse(strByJson).getAsJsonObject();

    //再转JsonArray 加上数据头
    JsonArray jsonArray = jsonObject.getAsJsonArray("muser");
//    JsonArray jsonElements = new JsonArray();

    Gson gson = new Gson();
    ArrayList<UserBean> userBeanList = new ArrayList<>();
    //循环遍历
    for (JsonElement user : jsonArray) {
      //通过反射 得到UserBean.class
      System.out.println(user.toString());
      System.out.println(new Gson().toJson(user));
      UserBean userBean = gson.fromJson(user, new TypeToken<UserBean>() {}.getType());
      userBeanList.add(userBean);
    }
    System.out.println(userBeanList);
  }
}
