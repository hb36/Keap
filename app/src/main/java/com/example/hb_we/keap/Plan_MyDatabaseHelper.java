package com.example.hb_we.keap;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Windows on 2017/6/7.
 */

public class Plan_MyDatabaseHelper extends SQLiteOpenHelper {

    //创建运动表（运动id，运动名称，单组卡路里，图片id)
    public static final String CREATE_ACTIVITY = "create table Activity(" +
            "id integer primary key autoincrement," +
            "name text," +
            "k integer," +
            "pic integer," +
            "detail text)";

    //创建计划表
    public static final String CREATE_PLAN = "create table Plan(" +
            "id integer primary key autoincrement," +
            "name text," +
            "activity1 text," +
            "group1 integer," +
            "activity2 text," +
            "group2 integer," +
            "activity3 text," +
            "group3 integer," +
            "activity4 text," +
            "group4 integer," +
            "activity5 text," +
            "group5 integer," +
            "total_k integer)";

    public Context mContext;

    public Plan_MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //创建运动表
        db.execSQL(CREATE_ACTIVITY);
        Toast.makeText(mContext,"成功创建运动表",Toast.LENGTH_SHORT).show();

        //创建计划表
        db.execSQL(CREATE_PLAN);
        Toast.makeText(mContext,"成功创建计划表",Toast.LENGTH_SHORT).show();

        //为运动表添加数据
        ContentValues values = new ContentValues();

        //背部松懈
        values.put("id",1);
        values.put("name","背部松懈");
        values.put("k",10);
        values.put("pic",R.drawable.beibusongxie);
        values.put("detail","要点：\n" +
                "·双手抱住对侧肩膀，双肩充分展开\n" +
                "·低头，下颚贴紧颈部，减少颈部受力\n" +
                "呼吸：\n" +
                "·全程保持均匀呼吸\n" +
                "动作感觉：\n" +
                "·背部肌肉在泡沫轴的挤压下，有轻微疼痛和舒展感");
        db.insert("Activity",null,values);
        values.clear();


        //侧背阔肌松懈
        values.put("id",2);
        values.put("name","侧背阔肌松懈");
        values.put("k",12);
        values.put("pic",R.drawable.cebeikuojisongxie);
        values.put("detail","要点：\n" +
                "·45度侧躺，让背部侧面接触泡沫轴\n" +
                "·臀部离地，用脚推动身体前后移动\n" +
                "呼吸：\n" +
                "·全程保持均匀呼吸\n" +
                "动作感觉：\n" +
                "·背部出现轻微压痛感，部分人会有单个地方感觉特别强");
        db.insert("Activity",null,values);
        values.clear();


        //猫式伸展
        values.put("id",3);
        values.put("name","猫式伸展");
        values.put("k",9);
        values.put("pic",R.drawable.maoshishenzhan);
        values.put("detail","要点：\n" +
                "·第一阶段拱起上背部，低头\n" +
                "·第二阶段胸部下沉到最低，仰头\n" +
                "·全身放松\n" +
                "呼吸：\n" +
                "·拱起背部时呼气，塌腰时吸气\n" +
                "动作感觉：\n" +
                "·起时整条脊柱向前弯曲到产生一定挤压感，同时背部有较强牵拉感\n" +
                "·塌落时整条脊柱向后伸展腹部有较强牵拉感");
        db.insert("Activity",null,values);
        values.clear();


        //支撑抬臀
        values.put("id",4);
        values.put("name","支撑抬臀");
        values.put("k",24);
        values.put("pic",R.drawable.zhichengtaitun);
        values.put("detail","要点：\n" +
                "·肩部力量将身体向后推，臀部上移\n" +
                "·臀部上移时背部挺直，脚跟可以离地\n" +
                "呼吸：\n" +
                "·拱起时呼气，还原时吸气\n" +
                "动作感觉：\n" +
                "·拱起时，肩部产生收缩感并持续保持紧绷");
        db.insert("Activity",null,values);
        values.clear();


