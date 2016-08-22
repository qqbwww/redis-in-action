package sample;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ZParams;

import java.util.Date;
import java.util.Set;

/**
 * Created by qianqinbo on 16/8/22.
 * 即记录用户,也记录时间
 *
 */
public class OnlineUserService {

    public static Jedis conn = new Jedis("localhost");

    /**
     * 上线
     */
    public void onLine(String key,String member){
//        Jedis conn = new Jedis("localhost");
        conn.zadd(key,new Date().getTime(),member);
    }


    /**
     * 是否在线
     */
    public Boolean isOnline(String key,String member){
        Double score = conn.zscore(key,member);
        if(score != null){
            System.out.println("score:"+score);
            return true;
        }else{
            return false;
        }
    }

    /**
     * 计算集合中的数量
     * @param key
     * @return
     */
    public long onlineCount(String key){
        return conn.zcard(key);
    }


    public long Day7AllOnlines(){
        ZParams params = new ZParams();
//        params.
        //七天都上线了的用户
        return conn.zinterstore("7_days_both_online_users","day_1_online_users",
                "day_2_online_users",
                "day_3_online_users",
                "day_4_online_users",
                "day_5_online_users",
                "day_6_online_users",
                "day_7_online_users");



    }

    public long sum7DayOnlines(){
        //其他总共都多少人上线
        return conn.zunionstore("7_days_both_online_users","day_1_online_users",
                "day_2_online_users",
                "day_3_online_users",
                "day_4_online_users",
                "day_5_online_users",
                "day_6_online_users",
                "day_7_online_users");
    }

    /**
     * 统计指定时间段内上线用户数量
     * @param startTimeStamp
     * @param endTimeStamp
     * @return
     */
    public long countOnlineBetween(Long startTimeStamp,Long endTimeStamp){
        return conn.zcount("online_users",startTimeStamp,endTimeStamp);
    }

    /**
     * 获取指定时间段内上线的用户名单
     * @param startTimeStamp
     * @param endTimeStamp
     * @return
     */
    public Set<String> onlineUserBetween(Long startTimeStamp, Long endTimeStamp){
        return conn.zrangeByScore("online_users",startTimeStamp,endTimeStamp);
    }


    public static void main(String[] args) {
        OnlineUserService service = new OnlineUserService();
        service.onLine("online_users","1");
        service.onLine("online_users","2");
        service.onLine("online_users","3");
        service.onLine("online_users","4");
        service.onLine("online_users","5");

        System.out.println(service.isOnline("online_users","1"));
        System.out.println(service.isOnline("online_users","2"));
        System.out.println(service.isOnline("online_users","3"));
        System.out.println(service.isOnline("online_users","4"));
        System.out.println(service.isOnline("online_users","5"));
        System.out.println(service.onlineCount("online_users"));
    }


}
