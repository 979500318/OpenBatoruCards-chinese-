package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class LRIGA_W2_ByeByeGabriela extends Card {

    public LRIGA_W2_ByeByeGabriela()
    {
        setImageSets("WXDi-P16-036");

        setOriginalName("バイバイ！！ガブリエラ");
        setAltNames("バイバイガブリエラ Baibai Gaburiera");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは能力を失う。\n" +
                "@E：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@C：アタックできない。@@を得る。\n" +
                "@E %W %X：あなたのトラッシュから#Gを持つシグニ１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Gabriela, Bye - Bye!!");
        setDescription("en",
                "@E: Target SIGNI on your opponent's field loses its abilities until end of turn.\n@E: Target SIGNI on your opponent's field gains@>@C: This SIGNI cannot attack.@@until end of turn.\n@E %W %X: Add target SIGNI with a #G from your trash to your hand.\n"
        );
        
        setName("en_fan", "Bye-Bye!! Gabriela");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and until end of turn, it loses its abilities.\n" +
                "@E: Target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@C: Can't attack.@@" +
                "@E %W %X: Target 1 #G @[Guard]@ SIGNI from your trash, and add it to your hand."
        );

		setName("zh_simplified", "拜拜！！哲布伊来");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，直到回合结束时为止，其的能力失去。\n" +
                "@E :对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n" +
                "@E %W%X从你的废弃区把持有#G的精灵1张作为对象，将其加入手牌。\n" +
                "（@E能力的:的左侧有费用。则可以选择不把费用支付，而不发动）\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.GABRIELA);
        setLRIGTeam(CardLRIGTeam.MUGEN_SHOUJO);
        setColor(CardColor.WHITE);
        setCost(Cost.colorless(2));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

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
            registerEnterAbility(new EnerCost(Cost.color(CardColor.WHITE, 1) + Cost.colorless(1)), this::onEnterEff3);
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MUTE).OP().SIGNI()).get();
            disableAllAbilities(target, AbilityGain.ALLOW, ChronoDuration.turnEnd());
        }
        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            if(target != null) attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
        }
        private void onEnterEff3()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withState(CardStateFlag.CAN_GUARD).fromTrash()).get();
            addToHand(target);
        }
    }
}