        //原地爬行
        values.put("id",5);
        values.put("name","原地爬行");
        values.put("k",233);
        values.put("pic",R.drawable.yuandipaxing);
        values.put("detail","要点：\n" +
                "·双腿尽可能伸直，柔韧性较差者可以屈膝\n" +
                "·用肩与手臂的力量前后爬行，下肢不参与发力\n" +
                "·身体越稳定越好\n" +
                "呼吸：\n" +
                "·全程保持均匀呼吸\n" +
                "动作感觉：\n" +
                "·核心肌群全程保持紧绷\n" +
                "·肩部全程保持紧绷");
        db.insert("Activity",null,values);
        values.clear();


        //开合跳
        values.put("id",6);
        values.put("name","开合跳");
        values.put("k",33);
        values.put("pic",R.drawable.kaihetiao);
        values.put("detail","要点：\n" +
                "·收紧腰腹，手臂用力绷紧\n" +
                "·用肩部力量抬臂，背部力量下拉手臂，用手臂带动身体的跳跃\n" +
                "·小腿尽可能放松，不可低头、仰头\n" +
                "·动作越快燃脂效果越好\n" +
                "呼吸：\n" +
                "·手臂上抬时吸气，下落时呼气\n" +
                "动作感觉：\n" +
                "·脚踝、膝盖放松，腹部始终保持紧绷\n" +
                "·整体有一定的弹性");
        db.insert("Activity",null,values);
        values.clear();


        //背部拉伸
        values.put("id",7);
        values.put("name","背部拉伸");
        values.put("k",3);
        values.put("pic",R.drawable.beibulashen);
        values.put("detail","要点\n" +
                "·腹部紧贴大腿，臀部紧贴脚后跟\n" +
                "·手臂放松尽量向前伸展，肩部下压，背部放松\n" +
                "呼吸\n" +
                "·全程保持均匀呼吸\n" +
                "动作感觉\n" +
                "·背部有明显舒展感和轻微牵拉感");
        db.insert("Activity",null,values);
        values.clear();


        //肩部拉伸
        values.put("id",8);
        values.put("name","肩部拉伸");
        values.put("k",3);
        values.put("pic",R.drawable.jianbulashen);
        values.put("detail","要点\n" +
                "·准备姿势：右手套住左臂肘关节，左肩用力下沉\n" +
                "·拉伸：右手发力将左臂向身体拉近，在牵拉感较强时，保持住\n" +
                "呼吸\n" +
                "·全程保持均匀呼吸\n" +
                "动作感觉\n" +
                "·肩部中部、后侧有牵拉感");
        db.insert("Activity",null,values);
        values.clear();


        //大腿后侧拉伸
        values.put("id",9);
        values.put("name","大腿后侧拉伸");
        values.put("k",3);
        values.put("pic",R.drawable.datuihoucelashen);
        values.put("detail","要点\n" +
                "·双腿分开大角度。微微勾起脚尖，向前俯身\n" +
                "·小幅度摆动身体\n" +
                "呼吸\n" +
                "·全程保持均匀呼吸\n" +
                "动作感觉\n" +
                "·大腿后侧有明显牵拉感");
        db.insert("Activity",null,values);
        values.clear();


        //小腿拉伸
        values.put("id",10);
        values.put("name","小腿拉伸");
        values.put("k",3);
        values.put("pic",R.drawable.xiaotuilashen);
        values.put("detail","要点\n" +
                "·脚跟着地，挺直背部，打开肩胛骨，头部与脊柱方向一致\n" +
                "呼吸\n" +
                "·全程保持均匀呼吸\n" +
                "动作感觉\n" +
                "·小腿后侧有明显牵拉感");
        db.insert("Activity",null,values);
        values.clear();


        //仰卧大腿内侧拉神
        values.put("id",11);
        values.put("name","仰卧大腿内侧拉神");
        values.put("k",6);
        values.put("pic",R.drawable.yangwodatuineicelashen);
        values.put("detail","要点\n" +
                "·一条腿伸直，手抓住小腿\n" +
                "·脚尖处于自然姿态，不可绷直或勾起\n" +
                "·另一条腿完全放松\n" +
                "呼吸\n" +
                "·呼气提腿，吸气还原\n" +
                "动作感觉\n" +
                "·大腿后侧有强烈的牵拉感");
        db.insert("Activity",null,values);
        values.clear();


