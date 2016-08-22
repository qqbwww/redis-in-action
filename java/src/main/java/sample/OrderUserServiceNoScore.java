package sample;

import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * Created by qianqinbo on 16/8/22.
 */
public class OrderUserServiceNoScore {

    public static final Jedis conn = new Jedis("localhost");

    public void addOnlineUser(String keyName,String key){
        conn.sadd("online_users",key);
    }

    public void isOnline(String keyName,String key){
        conn.sismember(keyName,key);
    }

    public long count(String keyName){
        return conn.scard(keyName);
    }

    public long countOneWeekOnline(String keyName,String... keys){
        return conn.sinterstore(keyName,keys);
    }

    public Set<String> oneWeekOnline(String keyName,String keys){
        return conn.sinter(keyName,keys);
    }

    //今天上线昨天没上线
    public Set<String> todayOnline(){
        return conn.sdiff("today_online_users","yesterday_online_users");
    }

    /**
     * 工作日上线了,但是假日没上线
     * @return
     */
    public Set<String> workDayOnline(){
        conn.sinterstore("weekday_online_users","monday_online_users","tuesday_online_users",
                "wednesday_online_users","thursday_online_users","friday_online_users");
        conn.sinterstore("holiday_online_users","saturday_online_users","sunday_online_users");
        return conn.sdiff("weekday_online_users","holiday_online_users");
    }
}
