package com.azubike.ellipsis.api.commands;

import com.azubike.ellipsis.commands.BaseCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class CloseAccountCommand extends BaseCommand {
}