        //哑铃弯举
        values.put("id",12);
        values.put("name","哑铃弯举");
        values.put("k",36);
        values.put("pic",R.drawable.yalingwanju);
        values.put("detail","要点：\n" +
                "·挺胸收腹，肩膀下沉固定\n" +
                "·发力时稍稍偏头看着肱二头肌的收缩有助于掌握本体感受\n" +
                "·上臂可以保持固定，也可以稍微上抬更多刺激手臂内侧\n" +
                "呼吸：\n" +
                "·收缩时呼气，下放时吸气\n" +
                "动作感觉：\n" +
                "·发力时，上臂前侧有明显收缩感\n" +
                "·哑铃重量较大时，小臂前侧全程有较强紧绷感");
        db.insert("Activity",null,values);
        values.clear();


        //哑铃颈后臂屈伸
        values.put("id",13);
        values.put("name","哑铃颈后臂屈伸");
        values.put("k",34);
        values.put("pic",R.drawable.yalingjinghoubiqushen);
        values.put("detail","要点：\n" +
                "·挺胸，上臂垂直于地面\n" +
                "·将两只哑铃再颈后并拢，形成一个整体\n" +
                "·利用手臂后侧肌肉发力将哑铃上举\n" +
                "呼吸：\n" +
                "·上举哑铃呼气，下放呼气\n" +
                "动作感觉\n" +
                "·上举时，上臂后侧有明显收缩感\n" +
                "·下放到最低点，上臂右侧有轻微牵拉感，但还是保持一定紧绷感，不是完全放松");
        db.insert("Activity",null,values);
        values.clear();


        //动态平板支撑
        values.put("id",14);
        values.put("name","动态平板支撑");
        values.put("k",33);
        values.put("pic",R.drawable.dongtaipingbanzhicheng);
        values.put("detail","要点\n" +
                "·收紧核心，背部挺直，全身保持稳定状态，尽力减少左右晃动的幅度\n" +
                "·双脚分开可以降低难度\n" +
                "呼吸：\n" +
                "·俯身时吸气，推起时呼气\n" +
                "动作感觉\n" +
                "·腹部始终有紧绷感\n" +
                "·推起时，肩、臂收缩发力明显，同时侧腹紧绷感加强");
        db.insert("Activity",null,values);
        values.clear();


        //仰卧屈膝后撑
        values.put("id",15);
        values.put("name","仰卧屈膝后撑");
        values.put("k",30);
        values.put("pic",R.drawable.yangwoquxihoucheng);
        values.put("detail","要点：\n" +
                "·双腿弯曲，下肢放松，背部紧贴椅沿上下移动\n" +
                "·绷紧肩部，手臂后侧发力推起身体\n" +
                "呼吸：\n" +
                "·下降时吸气，还原时呼气\n" +
                "动作感觉\n" +
                "·撑起时，上臂后侧有明显的收缩感\n" +
                "·肩部全程保持紧绷");
        db.insert("Activity",null,values);
        values.clear();


        //钻石俯卧撑
        values.put("id",16);
        values.put("name","钻石俯卧撑");
        values.put("k",42);
        values.put("pic",R.drawable.zuanshifuwocheng);
        values.put("detail","要点：\n" +
                "·双手撑于胸肌正下方\n" +
                "·下落时手肘朝斜后方打开\n" +
                "·手臂用力朝内夹，将身体推起\n" +
                "呼吸：\n" +
                "·下落时吸气，推起时憋气，推起后呼气\n" +
                "动作感觉\n" +
                "·推起时，上臂向内夹，胸部和上臂后侧有明显收缩感\n" +
                "·在最高点，胸部有强挤压感\n" +
                "·肩部全程保持紧绷");
        db.insert("Activity",null,values);
        values.clear();


