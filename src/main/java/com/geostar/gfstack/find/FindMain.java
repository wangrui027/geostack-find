package com.geostar.gfstack.find;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class FindMain {

    private static final String DEFAULT_ENCODING = "UTF-8";

    public static void main(String[] args) throws IOException {
        if (args.length >= 3) {
            String[] directoryArr = args[0].split(",");
            String[] extensions = args[1].split(",");
            String text = args[2];
            String encoding = DEFAULT_ENCODING;
            if (args.length >= 4) {
                encoding = args[3];
            }
//            String[] directoryArr = new String[]{"C:\\IDEA_WS"};
//            String[] extensions = new String[]{"java"};
//            String text = "static final";
            System.out.println("文件搜索开始");
            long start = System.currentTimeMillis();
            List<File> list = find(directoryArr, extensions, text, encoding);
            System.out.println("文件搜索结束");
            long end = System.currentTimeMillis();
            System.out.println(String.format("本次搜索结果%s条，耗时%s毫秒", list.size(), end - start));
            System.out.println("文件列表如下：");
            for (File file : list) {
                System.out.println(file.getPath());
            }
        } else {
            System.out.println("本jar包实现从指定路径指定扩展名的文件中搜索指定文本片段");
            System.out.println("参数一：搜索路径，如从多个路径中搜索，请使用英文逗号间隔");
            System.out.println("参数二：搜索文件的扩展名，如有多个扩展名，请使用英文逗号间隔");
            System.out.println("参数三：要搜索的文本片段");
            System.out.println("参数四：可选参数，被搜素的文件编码格式，默认为UTF-8编码");
            System.out.println("请至少输入三个参数");
        }
    }

    private static List<File> find(String[] directoryArr, String[] extensions, String text, String encoding) throws IOException {
        Set<String> set = new HashSet<>();
        for (String directory : directoryArr) {
            set.add(directory);
        }
        Set<File> fileSet = new HashSet<>();
        for (String directory : set) {
            Iterator<File> iterator = FileUtils.iterateFiles(new File(directory), extensions, true);
            while (iterator.hasNext()) {
                File file = iterator.next();
                String content = IOUtils.toString(file.toURI(), encoding);
                if (content.contains(text)) {
                    fileSet.add(file);
                }
            }
        }
        List<File> list = new ArrayList<>(fileSet);
        Collections.sort(list);
        return list;
    }

}
