package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class LRIG_R3_HanayoThreeProfuseBloomOfAHundredFlames extends Card {

    public LRIG_R3_HanayoThreeProfuseBloomOfAHundredFlames()
    {
        setImageSets("WX24-D2-04");

        setOriginalName("百火繚乱　花代・参");
        setAltNames("ヒャッカリョウランハナヨサン Hyakkaryouran Hanayo San");
        setDescription("jp",
                "@A $T1 %R：対戦相手のパワー10000以下のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@A $G1 %R0：あなたのシグニ１体を対象とし、ターン終了時まで、それは【アサシン】を得る。"
        );

        setName("en", "Hanayo Three, Profuse Bloom of a Hundred Flames");
        setDescription("en",
                "@A $T1 %R: Target 1 of your opponent's SIGNI with power 10000 or less, and banish it.\n" +
                "@A $G1 %R0: Target 1 of your SIGNI, and until end of turn, it gains [[Assassin]]."
        );

		setName("zh_simplified", "百火缭乱 花代·叁");
        setDescription("zh_simplified", 
                "@A $T1 %R:对战对手的力量10000以下的精灵1只作为对象，将其破坏。\n" +
                "@A $G1 %R0:你的精灵1只作为对象，直到回合结束时为止，其得到[[暗杀]]。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.HANAYO);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 1)), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);

            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
        }
        
        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(0,10000)).get();
            banish(target);
        }
        
        private void onActionEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()).get();
            if(target != null) attachAbility(target, new StockAbilityAssassin(), ChronoDuration.turnEnd());
        }
    }
}