        //弹力带弯举
        values.put("id",17);
        values.put("name","弹力带弯举");
        values.put("k",36);
        values.put("pic",R.drawable.tanlidaiwanju);
        values.put("detail","要点：\n" +
                "·身体垂直于地面\n" +
                "·手肘弯曲至45度左右，不要完全弯屈\n" +
                "·上臂贴紧身侧固定\n" +
                "呼吸：\n" +
                "·抬臂呼气，回放吸气\n" +
                "动作感觉\n" +
                "·抬臂时，上臂前侧有明显收缩感");
        db.insert("Activity",null,values);
        values.clear();


        //弹力带平举
        values.put("id",18);
        values.put("name","弹力带平举");
        values.put("k",33);
        values.put("pic",R.drawable.tanlidaipingju);
        values.put("detail","要点：\n" +
                "·身体垂直于地面\n" +
                "·手臂完全伸直\n" +
                "·侧向抬起至90度\n" +
                "呼吸：\n" +
                "·抬臂呼气，回放吸气\n" +
                "动作感觉\n" +
                "·抬臂时，上臂前侧有明显收缩感");
        db.insert("Activity",null,values);
        values.clear();


        //臀桥
        values.put("id",19);
        values.put("name","臀桥");
        values.put("k",24);
        values.put("pic",R.drawable.tunqiao);
        values.put("detail","要点：\n" +
                "·臀部抬起时上背部支撑地面\n" +
                "·下落时下背部贴地，但臀部悬空\n" +
                "·双脚脚跟比肩略宽，臀部抬起时膝关节与身体撑0度角\n" +
                "呼吸：\n" +
                "·臀部抬起时呼气，臀部下落时吸气\n" +
                "动作感觉\n" +
                "·感觉臀部慢慢离开垫子\n" +
                "·到顶部后，臀部有强烈的收缩挤压感");
        db.insert("Activity",null,values);
        values.clear();


        //深蹲
        values.put("id",20);
        values.put("name","深蹲");
        values.put("k",39);
        values.put("pic",R.drawable.shendun);
        values.put("detail","要点：\n" +
                "·双手抱拳贴于胸口，手肘向下\n" +
                "·脚跟与肩同宽，下蹲时手肘触碰膝盖前侧\n" +
                "·保持腰背挺直，臀部主导发力\n" +
                "呼吸：\n" +
                "·下蹲时吸气，蹲起时吸气\n" +
                "动作感觉\n" +
                "·下蹲时，臀部和大腿前侧有轻微牵拉感\n" +
                "·蹲起时，臀部和大腿前收缩发力，臀部更加明显");
        db.insert("Activity",null,values);
        values.clear();


        //后箭步蹲
        values.put("id",21);
        values.put("name","后箭步蹲");
        values.put("k",27);
        values.put("pic",R.drawable.houjianbudun);
        values.put("detail","要点：\n" +
                "·绷紧上半身，挺直背部\n" +
                "·下蹲时身体保持垂直于地面，后撤腿尽量蹲到最低但膝盖不触地，两膝均呈90度，重心均匀分布在两腿中间\n" +
                "呼吸：\n" +
                "·身体下蹲时吸气，起身时呼气\n" +
                "动作感觉\n" +
                "·蹲起时，前侧臀部及大腿前侧有收缩发力感，臀部更加明显");
        db.insert("Activity",null,values);
        values.clear();


        //保加利亚深蹲
        values.put("id",22);
        values.put("name","保加利亚深蹲");
        values.put("k",33);
        values.put("pic",R.drawable.baojialiyashendun);
        values.put("detail","要点：\n" +
                "·脚后跟搭在30-40厘米高的椅子或沙发上，尽可能放松\n" +
                "·背部挺直，脚后跟发力蹲起\n" +
                "·下蹲时略微俯身，膝盖不超过脚尖\n" +
                "·如果站不稳，可以搀扶着桌子进行\n" +
                "呼吸：\n" +
                "·下蹲吸气，蹲起呼气\n" +
                "动作感觉：\n" +
                "·尽量去找臀部的收缩感");
        db.insert("Activity",null,values);
        values.clear();


