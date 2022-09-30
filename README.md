# 文件大小优化

> 在服务器中，可能因为某些原因而堆积很多日志文件，有的没处理好可能单个日志文件几G甚至几十G。

本项目是解决日志堆积成超大文件的问题。

Linux可以使用split来解决上述场景问题，但split命令是用来分割文件的，即用了split命令还需要做后续的处理，如删除原文件、删除其他分割文件只留下一个等这些问题，让Linux命令玩不转的我有点难受，现在用Java写一版解决这类问题。

核心实现在于`RandomAccessFile`。

## 启动

`java -jar File_Size_Optimization.jar ` 可带上其他参数，如`java -jar File_Size_Optimization.jar debug=true`

| 参数名              | 参数值     | 说明                                                         |
| ------------------- | ---------- | ------------------------------------------------------------ |
| debug               | true/false | 表示debug调式，会读取resource下的配置文件并打印参数配置      |
| replaceSourceFile   | true/false | 影响默认文件处理，**如果为真则会将优化后的文件move到原文件中(被占用时会失败)** |
| preProcessViewFile  | true/false | 为真则会在处理前请求确认继续下一步                           |
| deleteProcessedFile | true/false | 为真则会删除处理过的文件                                     |



## 配置

在Jar所处于的文件夹下配置`config.properties`文件

| 参数名                 | 示例值                           | 说明                                                         |
| ---------------------- | -------------------------------- | ------------------------------------------------------------ |
| scanPath               | E:\\Pass\\File_Size_Optimization | 扫描路径                                                     |
| scanFileNamePattern    | \\w+\\.log                       | 文件名正则                                                   |
| scanFileSize           | 1GB                              | 取值范围[0,Long.MAX_VALUE] MB/GB，在取0时可以不用带上MB/GB   |
| optimizationPercentage | 90                               | 取值范围[1,99]，例如10MB的文件，优化后为1MB，取的是文件末尾百分之10的内容 |



