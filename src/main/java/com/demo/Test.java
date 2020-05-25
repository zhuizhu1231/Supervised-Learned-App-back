package com.demo;

import com.alibaba.fastjson.JSON;

import java.util.*;

public class Test {

    public static void main(String[] args) {
        HashMap<String, List<GuangGao>> area=new HashMap<>();
        List<GuangGao> GuangGaoList=new ArrayList<>();
        GuangGaoList.add(new GuangGao());//加入可供选择的国内地区广告
        GuangGaoList.add(new GuangGao());
        area.put("国内",GuangGaoList);
        List<GuangGao> GuangGaoWaiList=new ArrayList<>();
        GuangGaoWaiList.add(new GuangGao());//加入可供选择的国外地区广告
        GuangGaoWaiList.add(new GuangGao());
        area.put("国外",GuangGaoWaiList);
        //一共有多少各地区和地区下多少个广告  系统初始定义

      //用户投放地区的广告对象
        userOrder order = new userOrder();
        HashMap<String, Integer> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("国内",5);
        order.setArea(objectObjectHashMap);
        String s = JSON.toJSONString(order);//用户投放地区的广告对象转换长json
        System.out.println(s);
        userOrder order1 = JSON.parseObject(s, userOrder.class);//json解析用户投放地区的广告对象
        System.out.println(order1);
        for(Map.Entry<String, List<GuangGao>> a:area.entrySet()){//遍历系统初始的定义
            String key = a.getKey();
            HashMap<String, Integer> area1 = order1.getArea();
            if(area1.get(a.getKey())!=null&& area1.get(a.getKey())>0){//判断用户广告是否存在地区，存在显示广告
                //
                System.out.println("该用户投放了"+a.getKey()+area1.get(a.getKey())+"个广告，下面显示广告：");
                 List<GuangGao> value = a.getValue();//遍历广告显示  让用户选择
             };
        }
    }




}
class GuangGao{

}
class userOrder{
    HashMap<String, Integer> data=new HashMap<>();//String  地区   Integer投放广告数

    public HashMap<String, Integer> getArea() {
        return data;
    }

    public void setArea(HashMap<String, Integer> area) {
        this.data = area;
    }

    @Override
    public String toString() {
      StringBuilder stringBuilder=new StringBuilder();
        for(Map.Entry<String, Integer> a:data.entrySet()){
            stringBuilder.append(a.getKey()).append(":").append(a.getValue());
        }
        return "userOrder{" +
                "data=" + stringBuilder +
                '}';
    }
}