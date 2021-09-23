package davatar.wu.hydrator;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.gotti.wurmunlimited.modloader.classhooks.HookException;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;
import org.gotti.wurmunlimited.modloader.interfaces.PreInitable;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;

import javassist.CtClass;
import javassist.CtMethod;

public class Hydrator implements WurmServerMod, PreInitable {
    private static Logger logger = Logger.getLogger("Hydrator");
    
    public static void logException(String msg, Throwable e) {
        if (logger != null) { logger.log(Level.SEVERE, msg, e); }
    }

    public static void logInfo(String msg) {
        if (logger != null) { logger.log(Level.INFO, msg); }
    }

    public String getVersion() {
    	return "0.1";
    }

	@Override
	public void preInit() {
		try {
			CtClass ctClass = HookManager.getInstance().getClassPool().getCtClass("com.wurmonline.server.creatures.CreatureStatus");
			CtClass[] methodArguments = new CtClass[1];
			methodArguments[0] = CtClass.floatType;
			CtMethod modifyThirst = ctClass.getDeclaredMethod("modifyThirst", methodArguments);
			modifyThirst.setBody("{ if($1 >= 0) { return this.modifyThirst(-$1, -1.0f, -1.0f, -1.0f, -1.0f); } else { return this.modifyThirst($1, -1.0f, -1.0f, -1.0f, -1.0f); } }");
		} catch(Exception e) {
			logException("preInit: ", e);
			e.printStackTrace();
			throw new HookException(e);
		}
	}
}