        //侧弓步
        values.put("id",23);
        values.put("name","侧弓步");
        values.put("k",24);
        values.put("pic",R.drawable.cegongbu);
        values.put("detail","要点：\n" +
                "·双脚约两倍肩宽，脚尖向前\n" +
                "·下蹲时腰背挺直\n" +
                "·膝盖与脚尖方向一致，臀部发力蹲起\n" +
                "呼吸：\n" +
                "·下蹲吸气，蹲起呼气\n" +
                "动作感觉：\n" +
                "·蹲下时，大腿内侧有轻微牵拉感\n" +
                "·蹲起时，大腿内侧有明显收缩感");
        db.insert("Activity",null,values);
        values.clear();


        //仰卧开合腿
        values.put("id",24);
        values.put("name","仰卧开合腿");
        values.put("k",30);
        values.put("pic",R.drawable.yangwokaihetui);
        values.put("detail","要点：\n" +
                "·臀部贴地，大腿抬起\n" +
                "·双腿张开至最大幅度，腿内侧发力夹腿\n" +
                "呼吸：\n" +
                "·夹腿时呼气，下放时吸气\n" +
                "动作感觉：\n" +
                "·在最低处大腿内侧有牵拉感\n" +
                "·在抬起过程中大腿内侧有一定发力感");
        db.insert("Activity",null,values);
        values.clear();


        //箭步蹲跳
        values.put("id",25);
        values.put("name","箭步蹲跳");
        values.put("k",42);
        values.put("pic",R.drawable.jianbuduntiao);
        values.put("detail","要点：\n" +
                "·上半身与地面垂直，下蹲时双膝均呈90度\n" +
                "·双手用力上摆来帮助身体起跳，在空中迅速换腿，膝盖不能触地\n" +
                "呼吸：\n" +
                "·起跳时吸气，下落时呼气\n" +
                "动作感觉：\n" +
                "·臀部和大腿发力感明显\n" +
                "·多次跳跃后大腿和臀部有酸胀感");
        db.insert("Activity",null,values);
        values.clear();


        //登山跑
        values.put("id",26);
        values.put("name","登山跑");
        values.put("k",42);
        values.put("pic",R.drawable.dengshanpao);
        values.put("detail","要点：\n" +
                "·手肘微屈，上身放平，尽可能降低身体贴近地面\n" +
                "·用最快速度交替提膝，膝盖往胸部靠近\n" +
                "·用腹部的力量将大腿想前提\n" +
                "动作感觉：\n" +
                "·肩部全程有紧绷感，膝盖和脚踝是放松状态\n" +
                "·抬腿时，腹肌收缩发力");
        db.insert("Activity",null,values);
        values.clear();


        //俄罗斯转体
        values.put("id",27);
        values.put("name","俄罗斯转体");
        values.put("k",39);
        values.put("pic",R.drawable.eluosizhuanti);
        values.put("detail","要点：\n" +
                "·转动双肩来带动手臂的移动\n" +
                "·下背挺直，上背略微弓起\n" +
                "·手接触身体两侧地面，目光跟随双手移动\n" +
                "呼吸：\n" +
                "·手接触地面时呼气，身体转正时吸气\n" +
                "动作感觉：\n" +
                "·整个腹部始终有紧绷感\n" +
                "·转体时，对侧侧腹出现收缩挤压感");
        db.insert("Activity",null,values);
        values.clear();


        //俯卧挺身
        values.put("id",28);
        values.put("name","俯卧挺身");
        values.put("k",30);
        values.put("pic",R.drawable.fuwotingshen);
        values.put("detail","要点：\n" +
                "·双手抱住脑后，手肘向外打开\n" +
                "·头向后顶，与双手用力对抗\n" +
                "·收腹挺起上半身，用骨盆支撑身体\n" +
                "呼吸：\n" +
                "·挺身吸气，下落呼气\n" +
                "动作感觉：\n" +
                "·挺身时，下背部产生明显收缩和挤压感，在最高点保持紧绷\n" +
                "·挺身时，臀部持续保持紧绷");
        db.insert("Activity",null,values);
        values.clear();


