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
import java.util.LinkedList;
import java.util.List;

import com.convert.html.HtmlFileCreator;

public class Converter {

  public static void main(String[] args) {
    if (args.length != 1) {
      throw new IllegalArgumentException();
    }
    Converter converter = new Converter();
    String filePath = args[0];
    converter.processFiles(filePath);
  }

  public void processFiles(String filePath) {
    String[] filePathList = null;
    if (filePath.contains(Constant.COMMA)) {
      filePathList = filePath.split(Constant.COMMA);
    }
    List<File> files = new LinkedList<File>();

    if (filePathList == null) {
      createFileList(filePath, files);
    } else {
      for (String filePathFromList : filePathList) {
        createFileList(filePathFromList, files);
      }
    }
    for (File fileToProcess : files) {
      HtmlFileCreator htmlFileCreator = new HtmlFileCreator();
      htmlFileCreator.createHtmlFile(fileToProcess);
    }
  }

  private void createFileList(String filePath, List<File> files) {
    File file = new File(filePath);
    if (file.exists()) {
      if (file.isDirectory()) {
        File[] filePathList = file.listFiles();
        for (File innerFiles : filePathList) {
          createFileList(innerFiles.getAbsolutePath(), files);
        }
      } else {
        addFilesToProcessList(filePath, files, file);
      }
    }
  }

  private void addFilesToProcessList(String filePath, List<File> files, File file) {
    int lastDot = filePath.lastIndexOf(".") + 1;
    String fileExtention = filePath.substring(lastDot);
    if ((!filePath.contains("_blog.html")) && isFileAllowed(Constant.getFileExtentionAllowedList(), fileExtention)) {
      files.add(file);
    }
  }

  private boolean isFileAllowed(String[] fileExtentionAllowedList, String fileExtention) {
    boolean flag = false;
    for (String fileExt : fileExtentionAllowedList) {
      if (fileExt.equalsIgnoreCase(fileExtention)) {
        flag = true;
        break;
      }
    }
    return flag;
  }
}
