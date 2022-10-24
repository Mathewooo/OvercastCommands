package gg.overcast.commands.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
class CoreCommand extends Command {

    private final ArrayList<SubCommand> subcommands;
    private final CommandList commandList;

    public CoreCommand(String name, String description,
                       String usageMessage, CommandList commandList,
                       List<String> aliases, ArrayList<SubCommand> subCommands
    ) {
        super(name, description, usageMessage, aliases);
        this.subcommands = subCommands;
        this.commandList = commandList;
    }

    public ArrayList<SubCommand> getSubCommands() {
        return subcommands;
    }

    @Override
    public boolean execute(@Nonnull CommandSender sender, @Nonnull String commandLabel, String[] args) {
        if (args.length > 0) {
            for (int index = 0; index < getSubCommands().size(); index++)
                if (args[0].equalsIgnoreCase(getSubCommands().get(index).getName())
                        || (getSubCommands().get(index).getAliases() != null
                        && getSubCommands().get(index).getAliases()
                        .contains(args[0]))
                ) {
                    getSubCommands().get(index).perform(sender, args);
                } else if (commandList == null) {
                    sender.sendMessage("--------------------------------");
                    for (SubCommand subcommand : subcommands)
                        sender.sendMessage(subcommand.getSyntax() + " - " + subcommand.getDescription());
                    sender.sendMessage("--------------------------------");
                } else commandList.displayCommandList(sender, subcommands);
        }
        return true;
    }

    @Override
    public @Nonnull List<String> tabComplete(@Nonnull CommandSender sender,
                                             @Nonnull String alias, String[] args)
            throws IllegalArgumentException {
        if (args.length == 1) {
            ArrayList<String> subcommandsArguments = new ArrayList<>();
            for (int index = 0; index < getSubCommands().size(); index++) {
                subcommandsArguments.add(
                        getSubCommands().get(index)
                                .getName()
                );
            }
            return subcommandsArguments;
        } else if (args.length >= 2) {
            for (int index = 0; index < getSubCommands().size(); index++)
                if (args[0].equalsIgnoreCase(getSubCommands().get(index).getName())) {
                    List<String> subCommandArgs = getSubCommands()
                            .get(index)
                            .getSubcommandArguments(
                                    (Player) sender, args
                            );
                    if (subCommandArgs != null) return subCommandArgs;
                    return Collections.emptyList();
                }
        }
        return Collections.emptyList();
    }
}
