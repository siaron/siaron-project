package script

public class GroovyClassLoaderDemo {

    String sayHello(String name, String sex, int age) {
        println 'GroovyShell_2 的sayHello(String name, String sex, int age)方法'

        return "name: " + name + ", sex: " + sex + ", age: " + age
    }

}
