package com.nbcb.thinkingInJava.io.serialization;

import java.io.Serializable;


/**
 * 这三个文件是一个主题
 * Alien.java
 * FreezeAlien.java
 * ThawAlient.java
 *
 * 其中
 * Alien是要序列化的对象
 * FreezeAlien将Alient序列化
 * ThawAlient从序列化文件中读取对象
 * 当然，ThawAlient读取到对象后，是不知道对象类型的
 */
public class Alien implements Serializable{
}
