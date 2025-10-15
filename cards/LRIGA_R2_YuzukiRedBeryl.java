package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class LRIGA_R2_YuzukiRedBeryl extends Card {

    public LRIGA_R2_YuzukiRedBeryl()
    {
        setImageSets("WXDi-P10-025");

        setOriginalName("遊月・レッドベリル");
        setAltNames("ユヅキレッドベリル Yuzuki Reddo Beriru");
        setDescription("jp",
                "@A $G1 %X：あなたのシグニを２体まで対象とし、ターン終了時まで、それらは@>@C：このシグニは正面のシグニのパワーが10000以上であるかぎり、[[アサシン]]を得る。@@を得る。"
        );

        setName("en", "Yuzuki Red Beryl");
        setDescription("en",
                "@A $G1 %X: Up to two target SIGNI on your field gain@>@C: As long as the SIGNI in front of this SIGNI has power 10000 or more, this SIGNI gains [[Assassin]].@@until end of turn."
        );
        
        setName("en_fan", "Yuzuki Red Beryl");
        setDescription("en_fan",
                "@A $G1 %X: Target up to 2 of your SIGNI, and until end of turn, they gain:" +
                "@>@C: As long as the SIGNI in front of this SIGNI has power 10000 or more, this SIGNI gains [[Assassin]]."
        );

		setName("zh_simplified", "游月·红绿柱石");
        setDescription("zh_simplified", 
                "@A $G1 %X:你的精灵2只最多作为对象，直到回合结束时为止，这些得到\n" +
                "@>@C :这只精灵的正面的精灵的力量在10000以上时，得到[[暗杀]]。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.YUZUKI);
        setColor(CardColor.RED);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ActionAbility act = registerActionAbility(new EnerCost(Cost.colorless(1)), this::onActionEff);
            act.setUseLimit(UseLimit.GAME, 1);
        }

        private void onActionEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.ABILITY).own().SIGNI());
            
            if(data.get() != null)
            {
                for(int i=0;i<data.size();i++)
                {
                    ConstantAbility attachedConst = new ConstantAbility(new AbilityGainModifier(this::onAttachedConstEffModGetSample));
                    attachedConst.setCondition(this::onAttachedConstEffCond);
                    
                    attachAbility(data.get(i), attachedConst, ChronoDuration.turnEnd());
                }
            }
        }
        private ConditionState onAttachedConstEffCond(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getOppositeSIGNI() != null &&
                   cardIndex.getIndexedInstance().getOppositeSIGNI().getIndexedInstance().getPower().getValue() >= 10000 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityAssassin());
        }
    }
}
