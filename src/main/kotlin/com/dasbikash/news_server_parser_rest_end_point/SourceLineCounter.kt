/*
 * Copyright 2019 das.bikash.dev@gmail.com. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dasbikash.news_server_parser_rest_end_point

import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.RandomAccessFile
import java.util.ArrayList
import java.util.HashMap

object SourceLineCounter {

    private val allFiles = ArrayList<File>()
    private val countMode = SourceLineCounter.COUNT_MODE.EXCLUDE_TEST_FILES

    internal enum class COUNT_MODE {
        ABSOLUTE_ALL, ALL, EXCLUDE_TEST_FILES
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val file: File
        when (countMode) {
            SourceLineCounter.COUNT_MODE.ALL, SourceLineCounter.COUNT_MODE.ABSOLUTE_ALL -> file = File("src/")
            SourceLineCounter.COUNT_MODE.EXCLUDE_TEST_FILES -> file = File("src/main/")
        }

        if (file.exists() && file.isDirectory && file.canExecute()) {
            processFile(file)
        }

        val fileLineCountMap = HashMap<File, Int>()

        allFiles.forEach { file1 ->
            print("File: " + file1.absolutePath)
            if (file1.name.matches("\\..+".toRegex())) {
                println(" is hidden so quiting")
                return//@allFiles.forEach
            }
            try {
                val fileReader = RandomAccessFile(file1, "r")
                var lineCount = 0

                do {
                    val nextLine: String?
                    try {
                        nextLine = fileReader.readLine()
                    } catch (e: IOException) {
                        e.printStackTrace()
                        break
                    }

                    if (nextLine == null) {
                        break
                    }

                    if (countMode != SourceLineCounter.COUNT_MODE.ABSOLUTE_ALL) {
                        if (nextLine.trim { it <= ' ' }.isEmpty() ||
                                nextLine.contains("import") ||
                                nextLine.contains("package") ||
                                nextLine.matches("^//.+".toRegex()) ||
                                nextLine.matches("^.\\*.+".toRegex())) {
                            continue
                        }
                    }
                    lineCount++
                } while (true)
                println(" Line count: $lineCount")
                fileLineCountMap[file1] = lineCount
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }

        var totalLineCount = 0
        for (lineCount in fileLineCountMap.values) {
            totalLineCount += lineCount
        }

        println("Total lines: $totalLineCount")


    }


    private fun processFile(file: File) {
        val childFileNames = file.list()

        if (childFileNames != null && childFileNames.size > 0) {

            for (childFileName in childFileNames) {
                val childFile = File(file.absolutePath + "/" + childFileName)
                if (childFile.exists() && childFile.isDirectory && childFile.canExecute()) {
                    processFile(childFile)
                } else {
                    allFiles.add(childFile)
                }
            }
        }
    }
}
