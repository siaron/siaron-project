### 动态执行groovy代码有3种方式
 - GroovyShell：GroovyShell允许在Java类中（甚至Groovy类）求任意Groovy表达式的值。您可使用Binding对象输入参数给表达式，并最终通过GroovyShell返回Groovy表达式的计算结果。
 - GroovyClassLoader：用 Groovy 的 GroovyClassLoader ，动态地加载一个脚本并执行它的行为。GroovyClassLoader是一个定制的类装载器，负责解释加载Java类中用到的Groovy类。
 - GroovyScriptEngine：GroovyShell多用于推求对立的脚本或表达式，如果换成相互关联的多个脚本，使用GroovyScriptEngine会更好些。GroovyScriptEngine从您指定的位置（文件系统，URL，数据库，等等）加载Groovy脚本，并且随着脚本变化而重新加载它们。如同GroovyShell一样，GroovyScriptEngine也允许您传入参数值，并能返回脚本的值。
- http://loveshisong.cn/%E7%BC%96%E7%A8%8B%E6%8A%80%E6%9C%AF/2016-08-14-Drools%E7%AE%80%E5%8D%95%E4%BD%BF%E7%94%A8%E7%A4%BA%E4%BE%8B.html
- https://blog.csdn.net/wo541075754/article/category/6397798
- https://blog.csdn.net/han447227659/article/details/80911982
- https://blog.csdn.net/qq_31179577/article/details/77008249
- https://segmentfault.com/a/1190000016585971
- https://www.cnblogs.com/chenzhao/archive/2013/01/11/2856146.html
- http://archive.keyllo.com/L-%E7%BC%96%E7%A8%8B/drools-%E4%BB%8EDrools%E8%A7%84%E5%88%99%E5%BC%95%E6%93%8E%E5%88%B0%E9%A3%8E%E6%8E%A7%E5%8F%8D%E6%B4%97%E9%92%B1%E7%B3%BB%E7%BB%9Fv0.3.2.pdf
```
agenda-group "B to C"
auto-focus true
```