        //卷腹
        values.put("id",29);
        values.put("name","卷腹");
        values.put("k",33);
        values.put("pic",R.drawable.juanfu);
        values.put("detail","要点：\n" +
                "·卷腹时，将下背部用力贴紧地面\n" +
                "·卷腹背部微微用力上抬\n" +
                "·卷腹时手肘保持向外打开固定\n" +
                "呼吸：\n" +
                "·卷腹时呼气，下落时吸气\n" +
                "动作感觉：\n" +
                "·双腿放松，卷腹起身时上腹部明显收缩发力");
        db.insert("Activity",null,values);
        values.clear();


        //摸膝
        values.put("id",30);
        values.put("name","摸膝");
        values.put("k",36);
        values.put("pic",R.drawable.moxi);
        values.put("detail","要点：\n" +
                "·腰部贴地，臀部略微抬起，卷腹时重力压在腰部\n" +
                "·双手始终伸直，双摸到膝盖\n" +
                "呼吸：\n" +
                "·摸膝时呼气，下落时吸气\n" +
                "动作感觉：\n" +
                "·卷起时，腹部有明显收缩发力感，上腹更加强烈");
        db.insert("Activity",null,values);
        values.clear();


        //鸟式伸展
        values.put("id",31);
        values.put("name","鸟式伸展");
        values.put("k",33);
        values.put("pic",R.drawable.niaoshishenzhan);
        values.put("detail","要点：\n" +
                "·背部保持平直，手指抓地，抬腿时腰部不可下塌\n" +
                "·收紧腹部保持身体稳定\n" +
                "·手脚朝远处伸展\n" +
                "呼吸：\n" +
                "·伸展时吸气，回收时呼气\n" +
                "动作感觉：\n" +
                "·伸展时背部脊椎两侧和臀部的肌肉有收缩发力感\n" +
                "·收回时，腹部收缩发力，左侧腹部更加明显");
        db.insert("Activity",null,values);
        values.clear();


        //小燕飞
        values.put("id",32);
        values.put("name","小燕飞");
        values.put("k",30);
        values.put("pic",R.drawable.xiaoyanfei);
        values.put("detail","要点：\n" +
                "·双臂用力向脚的方向伸\n" +
                "·双肩向后夹紧、膝盖离地\n" +
                "·头、颈保持自然姿态，不要仰头\n" +
                "呼吸：\n" +
                "·全程保持均匀呼吸\n" +
                "动作感觉；\n" +
                "·下背部和臀部持续紧张并伴随一定挤压感");
        db.insert("Activity",null,values);
        values.clear();


        //仰卧举腿
        values.put("id",33);
        values.put("name","仰卧举腿");
        values.put("k",33);
        values.put("pic",R.drawable.yangwojutui);
        values.put("detail","要点：\n" +
                "·起始位置将大腿紧贴腹部，重心位于中背部，手指轻轻撑地\n" +
                "·下腹发力抬起臀部，小腿垂直于地面上抬\n" +
                "·身体有弹性地收缩伸展\n" +
                "呼吸：\n" +
                "·抬腿时呼气，下放时吸气\n" +
                "动作感觉：\n" +
                "·向上抬时，腹部有明显的收缩发力感，下腹部尤甚");
        db.insert("Activity",null,values);
        values.clear();


        //仰卧起坐
        values.put("id",34);
        values.put("name","仰卧起坐");
        values.put("k",42);
        values.put("pic",R.drawable.yangwoqizuo);
        values.put("detail","要点：\n" +
                "·起始动作双手脑后触地，脚底并拢\n" +
                "·身体头部、肩部、上背部、下背部依次离地\n" +
                "呼吸：\n" +
                "·起身时呼气，还原时吸气\n" +
                "动作感觉：\n" +
                "·起身时腹部有突然收缩紧绷感，在起身后半阶段腹部有挤压感\n" +
                "·腰部始终放松，不应有紧绷感");
        db.insert("Activity",null,values);
        values.clear();

        Toast.makeText(mContext,"成功添加运动",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion ,int newVersion){

    }

}
