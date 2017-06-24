/*
Copyright 2017 Travis Deshotels

Permission is hereby granted, free of charge, to any person obtaining a copy of this software
and associated documentation files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute,
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or
substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package tk.codedojo.foodselectorconverter.driver;

import com.fasterxml.jackson.databind.ObjectMapper;
import tk.codedojo.foodselectorconverter.beans.Choices;
import tk.codedojo.foodselectorconverter.beans.Restaurant;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Driver {
    private Driver(){

    }

    public static void main(String[] args){
        FileReader fileReader = null;
        String line;
        String[] data;
        String restaurantName;
        String person;
        String like;
        List<String> likes = new ArrayList();
        List<Choices> choicesList = new ArrayList();
        List<Restaurant> restaurants = new ArrayList();

        try {
            fileReader = new FileReader(args[0]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        try {
            line = bufferedReader.readLine();
            while (line != null){
                data = line.split(",");
                restaurantName = data[0];
                line = bufferedReader.readLine();
                while (!lineIsBlank(line)) {
                    data = line.split(",");
                    person = data[0];
                    for (int i = 1; i < data.length; i++) {
                        like = data[i];
                        if (!"".equals(like)) {
                            likes.add(like);
                        }
                    }
                    choicesList.add(new Choices(person, likes));
                    likes = new ArrayList();
                    line = bufferedReader.readLine();
                }
                restaurants.add(new Restaurant(restaurantName, choicesList));
                choicesList = new ArrayList();
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("food.json"), restaurants);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    private static boolean lineIsBlank(String line){
        if (line==null)
            return true;

        String[] data = line.split(",");

        for(int i=0;i<data.length;i++){
            if (!"".equals(data[i]))
                return false;
        }
        return true;
    }
}
