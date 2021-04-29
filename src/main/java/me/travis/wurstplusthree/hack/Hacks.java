package me.travis.wurstplusthree.hack;

import me.travis.wurstplusthree.WurstplusThree;
import me.travis.wurstplusthree.event.events.Render2DEvent;
import me.travis.wurstplusthree.event.events.Render3DEvent;
import me.travis.wurstplusthree.gui.WurstplusGui;
import me.travis.wurstplusthree.hack.chat.AutoEz;
import me.travis.wurstplusthree.hack.chat.ClearChatbox;
import me.travis.wurstplusthree.hack.chat.CustomChat;
import me.travis.wurstplusthree.hack.chat.TotemPopCounter;
import me.travis.wurstplusthree.hack.client.Gui;
import me.travis.wurstplusthree.hack.client.Hud;
import me.travis.wurstplusthree.hack.combat.*;
import me.travis.wurstplusthree.hack.misc.*;
import me.travis.wurstplusthree.hack.player.*;
import me.travis.wurstplusthree.hack.render.*;
import me.travis.wurstplusthree.util.Globals;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Hacks implements Globals {

    private final List<Hack> hacks = new ArrayList<>();
    private List<Hack> drawnHacks = new ArrayList<>();

    public Hacks() {
        // chat
        this.hacks.add(new ClearChatbox());
        this.hacks.add(new TotemPopCounter());
        this.hacks.add(new AutoEz());
        this.hacks.add(new CustomChat());
        // client
        this.hacks.add(new Gui());
        this.hacks.add(new Hud());
        // combat
        // TODO : ANVIL AURA
        // TODO : PISTON AURA
        // TODO : BED AURA
        // TODO : AUTO 32K
        this.hacks.add(new KillAura());
        this.hacks.add(new Surround());
        this.hacks.add(new CrystalAura());
        this.hacks.add(new Offhand());
        this.hacks.add(new Trap());
        this.hacks.add(new HoleFill());
        this.hacks.add(new Crits());
        this.hacks.add(new Burrow());
        // misc
        this.hacks.add(new FakePlayer());
        this.hacks.add(new MCF());
        this.hacks.add(new InstantBreak());
        this.hacks.add(new Blink());
        this.hacks.add(new Replenish());
        this.hacks.add(new EntityMine());
        this.hacks.add(new AntiVoid());
        this.hacks.add(new Radio());
        this.hacks.add(new Pitbull());
        // player
        this.hacks.add(new Sprint());
        this.hacks.add(new ReverseStep());
        this.hacks.add(new Freecam());
        this.hacks.add(new NoKnockback());
        this.hacks.add(new Speed());
        this.hacks.add(new Step());
        this.hacks.add(new Scaffold());
        this.hacks.add(new FastUtil());
        this.hacks.add(new ArmourMend());
        this.hacks.add(new XCarry());
        this.hacks.add(new Jesus());
        this.hacks.add(new PlayerSpoofer());
        // render
        this.hacks.add(new AntiFog());
        this.hacks.add(new Nametags());
        this.hacks.add(new CrystalRender());
        this.hacks.add(new HoleESP());
        this.hacks.add(new NoRender());
        this.hacks.add(new CameraClip());
        this.hacks.add(new Fov());
        this.hacks.add(new HandColour());
        this.hacks.add(new Chams());
        this.hacks.add(new LogSpots());
        this.hacks.add(new Tracers());
        this.hacks.add(new SmallShield());
        this.hacks.add(new TargetDetails());
        this.hacks.add(new Esp());
        this.hacks.add(new ExtraTab());
        this.hacks.add(new BreakHighlight());
        this.hacks.add(new ViewModel());
    }

    public List<Hack> getHacks() {
        return this.hacks;
    }

    public List<Hack> getDrawnHacks() {
        return this.drawnHacks;
    }

    public boolean isDrawHack(Hack hack) {
        return this.drawnHacks.contains(hack);
    }

    public void addDrawHack(Hack hack) {
        this.drawnHacks.add(hack);
    }

    public void removeDrawnHack(Hack hack) {
        if (!isDrawHack(hack)) return;
        this.drawnHacks.remove(hack);
    }

    public Hack getHackByName(String name) {
        for (Hack hack : this.hacks) {
            if (hack.getName().equalsIgnoreCase(name)) {
                return hack;
            }
        }
        return null;
    }

    public void enablehack(String name) {
        this.getHackByName(name).enable();
    }

    public void disablehack(String name) {
        this.getHackByName(name).disable();
    }

    public boolean ishackEnabled(String name) {
        return this.getHackByName(name).isEnabled();
    }

    public void onUpdate() {
        this.hacks.stream().filter(Hack::isEnabled).forEach(Hack::onUpdate);
    }

    public void onTick() {
        this.hacks.stream().filter(Hack::isEnabled).forEach(Hack::onTick);
    }

    public void onRender2D(Render2DEvent event) {
        this.hacks.stream().filter(Hack::isEnabled).forEach(hack -> hack.onRender2D(event));
    }

    public void onRender3D(Render3DEvent event) {
        this.hacks.stream().filter(Hack::isEnabled).forEach(hack -> hack.onRender3D(event));
    }

    public void onLogout() {
        this.hacks.forEach(Hack::onLogout);
    }

    public void onLogin() {
        this.hacks.forEach(Hack::onLogin);
    }

    public void onUnload() {
        this.hacks.forEach(MinecraftForge.EVENT_BUS::unregister);
        this.hacks.forEach(Hack::onUnload);
    }

    public void unloadAll() {
        for (Hack hack : this.hacks) {
            hack.disable();
        }
    }

    public void onKeyDown(int key) {
        if (key == 0 || mc.currentScreen instanceof WurstplusGui) {
            return;
        }
        for (Hack hack : this.hacks) {
            if (hack.getBind() == key) {
                hack.toggle();
            }
        }
    }

    public List<Hack.Category> getCategories() {
        return Arrays.asList(Hack.Category.values());
    }

    public List<Hack> getHacksByCategory(Hack.Category cat) {
        List<Hack> hacks = new ArrayList<>();
        for (Hack hack : this.hacks) {
            if (hack.getCategory() == cat) {
                hacks.add(hack);
            }
        }
        hacks.sort(Comparator.comparing(Hack::getName));
        return hacks;
    }

    public List<Hack> getEnabledHacks() {
        List<Hack> hacks = new ArrayList<>();
        for (Hack hack : this.hacks) {
            if (hack.isEnabled()) {
                hacks.add(hack);
            }
        }
        return hacks;
    }

    public List<Hack> getSortedHacks(boolean reverse, boolean customFont) {
        if (customFont) {
            return this.getEnabledHacks().stream().sorted(Comparator.comparing(hack ->
                    WurstplusThree.GUI_FONT_MANAGER.getTextWidth(hack.getFullArrayString())
                            * (reverse ? -1 : 1))).collect(Collectors.toList());
        } else {
            return this.getEnabledHacks().stream().sorted(Comparator.comparing(hack ->
                    mc.fontRenderer.getStringWidth(hack.getFullArrayString())
                            * (reverse ? -1 : 1))).collect(Collectors.toList());
        }
    }

}
