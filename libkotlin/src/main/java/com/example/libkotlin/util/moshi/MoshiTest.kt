package com.example.main.util.moshi

import com.example.libkotlin.util.moshi.JStudent
import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory


object MoshiTest {
    val TAG = "MoshiTest"
    fun init() {
        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        println("---------------bean------------------------")
        //val moshi = Moshi.Builder().build()
        //val adapter = moshi.adapter(JStudent::class.java) //JStudent: java类型的student
        val adapter = moshi.adapter(JStudent::class.java)
        var strStudent = "{\n" +
                "  \"name\": \"xiao li\",\n" +
                "  \"age\": 13\n" +
                "}"
        val student = adapter.fromJson(strStudent)
        println("student ="+student)
        strStudent = adapter.toJson(student)
        println("strStudent ="+strStudent)

        //-------list-------
        println("---------------list------------------------")
        strStudent = "[{\n" +
                "  \"name\": \"xiao li1\",\n" +
                "  \"age\": 13\n" +
                "},\n" +
                "{\n" +
                "  \"name\": \"xiao li2\",\n" +
                "  \"age\": 14\n" +
                "},\n" +
                "{\n" +
                "  \"name\": \"xiao li3\",\n" +
                "  \"age\": 15\n" +
                "}]"
        var listOfCardsType = Types.newParameterizedType(List::class.java, Student::class.java)
        var jsonAdapter = moshi.adapter<List<Student>>(listOfCardsType)
        var students = jsonAdapter.fromJson(strStudent)
        println("students ="+students)
        strStudent = jsonAdapter.toJson(students)
        println("strStudents ="+strStudent)

        println("---------------map------------------------")
        strStudent = "{\"0\":{\"name\":\"xiaoli1\",\"age\":13},\"1\":{\"name\":\"xiaoli2\",\"age\":14},\"2\":{\"name\":\"xiaoli3\",\"age\":15}}"

        var mapType = Types.newParameterizedType(Map::class.java, Integer::class.java, Student::class.java)
        var jsonAdapter2: JsonAdapter<Map<Int, Student>> = moshi.adapter(mapType)
        val studentMap = jsonAdapter2.fromJson(strStudent)
        println("studentMap ="+studentMap)
/*        val map = HashMap<Int, Student>()
        map.put(0, Student("xiaoli1", 13))
        map.put(1, Student("xiaoli2", 14))
        map.put(2, Student("xiaoli3", 15))*/
        strStudent = jsonAdapter2.toJson(studentMap)
        println("strStudents ="+strStudent)

        println("---------------other------------------------")

        val mapType3 = Types.newParameterizedType(Map::class.java, Integer::class.java, Teacher::class.java)
        val jsonAdapter3: JsonAdapter<Map<Int, Teacher>> = moshi.adapter(mapType3)
        //val studentMap = jsonAdapter3.fromJson(strStudent)
        //println("studentMap ="+studentMap)
        val map = HashMap<Int, Teacher>()
        map.put(0, Teacher("xiaoli1", 13))
        map.put(1, Teacher("xiaoli2", 14))
        map.put(2, Teacher("xiaoli3", 15))
        val strTeacher = jsonAdapter3.toJson(map)
        println("strTeacher ="+strTeacher)

        println("---------------moshi-kotlin-codegen------------------------")
        val jsonCar = "{\n" +
                "  \"brand\": \"一汽大众\"\n" +
                "}"
        val adapterCar = moshi.adapter(Car::class.java)
        var car = adapterCar.fromJson(jsonCar)
        println("car ="+car)
        car = Car("梅赛德斯-奔驰")
        val strCar = adapterCar.toJson(car)
        println("strCar ="+strCar)
    }

    /**
     * 因Student类是kotlin类，所以在实例化moshi时要加上add(KotlinJsonAdapterFactory()),否则序列化kotlin的Bean
     * 对象时会报如下错误：
     * Cannot serialize Kotlin type com.example.main.util.moshi.MoshiTest$Student.
     * Reflective serialization of Kotlin classes without using kotlin-reflect has undefined
     * and unexpected behavior. Please use KotlinJsonAdapterFactory from the moshi-kotlin artifact
     * or use code gen from the moshi-kotlin-codegen artifact.
     * 而序列化Java对象时不需要加add(KotlinJsonAdapterFactory());
     * 使用val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()方式，即加了KotlinJsonAdapterFactory()
     * 即可以序列化java Bean,也可以序列化kotlin Bean
     *
     * package com.example.libkotlin.util.moshi;
     *
     * public class JStudent {
     *     private String name;
     *     private int age;
     *
     *     public JStudent(String name, int age) {
     *         this.name = name;
     *         this.age = age;
     *     }
     * }
     *
     */

    data class Student(val name: String, val age: Int)

    /**
     * @Json: key转换
     * transitent: 跳过该字段不解析
     */
    data class Teacher(
        @Json(name = "name") var teacherName: String = "",
        @Transient var age: Int = 0,
    )

    @JsonClass(generateAdapter = true)
    data class Car(@Json(name = "brand") var brandName: String = "")
}

fun main (){
    MoshiTest.init()
}
