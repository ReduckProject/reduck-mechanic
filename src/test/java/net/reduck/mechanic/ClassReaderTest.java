package net.reduck.mechanic;

import lombok.SneakyThrows;
import net.reduck.mechanic.internal.asm.ClassReader;
import net.reduck.mechanic.internal.asm.ClassVisitor;
import net.reduck.mechanic.internal.asm.MethodVisitor;
import net.reduck.mechanic.internal.asm.Opcodes;
import org.junit.jupiter.api.Test;
import org.springframework.util.FileCopyUtils;

import java.io.File;

/**
 * @author Gin
 * @since 2023/8/1 17:07
 */
public class ClassReaderTest {

    @Test
    @SneakyThrows
    public void test() {
        ClassReader reader = new ClassReader(FileCopyUtils.copyToByteArray(new File("/Users/zhanjinkai/Documents/GitHub/reduck-mechanic/target/classes/net/reduck/mechanic/Synchronization.class")));
        System.out.println(reader.header);

        String name = reader.getClassName();
//        for(int i = 0; i < reader.getItemCount(); i++) {
//            System.out.println(reader.getItem(i));
//        }

        PackageNameExtractor packageNameExtractor = new PackageNameExtractor();
        reader.accept(packageNameExtractor, ClassReader.SKIP_DEBUG);
        String packageName = packageNameExtractor.getPackageName();
        System.out.println(packageName);
    }

    public class PackageNameExtractor extends ClassVisitor {

        private String packageName;

        public PackageNameExtractor() {
            super(Opcodes.ASM9);
        }

        public String getPackageName() {
            return packageName;
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            // Extracting the package name from the class name
            int lastSlashIndex = name.lastIndexOf('/');
            if (lastSlashIndex >= 0) {
                packageName = name.substring(0, lastSlashIndex).replace('/', '.');
            } else {
                packageName = "";
            }

            System.out.println("version=" + version);
            System.out.println("access=" + access);
            System.out.println("name=" + name);
            System.out.println("signature=" + signature);
            System.out.println("superName=" + superName);
            System.out.println("interfaces=" + interfaces);
            System.out.println("---------------------------------------");
            super.visit(version, access, name, signature, superName, interfaces);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            System.out.println("name=" + name);
            System.out.println("descriptor=" + descriptor);
            System.out.println("signature=" + signature);
            System.out.println("exceptions=" + exceptions);
            System.out.println("------------------------------------");
            return super.visitMethod(access, name, descriptor, signature, exceptions);
        }

        @Override
        public void visitSource(String source, String debug) {
            System.out.println("source=" + source);
            System.out.println("debug=" + debug);
            super.visitSource(source, debug);
        }
    }
}
