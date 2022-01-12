package me.harpylmao.commands.command.interfaces;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import net.dv8tion.jda.api.Permission;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandParams {
  String name();

  String usage() default "";

  Permission[] permissions() default {};

  String[] aliases() default {};

  String category() default "General";

  long cooldown() default 0;
}
