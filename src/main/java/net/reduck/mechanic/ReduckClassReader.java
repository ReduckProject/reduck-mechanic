package net.reduck.mechanic;

import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author Gin
 * @since 2023/8/1 10:10
 */
public class ReduckClassReader {
    private static final int[] structure = new int[]{
            4, 2, 2, 2, -1, 2, 2, 2, 2, 2, 2, -1, 2 - 1, 2, -1
    };

    private static final String[] constantPoolTags = new String[]{
            ""
            , "CONSTANT_Utf8"
            , ""
            , "CONSTANT_Integer"
            , "CONSTANT_Float"
            , "CONSTANT_Long"
            , "CONSTANT_Double"
            , "CONSTANT_Class"
            , "CONSTANT_String"
            , "CONSTANT_Fieldref"
            , "CONSTANT_Methodref"
            , "CONSTANT_InterfaceMethodref"
            , "CONSTANT_NameAndType"
            , ""
            , ""
            , "CONSTANT_MethodHandle"
            , "CONSTANT_MethodType"
            , "CONSTANT_InvokeDynamic"
    };
//    CONSTANT_Class	7
//    CONSTANT_Fieldref	9
//    CONSTANT_Methodref	10
//    CONSTANT_InterfaceMethodref	11
//    CONSTANT_String	8
//    CONSTANT_Integer	3
//    CONSTANT_Float	4
//    CONSTANT_Long	5
//    CONSTANT_Double	6
//    CONSTANT_NameAndType	12
//    CONSTANT_Utf8	1
//    CONSTANT_MethodHandle	15
//    CONSTANT_MethodType	16
//    CONSTANT_InvokeDynamic	18

    private static final int expectMagic = 0xCAFEBABE;

    public static void main(String[] args) throws IOException {
        byte[] data = FileCopyUtils.copyToByteArray(new File("/Users/zhanjinkai/Documents/GitHub/reduck-mechanic/src/main/resources/ClassReader.class"));
        byte[] process;
        int pos = 0;
        int index = 0;
        int magic = BitsUtils.readInt(read(data, pos, structure[index]));
        System.out.println("magic valid is " + (magic == expectMagic));
        pos += structure[index];
        index++;

        int minorVersion = BitsUtils.readUnsignedShort(read(data, pos, structure[index]));
        System.out.println("minorVersion=" + minorVersion);

        pos += structure[index];
        index++;

        int majorVersion = BitsUtils.readUnsignedShort(read(data, pos, structure[index]));
        System.out.println("majorVersion=" + majorVersion);

        pos += structure[index];
        index++;

        int constantPoolCount = BitsUtils.readUnsignedShort(read(data, pos, structure[index]));
        System.out.println("constantPoolCount=" + constantPoolCount);

        pos += structure[index];
        index++;

        // constant pool
        process = read(data, pos, constantPoolCount - 1);
        processConstantPool(process);

        pos += constantPoolCount - 1;
        index++;

    }

    public static void processConstantPool(byte[] data) {
        int constantPoolTag = (int) data[0];
        System.out.println("constantPoolTag=" + constantPoolTags[constantPoolTag]);
        switch (constantPoolTags[constantPoolTag]) {
            case "CONSTANT_Methodref":
                processMethodRef(data);
                break;
            default:
                //
        }
    }

    public static void processMethodRef(byte[] data) {
        int classIndex = BitsUtils.readUnsignedShort(read(data, 1, 2));
        System.out.println("classIndex=" + classIndex);

        int nameAndTypeIndex = BitsUtils.readUnsignedShort(read(data, 3, 2));
        System.out.println("nameAndTypeIndex=" + nameAndTypeIndex);

        for(int i =  0; i< data.length; i++){
            System.out.println(data[i]);
        }
    }

    public static byte[] read(byte[] data, int pos, int len) {
        System.out.println(pos + "+" + len);
        byte[] newData = new byte[len];
        System.arraycopy(data, pos, newData, 0, len);
        return newData;
    }
}
