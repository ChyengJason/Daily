package com.example.cheng.myfifthapplication.util;

/**
 * Created by cheng on 16-3-12.
 * 颜色
 */
public class ColorPanel {
    public final static int red =        0xffF44336;
    public final static int pink =       0xffE91E63;
    public final static int Purple =     0xff9C27B0;
    public final static int DeepPurple = 0xff673AB7;
    public final static int Indigo =     0xff3F51B5;
    public final static int Blue =       0xff2196F3;
    public final static int LightBlue =  0xff03A9F4;
    public final static int Cyan =       0xff00BCD4;
    public final static int Teal =       0xff009688;
    public final static int Green =      0xff4CAF50;
    public final static int LightGreen = 0xff8BC34A;
    public final static int Lime =       0xffCDDC39;
    public final static int Yellow =     0xffFFEB3B;
    public final static int Amber =      0xffFFC107;
    public final static int Orange =     0xffFF9800;
    public final static int DeepOrange = 0xffFF5722;
    public final static int Brown =      0xff795548;
    public final static int Grey =       0xff9E9E9E;
    public final static int BlueGray =   0xff607D8B;
    public final static int Black =      0xff000000;
    public final static  int White =      0xffffffff;

    public static String int2stringColor(int color){
        switch (color){
            case red:         return "red";
            case pink:        return "pink";
            case Purple:      return "Purple";
            case DeepPurple:  return "DeepPurple";
            case Indigo:      return "Indigo";
            case Blue:        return "Blue";
            case LightBlue:   return "LightBlue";
            case Cyan:        return "Cyan";
            case Teal:        return "Teal";
            case Green:       return "Green";
            case LightGreen:  return "LightGreen";
            case Lime:        return "Lime";
            case Yellow:      return "Yellow";
            case Amber:       return "Amber";
            case Orange:      return "Orange";
            case DeepOrange:  return "DeepOrange";
            case Brown:       return "Brown";
            case Grey:        return "Grey";
            case BlueGray:    return "BlueGray";
            case Black:       return "Black";
            case White:       return "White";
            default:          return "Black";
        }
    }

    public static int string2intColor(String color){
        switch (color){
            case "red":         return red;
            case "pink":        return pink;
            case "Purple":      return Purple;
            case "DeepPurple":  return DeepPurple;
            case "Indigo":      return Indigo;
            case "Blue":        return Blue;
            case "LightBlue":   return LightBlue;
            case "Cyan":        return Cyan;
            case "Teal":        return Teal;
            case "Green":       return Green;
            case "LightGreen":  return LightGreen;
            case "Lime":        return Lime;
            case "Yellow":      return Yellow;
            case "Amber":       return Amber;
            case "Orange":      return Orange;
            case "DeepOrange":  return DeepOrange;
            case "Brown":       return Brown;
            case "Grey":        return Grey;
            case "BlueGray":    return BlueGray;
            case "Black":       return Black;
            case "White":       return White;
            default:          return Black;
        }
    }
    public static String getDefultColor(){
        return "Black";
    }
}
