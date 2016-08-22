package sample;

import redis.clients.jedis.Jedis;

/**
 * Created by qianqinbo on 16/8/22.
 * 使用位图记录用户登录
 */
public class OrderUserServiceBit {

    public static final Jedis conn = new Jedis("localhost");


    public void online(String keyName,Long memberId){
        conn.setbit(keyName,memberId,true);
    }

    public void isOnline(String keyName,Long memberId){
        conn.getbit(keyName,memberId);
    }

//    public long countOnline(String keyName){
////        return conn.bit
//    }


    public static void main(String[] args) {

    }
}
