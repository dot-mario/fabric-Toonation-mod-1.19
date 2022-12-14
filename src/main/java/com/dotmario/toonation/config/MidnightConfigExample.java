package com.dotmario.toonation.config;

/** MidnightConfig documentation & examples:
 * Thanks for choosing MidnightConfig - the fancy, tiny and lightweight config library.
 * If you want to use the lib in your mod, here are some examples and hints:
 * Every option in a MidnightConfig class has to be public and static, so we can access it from other classes.
 * The config class also has to extend MidnightConfig*/

public class MidnightConfigExample extends MidnightConfig {

//    @Comment public static Comment text1;                       // Comments are rendered like an option without a button and are excluded from the config file
//    @Comment(centered = true) public static Comment text2;      // Centered comments are the same as normal ones - just centered!
//    @Entry public static int fabric = 16777215;                 // Example for an int option
//    @Entry public static double world = 1.4D;                   // Example for a double option
//    @Entry public static boolean showInfo = true;               // Example for a boolean option
    @Comment(centered = true) public static Comment setting;
    @Entry(name = "인벤토리 아이템 하나 제거",min=0) public static int removeItem = 0;
    @Entry(name = "크리퍼 소환",min=0) public static int spawnCreeper = 0;
    @Entry(name = "TNT 소환",min=0) public static int spawnTNT = 0;
    @Entry(name = "랜덤 몬스터 소환",min=0) public static int spawnRandomMonster = 0;
//    @Entry(name = "인벤토리 한칸 제거",min=0) public static int removeInventory = 0;
//    @Entry(name = "인벤토리 한칸 복구",min=0) public static int addInventory = 0;
    @Entry(name = "HP 한칸 제거",min=0) public static int removeHP = 0;
    @Entry(name = "HP 한칸 추가",min=0) public static int addHP = 0;
    @Comment(centered = true) public static Comment blank1;
    @Comment(centered = true) public static Comment blank2;
    @Comment(centered = true) public static Comment blank3;
    @Comment(centered = true) public static Comment blank4;
    @Comment(centered = true) public static Comment blank5;
    @Comment(centered = true) public static Comment blank6;
    @Entry(name = "투네이션 알림 URL") public static String toonationURL = "";
//    @Entry public static TestEnum testEnum = TestEnum.FABRIC;   // Example for an enum option
//    public enum TestEnum {                               // Enums allow the user to cycle through predefined options
//        QUILT, FABRIC, FORGE
//    }
//    @Entry(min=69,max=420) public static int hello = 420;   // - The entered number has to be larger than 69 and smaller than 420
//    @Entry(width = 7, min = 7, isColor = true, name = "I am a color!") public static String titleColor = "#ffffff"; // The isColor property adds a preview box for a hexadecimal color
//    @Entry(name = "I am an array list!") public static List<String> arrayList = List.of("String1", "String2"); // Array String Lists are also supported
//    @Entry(name = "I am an int slider.",isSlider = true, min = 0, max = 100) public static int intSlider = 35; // Int fields can also be displayed as a Slider
//    @Entry(name = "I am a float slider!", isSlider = true, min = 0f, max = 1f, precision = 1000) public static float floatSlider = 0.24f; // And so can floats! Precision defines the amount of decimal places
    // The name field can be used to specify a custom translation string or plain text

//    public static int imposter = 16777215; // - Entries without an @Entry or @Comment annotation are ignored

    /*
    The .json language file for your config class could look similar to this:
    {
	    "toonation.midnightconfig.title":"I am a title",
	    "toonation.midnightconfig.text1":"I am a comment *u*",
	    "toonation.midnightconfig.text2":"I am a centered comment (╯°□°）╯︵ ┻━┻",
	    "toonation.midnightconfig.name":"I am a string!",
	    "toonation.midnightconfig.name.tooltip":"I am a tooltip uwu \nI am a new line",
	    // When hovering over the option "showInfo",
	    // this text will appear as a tooltip.
	    // "\n" inserts a line break.
	    "toonation.midnightconfig.fabric":"I am an int",
	    "toonation.midnightconfig.world":"I am a double",
	    "toonation.midnightconfig.showInfo":"I am a boolean",
	    "toonation.midnightconfig.hello":"I am a limited int!",
	    "toonation.midnightconfig.testEnum":"I am an enum!",
	    "toonation.midnightconfig.enum.TestEnum.FORGE":"Slow",
	    "toonation.midnightconfig.enum.TestEnum.FABRIC":"Fancy",
	    "toonation.midnightconfig.enum.TestEnum.QUILT":"Fabulous"
    }
    To initialize the config you have to call "MidnightConfig.init("modid", MidnightConfigExample.class)" in your ModInitializer
    To get an instance of the config screen you have to call "MidnightConfig.getScreen(parent, "modid");"

    If you don't use the whole library and therefore not the automatic ModMenu integration, the code in your ModMenu integration class would look something like this:
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> MidnightConfig.getScreen(parent, "modid");
    }
    */
}