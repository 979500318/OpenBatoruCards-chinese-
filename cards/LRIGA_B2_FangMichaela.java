package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;

public final class LRIGA_B2_FangMichaela extends Card {

    public LRIGA_B2_FangMichaela()
    {
        setImageSets("WXDi-P16-044");

        setOriginalName("牙・ミカエラ");
        setAltNames("キバミカエラ Kiba Mikaera");
        setDescription("jp",
                "@E：対戦相手のルリグ１体を対象とし、それを凍結する。\n" +
                "@E：次の対戦相手のターン終了時まで、このルリグは@>@C：対戦相手のシグニの@U能力は発動しない。@@を得る。"
        );

        setName("en", "Michaela, Fangs");
        setDescription("en",
                "@E: Freeze target LRIG on your opponent's field.\n@E: This LRIG gains@>@C: The @U abilities of your opponent's SIGNI do not activate.@@until the end of your opponent's next end phase."
        );
        
        setName("en_fan", "Fang Michaela");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's LRIG, and freeze it.\n" +
                "@E: Until the end of your opponent's next turn, this LRIG gains:" +
                "@>@C: The @U abilities of your opponent's SIGNI don't activate."
        );

		setName("zh_simplified", "牙·米卡伊来");
        setDescription("zh_simplified", 
                "@E :对战对手的分身1只作为对象，将其冻结。\n" +
                "@E :直到下一个对战对手的回合结束时为止，这只分身得到\n" +
                "@>@C 对战对手的精灵的@U能力不能发动。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MICHAELA);
        setLRIGTeam(CardLRIGTeam.MUGEN_SHOUJO);
        setColor(CardColor.BLUE);
        setCost(Cost.colorless(1));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().anyLRIG()).get();
            freeze(target);
        }
        private void onEnterEff2()
        {
            ConstantAbility attachedConst = new ConstantAbility(new PlayerRuleCheckModifier<>(PlayerRuleCheckType.CAN_USE_ABILITY, TargetFilter.HINT_OWNER_OP, this::onEnterEff2RuleCheck));
            attachAbility(getCardIndex(), attachedConst, ChronoDuration.nextTurnEnd(getOpponent()));
        }
        private RuleCheckState onEnterEff2RuleCheck(RuleCheckData data)
        {
            return data.getSourceAbility() instanceof AutoAbility &&
                   data.getSourceCardIndex() != null && CardType.isSIGNI(data.getSourceCardIndex().getCardReference().getType()) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }
    }
}
