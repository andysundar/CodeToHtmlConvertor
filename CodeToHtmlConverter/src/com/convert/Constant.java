/**
 * Copyright [2014] Anindya Bandopadhyay
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.convert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Constant {

  public static final String FILE_EXTENTION = "_blog.html";
  public static final String COMMA = ",";
  public static final String WORKING_DIR = System.getProperty("user.dir");
  private static String fileExtentionAllowed;
  private static String[] fileExtentionAllowedList;
  private static String javaKeywords;
  private static String[] javaKeywordList;
  private static String cssFileName;
  private static final StringBuilder workingDirPath = new StringBuilder(WORKING_DIR).append(File.separator);

  static {
    File propsFile = new File(workingDirPath.toString() + "properties", "html.properties");
    InputStream is;
    Properties props = new Properties();
    try {
      is = new FileInputStream(propsFile);
      props.load(is);
      is.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    fileExtentionAllowed = props.getProperty("file.extention.allowed");
    fileExtentionAllowedList = fileExtentionAllowed.split(COMMA);

    javaKeywords = props.getProperty("java.keyWords");
    javaKeywordList = javaKeywords.split(COMMA);

    cssFileName = props.getProperty("css.file.name");
  }

  public static String[] getFileExtentionAllowedList() {
    return fileExtentionAllowedList;
  }

  public static String getJavaKeywords() {
    return javaKeywords;
  }

  public static String[] getJavaKeywordList() {
    return javaKeywordList;
  }

  public static String getCssFileName() {
    return cssFileName;
  }

  public static StringBuilder getWorkingDirPath() {
    return workingDirPath;
  }
}
