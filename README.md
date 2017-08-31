# FilePicker
文件选择器

效果图：

![](https://github.com/chsmy/FilePicker/blob/master/images/2012.gif)

钉钉中上传的附件的文件选择器，可以选择常用文件和根据文件夹选择全部文件，做个demo实现这种效果
界面跟钉钉不一样，展示方面没啥好说的每个项目的UI展示可能都不一样，关键是数据是怎么或得的。

包括常用文件和全部文件

>全部文件：
 通过根路径找到相应的files并展示
 
 全部文件首页只需找到手机的根目录 ,然后通过系统file的方法找到数据
 
 ```
         String path = Environment.getExternalStorageDirectory().getAbsolutePath() 
         File directory = new File(path);
         File[] files = directory.listFiles(filter);
 ```
 
 当我们点击文件夹的时候，仍然可以通过上面的方法找到它下级的文件

>常用文件：
查找常用文件当然也可以通过上面全部文件的方法递归找出所有的文件，但是这种方法太耗时了也不够优雅，因为
安卓系统把所有的文件都会映射到媒体数据库中，所以比较好的方法就是通过媒体查询MediaStore.Files
demo中查询的是微信和QQ中的图片和下载的文件
demo中是通过媒体查询查询所有的文件在根据条件匹配相关的文件，查询所有文件是耗时操作 ，所以放在子线程中去执行

demo中查询语句：
```
Cursor cursor = context.getContentResolver().query(
                MediaStore.Files.getContentUri("external"),//数据源
                projection,//查询类型
                null,//查询条件
                null,
                MediaStore.Files.FileColumns.DATE_ADDED + " DESC");
```
查询条件没有写所以查询所有的文件，现实项目中如果明确知道要查询的文件类型可以更改查询条件，**这样可以非常有效的提高查询的速度**。
比如：
```
 String selection = MediaStore.Files.FileColumns.MIME_TYPE + "= ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? "
                + " or " + MediaStore.Files.FileColumns.MIME_TYPE + " = ? ";
        String[] selectionArgs = new String[]{"text/plain", "application/msword",
                "application/pdf", "application/vnd.ms-powerpoint",
                "application/vnd.ms-excel", "image/jpeg",
                "image/png"
        };
        final Cursor cursor = context.getContentResolver().query(
                MediaStore.Files.getContentUri("external"),//数据源
                projection,//查询类型
                selection,//查询条件
                selectionArgs,
                MediaStore.Files.FileColumns.DATE_ADDED + " DESC");
```

这样就可以查询出所有的 .tex .doc .dot .xls . jpg .jepeg .png 文件速度快很多。

**Thanks:**

[LFilePicker](https://github.com/leonHua/LFilePicker)

[Android-FilePicker](https://github.com/DroidNinja/Android-FilePicker)
